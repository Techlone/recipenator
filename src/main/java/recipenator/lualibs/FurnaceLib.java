package recipenator.lualibs;

import recipenator.api.lua.LuaLibBase;
import recipenator.recipes.FurnaceRecipeManager;

public class FurnaceLib extends LuaLibBase<FurnaceRecipeManager> {
    @Override
    public String getName() {
        return "furnace";
    }

    @Override
    public FurnaceRecipeManager get() {
        return new FurnaceRecipeManager();
    }
}
