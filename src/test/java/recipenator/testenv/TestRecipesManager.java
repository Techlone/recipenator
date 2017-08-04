package recipenator.testenv;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import recipenator.api.component.IRecipeComponent;
import recipenator.recipes.RecipesManager;

public class TestRecipesManager extends RecipesManager {
    public static void add(IRecipeComponent<ItemStack> result, IRecipeComponent[][] rawInputs) {
        System.out.println("Add shaped recipe for " + result);
    }

    public static void add(IRecipeComponent<ItemStack> result, IRecipeComponent[] rawInputs) {
        System.out.println("Add shapeless recipe for " + result);
    }
}
