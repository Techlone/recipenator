package recipenator.lualibs;

import recipenator.api.lua.LuaLibBase;
import recipenator.utils.OreDictIndexator;

public class OreDictLib extends LuaLibBase<OreDictIndexator> {
    @Override
    public String getName() {
        return "ore";
    }

    @Override
    public OreDictIndexator get() {
        return new OreDictIndexator();
    }
}
