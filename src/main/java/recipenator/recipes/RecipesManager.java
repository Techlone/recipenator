package recipenator.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import recipenator.api.component.IRecipeComponent;
import recipenator.api.metamethod.Metamethod;
import recipenator.api.metamethod.MetamethodType;
import recipenator.components.NullComponent;
import recipenator.utils.RecipeHelper;

import java.util.Arrays;

public class RecipesManager {
    public void addRecipe(IRecipe recipe) {
        GameRegistry.addRecipe(recipe);
    }

    public void addShaped(IRecipeComponent<ItemStack> result, IRecipeComponent[][] rawInputs) {
        RecipeHelper.Size size = RecipeHelper.rectangulate(rawInputs);
        IRecipeComponent[] inputs = Arrays.stream(rawInputs).flatMap(Arrays::stream).toArray(IRecipeComponent[]::new);
        addRecipe(new RecipeShaped(size, inputs, result));
    }

    public void addShapeless(IRecipeComponent<ItemStack> result, IRecipeComponent[] rawInputs) {
        IRecipeComponent[] inputs = Arrays.stream(rawInputs).filter(NullComponent::isNull).toArray(IRecipeComponent[]::new);
        addRecipe(new RecipeShapeless(inputs, result));
    }

    @Metamethod(MetamethodType.INDEX)
    public Object index(String id) {
        return null; //TODO mod integration
    }
}
