package recipenator.lualib.recipe;

import recipenator.api.lua.BaseLuaLib;

public class RecipeLib extends BaseLuaLib<RecipeManager> {
    @Override
    public String getName() {
        return "recipe";
    }

    @Override
    public RecipeManager get() {
        return RecipeManager.instance;
    }
}
