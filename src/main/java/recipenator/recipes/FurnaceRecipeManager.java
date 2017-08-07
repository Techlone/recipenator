package recipenator.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import recipenator.RecipenatorMod;
import recipenator.api.component.IRecipeComponent;
import recipenator.utils.RecipeHelper;

import java.util.*;

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

    public static void remove(IRecipeComponent<?> component) {
        remove(component, false);
    }

    public static void remove(IRecipeComponent<?> component, boolean isInput) {
        Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.smelting().getSmeltingList();
        Set<ItemStack> inputs = new HashSet<>();
        List<ItemStack> items = component.getAllItems();
        for (Map.Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
            ItemStack input = entry.getKey();
            ItemStack output = entry.getValue();
            float exp = FurnaceRecipes.smelting().func_151398_b(input);
            for (ItemStack item : items) {
                if (!RecipeHelper.areEqual(item, isInput ? input : output)) continue;
                inputs.add(input);
                RecipenatorMod.addCancelAction(() -> FurnaceRecipes.smelting().func_151394_a(input, output, exp));
            }
        }
        for (ItemStack input : inputs)
            smeltingList.remove(input);
    }
}
