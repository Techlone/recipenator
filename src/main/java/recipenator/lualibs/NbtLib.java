package recipenator.lualibs;

import recipenator.api.lua.LuaLibBase;

public class NbtLib extends LuaLibBase {
    @Override
    public Object get() {
        return new NbtProvider();
    }
}
