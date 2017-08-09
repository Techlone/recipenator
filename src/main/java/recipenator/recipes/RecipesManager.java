package recipenator.recipes;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import recipenator.RecipenatorMod;
import recipenator.api.component.IRecipeComponent;
import recipenator.components.NullComponent;
import recipenator.components.OreComponent;
import recipenator.utils.RecipeHelper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecipesManager {
    private static void addRecipe(IRecipe recipe) {
        GameRegistry.addRecipe(recipe);
        RecipenatorMod.addCancelAction(() -> getRecipeList().remove(recipe));
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

    public static void remove(IRecipeComponent<?> output) {
        getRecipeList().removeAll(getRecipesByOutput(output));
    }

    public static void remove(IRecipeComponent<?> output, IRecipeComponent[][] inputs) {
        RecipeHelper.Size size = RecipeHelper.rectangulate(inputs);
        List<IRecipeComponent> inputList = Arrays.stream(inputs).flatMap(Arrays::stream).collect(Collectors.toList());
        for (IRecipe recipe : getRecipesByOutput(output)) {
        }
    }

    public static void Test(OreComponent output) {
        for (ItemStack itemStack : output.getAllItems()) {
            for (ICraftingHandler h : GuiCraftingRecipe.craftinghandlers) {
                ICraftingHandler trueH = h.getRecipeHandler("item", itemStack);
                for (int i = 0; i < trueH.numRecipes(); i++) {
                    List<PositionedStack> ingredientStacks = trueH.getIngredientStacks(i);
                    System.out.println(ingredientStacks.size());
                }
            }
        }
    }

    private static List<IRecipe> getRecipesByOutput(IRecipeComponent<?> output) {
        return getRecipeList().stream()
                .filter(recipe -> output.equals(recipe.getRecipeOutput()))
                .collect(Collectors.toList());
    }

    private static List<IRecipe> getRecipeList() {
        return (List<IRecipe>) CraftingManager.getInstance().getRecipeList();
    }

    public Object index(Object id) {
        return null; //TODO mod integration
    }
}
