package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import recipenator.api.extention.LuaField;
import recipenator.api.extention.LuaName;
import recipenator.api.metamethod.Metamethod;
import recipenator.utils.CommonHelper;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class JavaClassExtender {
    public static void extendBy(Class<?> extClass) {
        extendFields(extClass);
        extendMethods(extClass);
    }

    private static void extendFields(Class<?> extClass) {
        for (Field field : extClass.getDeclaredFields()) {
            if (!isPublicStatic(field) || !field.getType().equals(LuaField.class)) continue;
            LuaField<?, ?> luaField = (LuaField<?, ?>) CommonHelper.ignoreErrors(field::get, null);
            JavaClass.forClass(getFirstGenericType(field)).extendedFields.put(getLuaName(field), luaField);
        }
    }

    private static void extendMethods(Class<?> extClass) {
        for (Method method : extClass.getDeclaredMethods()) {
            if (!isPublicStatic(method) || method.getParameterCount() == 0) continue;
            Class<?> baseClass = method.getParameterTypes()[0];
            if (method.isAnnotationPresent(Metamethod.class)) {
                CoerceJavaToLua.InstanceCoercion.extendMetatable(baseClass, method);
            } else {
                JavaClass.forClass(baseClass).methods.put(getLuaName(method), JavaMethod.forMethod(method));
            }
        }
    }

    private static Class<?> getFirstGenericType(Field field) {
        return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }

    private static boolean isPublicStatic(Member member) {
        return Modifier.isPublic(member.getModifiers()) && Modifier.isStatic(member.getModifiers());
    }

    public static <T extends AnnotatedElement & Member> LuaString getLuaName(T member) {
        LuaName luaName = member.getAnnotation(LuaName.class);
        return LuaString.valueOf(luaName != null ? luaName.value() : getLuaName(member.getName()));
    }

    public static String getLuaName(String name) {
        return (name.startsWith("get") || name.startsWith("set") ? name.substring(3) : name).replaceAll("([a-z0-9])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
