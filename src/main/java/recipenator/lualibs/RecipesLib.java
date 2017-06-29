package recipenator.lualibs;

import recipenator.api.lua.LuaLibBase;
import recipenator.recipes.RecipesManager;

public class RecipesLib extends LuaLibBase<RecipesManager> {
    @Override
    public String getName() {
        return "recipes";
    }

    @Override
    public RecipesManager get() {
        return new RecipesManager();
    }
}
