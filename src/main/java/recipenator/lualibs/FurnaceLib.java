package recipenator.lualibs;

import recipenator.api.lua.LuaLibBase;
import recipenator.recipes.FurnaceRecipeManager;

public class FurnaceLib extends LuaLibBase<FurnaceRecipeManager> {
    @Override
    public FurnaceRecipeManager get() {
        return new FurnaceRecipeManager();
    }
}
