package recipenator.api.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.function.Supplier;

public abstract class LuaLibBase<T> extends TwoArgFunction implements Supplier<T> {
    public LuaLibBase() {
        String libClassName = getClass().getSimpleName();
        if (!libClassName.equals(""))
            this.name = libClassName.substring(0, libClassName.length() - 3).toLowerCase();
    }

    @Override
    public LuaValue call(LuaValue emptyString, LuaValue env) {
        env.set(name, CoerceJavaToLua.coerce(get()));
        env.get("package").get("loaded").set(name, env.get(name));
        return NIL;
    }
}
