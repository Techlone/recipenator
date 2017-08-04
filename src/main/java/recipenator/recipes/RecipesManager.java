package recipenator.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import recipenator.RecipenatorMod;
import recipenator.api.component.IRecipeComponent;
import recipenator.components.NullComponent;
import recipenator.utils.RecipeHelper;

import java.util.Arrays;

public class RecipesManager {
    private static void addRecipe(IRecipe recipe) {
        GameRegistry.addRecipe(recipe);
        RecipenatorMod.addCancelAction(() -> CraftingManager.getInstance().getRecipeList().remove(recipe));
    }

    public static void add(IRecipeComponent<ItemStack> result, IRecipeComponent[][] rawInputs) {
        RecipeHelper.Size size = RecipeHelper.rectangulate(rawInputs);
        IRecipeComponent[] inputs = Arrays.stream(rawInputs).flatMap(Arrays::stream).toArray(IRecipeComponent[]::new);
        addRecipe(new RecipeShaped(size, inputs, result));
    }

    public static void add(IRecipeComponent<ItemStack> result, IRecipeComponent[] rawInputs) {
        IRecipeComponent[] inputs = Arrays.stream(rawInputs).filter(NullComponent::isNull).toArray(IRecipeComponent[]::new);
        addRecipe(new RecipeShapeless(inputs, result));
    }

    public Object index(Object id) {
        return null; //TODO mod integration
    }
}
