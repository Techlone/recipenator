package recipenator.lualibs;

import recipenator.api.lua.LuaLibBase;
import recipenator.utils.OreDictIndexer;

public class OreDictLib extends LuaLibBase<OreDictIndexer> {
    public OreDictLib() {
        this.name = "ores";
    }

    @Override
    public OreDictIndexer get() {
        return new OreDictIndexer();
    }
}
