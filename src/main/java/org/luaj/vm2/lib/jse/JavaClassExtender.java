package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaString;
import recipenator.api.extention.LuaField;
import recipenator.utils.CommonHelper;

import java.lang.reflect.*;

import static org.luaj.vm2.lib.jse.JavaClass.getLuaNames;

public class JavaClassExtender {
    public static void extendBy(Class<?> extClass) {
        extendFields(extClass);
        extendMethods(extClass);
    }

    private static void extendFields(Class<?> extClass) {
        for (Field field : extClass.getDeclaredFields()) {
            if (!isPublicStatic(field) || !field.getType().equals(LuaField.class)) continue;
            LuaField<?, ?> luaField = (LuaField<?, ?>) CommonHelper.ignoreErrors(field::get, null);
            for (LuaString luaName : getLuaNames(field)) {
                JavaClass.forClass(getFirstGenericType(field)).extendedFields.put(luaName, luaField);
            }
        }
    }

    private static void extendMethods(Class<?> extClass) {
        for (Method method : extClass.getDeclaredMethods()) {
            if (!isPublicStatic(method) || method.getParameterCount() == 0) continue;
            Class<?> baseClass = method.getParameterTypes()[0];
//            if (method.isAnnotationPresent(Metamethod.class)) {
//                CoerceJavaToLua.InstanceCoercion.extendMetatable(baseClass, method);
//            } else {
            for (LuaString luaName : getLuaNames(method)) {
                JavaClass.forClass(baseClass).methods.put(luaName, JavaMethod.forMethod(method));
            }
//            }
        }
    }

    private static Class<?> getFirstGenericType(Field field) {
        return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }

    private static boolean isPublicStatic(Member member) {
        return Modifier.isPublic(member.getModifiers()) && Modifier.isStatic(member.getModifiers());
    }
}
