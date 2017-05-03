package recipenator.lualib.oredict;

import recipenator.api.lua.BaseLuaLib;

public class OreDictLib extends BaseLuaLib {
    @Override
    public String getName() {
        return "ore";
    }

    @Override
    public Object get() {
        return new OreDictIndexator();
    }

}
