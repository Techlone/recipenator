package recipenator.utils.lua;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import recipenator.api.annotation.Metamethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MetamethodWrapper {
    private final static Map<Class, LuaTable> metatables = new HashMap<>();

    private static LuaTable getMetatable(Object object) {
        Class cls = object.getClass();
        LuaTable mt = metatables.get(cls);
        if (mt == null) {
            Metamethod mma;
            mt = new LuaTable();
            for (Method method : cls.getMethods()) {
                if ((mma = method.getAnnotation(Metamethod.class)) != null) {
                    mt.set(mma.value().name, JavaMethod.fromMethod(method));
                }
            }
            if (mt.keyCount() == 0) return null;
            metatables.put(cls, mt);
        }
        return mt;
    }

    public static LuaValue wrap(Object value) {
        if (value instanceof LuaValue) return (LuaValue) value;
        LuaValue luaValue = CoerceJavaToLua.coerce(value);
        luaValue.setmetatable(getMetatable(value));
        return luaValue;
    }

    private static class JavaMethod extends VarArgFunction {
        private static Map<Method, JavaMethod> methods = new HashMap<>();

        public static JavaMethod fromMethod(Method m) {
            return methods.computeIfAbsent(m, k -> new JavaMethod(m));
        }

        private final Method method;
        private final Class<?>[] types;

        private JavaMethod(Method method) {
            this.method = method;
            this.types = method.getParameterTypes();
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            return invokeMethod(varargs.checkuserdata(1), varargs.subargs(2));
        }

        private LuaValue invokeMethod(Object instance, Varargs varargs) {
            try {
                return wrap(method.invoke(instance, convertArgs(varargs)));
            } catch (InvocationTargetException e) {
                throw new LuaError(e.getTargetException());
            } catch (Exception e) {
                return LuaValue.error(String.format("coercion error %s\nmethod %s", e, method));
            }
        }

        private Object[] convertArgs(Varargs varargs) {
            Object[] args = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                args[i] = CoerceLuaToJava.coerce(varargs.arg(i + 1), types[i]);
            }
            return args;
        }
    }
}
