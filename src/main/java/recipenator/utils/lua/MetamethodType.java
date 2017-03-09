package recipenator.utils.lua;

import org.luaj.vm2.LuaValue;

public enum MetamethodType {
    INDEX(LuaValue.INDEX),
    MUL(LuaValue.MUL);

    public final LuaValue name;
    MetamethodType(LuaValue name) {
        this.name = name;
    }
}
