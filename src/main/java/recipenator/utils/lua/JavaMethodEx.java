package recipenator.utils.lua;


import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import recipenator.api.extention.FieldGetter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class JavaMethodEx extends VarArgFunction {
    private final Method method;
    private final Class<?>[] types;
    private final boolean isStatic;
    private final String fieldName;

    private JavaMethodEx(Method method) {
        this.method = method;
        this.types = method.getParameterTypes();
        this.isStatic = Modifier.isStatic(method.getModifiers());
        FieldGetter getter = method.getAnnotation(FieldGetter.class);
        this.fieldName = getter != null ? getter.value().equals("") ? getFieldName(method.getName()) : getter.value() : "";
    }

    @Override
    public Varargs invoke(Varargs varargs) {
        return invokeMethod(varargs.checkuserdata(1), varargs.subargs(2));
    }

    private LuaValue invokeMethod(Object instance, Varargs varargs) {
        try {
//            return wrap(method.invoke(instance, convertArgs(instance, varargs)));
            return CoerceJavaToLua.coerce(method.invoke(instance, convertArgs(instance, varargs)));
        } catch (InvocationTargetException e) {
            throw new LuaError(e.getTargetException());
        } catch (Exception e) {
            return LuaValue.error(String.format("coercion error %s\nmethod %s", e, method));
        }
    }

    private Object[] convertArgs(Object instance, Varargs varargs) {
        Object[] args = new Object[types.length];
        if (isStatic) args[0] = instance;
        for (int i = isStatic ? 1 : 0; i < types.length; i++)
            args[i] = CoerceLuaToJava.coerce(varargs.arg(i + (isStatic ? 0 : 1)), types[i]);
        return args;
    }

    public static String getFieldName(String name) {
        return (name.startsWith("get") ? name.substring(3) : name).replaceAll("([a-z0-9])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
