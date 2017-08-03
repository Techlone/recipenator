/*******************************************************************************
* Copyright (c) 2011 Luaj.org. All rights reserved.
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
******************************************************************************/
package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import recipenator.api.extention.LuaField;
import recipenator.api.extention.LuaName;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * LuaValue that represents a Java class.
 * <p>
 * Will respond to get() and set() by returning field values, or java methods. 
 * <p>
 * This class is not used directly.  
 * It is returned by calls to {@link CoerceJavaToLua#coerce(Object)} 
 * when a Class is supplied.
 * @see CoerceJavaToLua
 * @see CoerceLuaToJava
 */
class JavaClass extends JavaInstance implements CoerceJavaToLua.Coercion {
    static final Map<Class, JavaClass> classes = new ConcurrentHashMap<>();
    static final LuaValue NEW = valueOf("new");

    Map<LuaValue, Field> fields;
    Map<LuaValue, LuaValue> methods;
    Map<LuaValue, LuaValue> innerClasses;
    Map<LuaValue, LuaField<?, ?>> extendedFields;

    static JavaClass forClass(Class c) {
        return classes.computeIfAbsent(c, JavaClass::new);
    }

    JavaClass(Class c) {
        super(c);
        javaClass = this;
        fields = getFields(c);
        methods = getMethods(c);
        innerClasses = getInnerClasses(c);
        extendedFields = new HashMap<>();
    }

    public LuaValue getConstructor() {
        return getMethod(NEW);
    }

    public LuaValue coerce(Object javaValue) {
        return this;
    }

    LuaValue getFieldValue(LuaUserdata luaObject, LuaValue fieldName) {
        Field field = fields.get(fieldName);
        if (field != null) try {
            return CoerceJavaToLua.coerce(field.get(luaObject.m_instance));
        } catch (IllegalAccessException e) {
            throw new LuaError(e);
        }

        LuaField luaField = extendedFields.get(fieldName);
        if (luaField != null && luaField.getter != null)
            return CoerceJavaToLua.coerce(luaField.getter.apply(luaObject.m_instance));

        return LuaValue.NIL;
    }

    boolean setFieldValue(LuaUserdata luaObject, LuaValue fieldName, LuaValue value) {
        Field field = fields.get(fieldName);
        if (field != null) try {
            field.set(luaObject.m_instance, CoerceLuaToJava.coerce(value, field.getType()));
            return true;
        } catch (Exception e) {
            throw new LuaError(e);
        }

        LuaField luaField = extendedFields.get(fieldName);
        if (luaField != null && luaField.getter != null) {
            luaField.setter.accept(luaObject.m_instance, CoerceLuaToJava.coerce(value, Object.class));
            return true;
        }

        return false;
    }

    LuaValue getMethod(LuaValue key) {
        return methods.get(key);
    }

    LuaValue getInnerClass(LuaValue key) {
        return innerClasses.get(key);
    }

    private static Map<LuaValue, Field> getFields(Class clazz) {
        Map<LuaValue, Field> fields = new HashMap<>();
        for (Field field : clazz.getFields()) {
            if (!Modifier.isPublic(field.getModifiers())) continue;
            fields.put(getLuaName(field), field);
            try {
                if (!field.isAccessible())
                    field.setAccessible(true);
            } catch (SecurityException ignored) {
            }
        }
        return fields;
    }

    private static Map<LuaValue, LuaValue> getMethods(Class clazz) {
        Map<LuaString, List<JavaMethod>> allMethods = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            if (!Modifier.isPublic(method.getModifiers())) continue;
            List<JavaMethod> sameNamedMethods = allMethods.computeIfAbsent(getLuaName(method), k -> new ArrayList<>());
            sameNamedMethods.add(JavaMethod.forMethod(method));
        }

        List<JavaConstructor> constructors = new ArrayList<>();
        for (Constructor constructor : clazz.getConstructors())
            if (Modifier.isPublic(constructor.getModifiers()))
                constructors.add(JavaConstructor.forConstructor(constructor));

        Map<LuaValue, LuaValue> methods = new HashMap<>();
        methods.put(NEW, constructors.size() == 1 ? constructors.get(0) : JavaConstructor.forConstructors(constructors));

        for (Entry<LuaString, List<JavaMethod>> entry : allMethods.entrySet()) {
            List<JavaMethod> sameNamedMethods = entry.getValue();
            LuaValue javaMethod = sameNamedMethods.size() == 1 ? sameNamedMethods.get(0) : JavaMethod.forMethods(sameNamedMethods);
            methods.put(entry.getKey(), javaMethod);
        }
        return methods;
    }

    private static Map<LuaValue, LuaValue> getInnerClasses(Class clazz) {
        Map<LuaValue, LuaValue> innerClasses = new HashMap<>();
        for (Class innerClass : clazz.getClasses()) {
            String name = innerClass.getName();
            String stub = name.substring(Math.max(name.lastIndexOf('$'), name.lastIndexOf('.')) + 1);
            innerClasses.put(LuaValue.valueOf(stub), forClass(innerClass));
        }
        return innerClasses;
    }

    public static <T extends AnnotatedElement & Member> LuaString getLuaName(T member) {
        LuaName luaName = member.getAnnotation(LuaName.class);
        return LuaString.valueOf(luaName != null ? luaName.value() : getLuaName(member.getName()));
    }

    public static String getLuaName(String name) {
        return (name.startsWith("get") || name.startsWith("set") ? name.substring(3) : name).replaceAll("([a-z0-9])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
