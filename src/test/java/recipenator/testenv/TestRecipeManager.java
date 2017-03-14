package recipenator.testenv;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import recipenator.api.IRecipeComponent;
import recipenator.lualib.recipe.RecipeManager;

public class TestRecipeManager extends RecipeManager {
    @Override
    public void addRecipe(IRecipe recipe) {
        AddedRecipes.add(recipe);
    }

    @Override
    public void addShaped(IRecipeComponent<ItemStack> result, IRecipeComponent[][] input) {
        System.out.println("Add shaped recipe for " + result);
        super.addShaped(result, input);
    }

    @Override
    public void addShapeless(IRecipeComponent<ItemStack> result, IRecipeComponent[] input) {
        System.out.println("Add shapeless recipe for " + result);
        super.addShapeless(result, input);
    }
}
