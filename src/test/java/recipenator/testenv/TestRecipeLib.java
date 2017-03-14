package recipenator.testenv;

import recipenator.lualib.recipe.RecipeLib;

public class TestRecipeLib extends RecipeLib {
    @Override
    protected Object getLib() {
        return new TestRecipeManager();
    }
}
