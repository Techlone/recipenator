package recipenator.testenv;

import recipenator.lualibs.RecipesLib;

public class TestRecipesLib extends RecipesLib {
    @Override
    public TestRecipesManager get() {
        return new TestRecipesManager();
    }
}
