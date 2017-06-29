package recipenator.api.metamethod;

import org.luaj.vm2.LuaValue;

public enum MetamethodType {
    INDEX(LuaValue.INDEX),
    NEWINDEX(LuaValue.NEWINDEX),
    MUL(LuaValue.MUL);

    public final LuaValue name;
    MetamethodType(LuaValue name) {
        this.name = name;
    }
}
