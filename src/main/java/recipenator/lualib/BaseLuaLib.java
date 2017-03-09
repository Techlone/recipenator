package recipenator.lualib;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import recipenator.utils.lua.MetamethodWrapper;

public abstract class BaseLuaLib extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        String libName = getLibName();
        env.set(libName, MetamethodWrapper.wrap(getLib()));
        env.get("package").get("loaded").set(libName, env.get(libName));
        return NIL;
    }

    protected abstract String getLibName();

    protected abstract Object getLib();
}
