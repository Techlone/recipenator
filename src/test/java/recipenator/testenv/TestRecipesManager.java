package recipenator.testenv;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import recipenator.api.component.IRecipeComponent;
import recipenator.recipes.RecipesManager;

public class TestRecipesManager extends RecipesManager {
    @Override
    public void addRecipe(IRecipe recipe) {
        System.out.println("addRecipe(IRecipe recipe)");
    }

    @Override
    public void addShaped(IRecipeComponent<ItemStack> result, IRecipeComponent[][] rawInputs) {
        System.out.println("Add shaped recipe for " + result);
        super.addShaped(result, rawInputs);
    }

    @Override
    public void addShapeless(IRecipeComponent<ItemStack> result, IRecipeComponent[] rawInputs) {
        System.out.println("Add shapeless recipe for " + result);
        super.addShapeless(result, rawInputs);
    }
}
