package recipenator.lualibs;

import recipenator.api.lua.LuaLibBase;
import recipenator.utils.FluidIndexer;

public class FluidsLib extends LuaLibBase<FluidIndexer> {
    @Override
    public FluidIndexer get() {
        return new FluidIndexer();
    }
}
