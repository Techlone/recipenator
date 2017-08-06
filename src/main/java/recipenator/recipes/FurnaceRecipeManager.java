package recipenator.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import recipenator.api.component.IRecipeComponent;

import java.util.Collection;
import java.util.Collections;

public class FurnaceRecipeManager {
    public static void add(IRecipeComponent<ItemStack> output, IRecipeComponent<?> input) {
        add(output, input, 0);
    }

    public static void add(IRecipeComponent<ItemStack> output, IRecipeComponent<?> input, float exp) {
        ItemStack outputItem = output.getRecipeItem();
        for (ItemStack inputItem : input.getAllItems()) {
            FurnaceRecipes.smelting().func_151394_a(inputItem, outputItem, exp);
        }
    }

    public static void removeInput(IRecipeComponent<?> input) {
        for (ItemStack inputItem : input.getAllItems()) {
            FurnaceRecipes.smelting().getSmeltingList().remove(inputItem);
        }
    }

    public static void removeOutput(IRecipeComponent<ItemStack> output) {
        ItemStack outputItem = output.getRecipeItem();
        Collection friedItems = FurnaceRecipes.smelting().getSmeltingList().values();
        friedItems.removeAll(Collections.singleton(outputItem));
    }
}
