package recipenator.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import recipenator.api.component.IRecipeComponent;
import recipenator.utils.RecipeHelper;

import java.util.Set;

public class RecipeShapeless implements IRecipe {
    private final IRecipeComponent[] inputs;
    private final IRecipeComponent<ItemStack> result;

    public RecipeShapeless(IRecipeComponent[] inputs, IRecipeComponent<ItemStack> result) {
        this.inputs = inputs;
        this.result = result;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        Set<ItemStack> inputs = RecipeHelper.getInputSet(inventory);
        if (inputs.size() != this.inputs.length) return false;
        ItemStack itemStack;
        for (IRecipeComponent component : this.inputs) {
            if ((itemStack = RecipeHelper.findEqualItem(inputs, component)) == null) return false;
            inputs.remove(itemStack);
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        return result.getRecipeItem();
    }

    @Override
    public int getRecipeSize() {
        return inputs.length;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result.getRecipeItem();
    }

    public Object[] getRecipeInputs() {
        return RecipeHelper.getRecipeInputs(inputs);
    }
}
