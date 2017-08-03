package org.luaj.vm2.lib.jse;

import org.luaj.vm2.LuaString;
import recipenator.api.extention.LuaField;
import recipenator.api.extention.LuaName;
import recipenator.utils.CommonHelper;

import java.lang.reflect.*;

import static org.luaj.vm2.lib.jse.JavaClass.getLuaName;

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
//            if (method.isAnnotationPresent(Metamethod.class)) {
//                CoerceJavaToLua.InstanceCoercion.extendMetatable(baseClass, method);
//            } else {
            JavaClass.forClass(baseClass).methods.put(getLuaName(method), JavaMethod.forMethod(method));
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
