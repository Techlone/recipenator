package recipenator.lualibs;

import recipenator.api.lua.LuaLibBase;
import recipenator.utils.OreDictIndexer;

public class OreDictLib extends LuaLibBase<OreDictIndexer> {
    @Override
    public String getName() {
        return "ores";
    }

    @Override
    public OreDictIndexer get() {
        return new OreDictIndexer();
    }
}
