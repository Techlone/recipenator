package recipenator.api.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.function.Supplier;

public abstract class BaseLuaLib<T> extends TwoArgFunction implements Supplier<T> {
    @Override
    public LuaValue call(LuaValue emptyString, LuaValue env) {
        String libName = getName();
        env.set(libName, CoerceJavaToLua.coerce(get()));
        env.get("package").get("loaded").set(libName, env.get(libName));
        return NIL;
    }

    public abstract String getName();
}
