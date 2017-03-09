package recipenator.lualib.oredict;

import recipenator.lualib.BaseLuaLib;

public class OreDictLib extends BaseLuaLib {
    @Override
    protected String getLibName() {
        return "ore";
    }

    @Override
    protected Object getLib() {
        return new OreDictIndexator();
    }

}
