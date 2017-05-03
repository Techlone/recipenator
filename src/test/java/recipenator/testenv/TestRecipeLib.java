package recipenator.testenv;

import recipenator.lualib.recipe.RecipeLib;

public class TestRecipeLib extends RecipeLib {
    @Override
    public TestRecipeManager get() {
        return new TestRecipeManager();
    }
}
