package recipenator.lualib.recipe;

import recipenator.lualib.BaseLuaLib;

public class RecipeLib extends BaseLuaLib {
    @Override
    protected String getLibName() {
        return "recipe";
    }

    @Override
    protected Object getLib() {
        return new RecipeManager();
    }
}
