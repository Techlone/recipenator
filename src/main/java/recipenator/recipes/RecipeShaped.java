package recipenator.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import recipenator.api.component.IRecipeComponent;
import recipenator.utils.RecipeHelper;

public class RecipeShaped implements IRecipe {
    private final static int MAX_WIDTH = 3;
    private final static int MAX_HEIGHT = 3;

    private final RecipeHelper.Size size;
    private final IRecipeComponent[] inputs;
    private final IRecipeComponent<ItemStack> result;
    private final boolean mirrored;

    public RecipeShaped(RecipeHelper.Size size, IRecipeComponent[] inputs, IRecipeComponent<ItemStack> result) {
        this(size, inputs, result, false);
    }

    public RecipeShaped(RecipeHelper.Size size, IRecipeComponent[] inputs, IRecipeComponent<ItemStack> result, boolean mirrored) {
        this.size = size;
        this.inputs = inputs;
        this.result = result;
        this.mirrored = mirrored;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return inventory.getSizeInventory() >= inputs.length && RecipeHelper.returnFirstTrueInDoubleCycle((x, y) -> checkMatch(inventory, x, y), MAX_WIDTH - size.width + 1, MAX_HEIGHT - size.height + 1);
    }

    private boolean checkMatch(InventoryCrafting inventory, int xo, int yo) {
        return RecipeHelper.returnFirstTrueInDoubleCycle((x, y) -> {
            ItemStack item = inventory.getStackInRowAndColumn(x, y);
            int xi = x - xo, yi = y - yo;
            int i = yi * size.width + xi;
            return RecipeHelper.isEquals(getComponent(i), item);
                    //|| mirrored && RecipeHelper.isEquals(getComponent(i + size.width - ((xi << 1) + 1)), item);
        }, MAX_WIDTH, MAX_HEIGHT);
    }

    private IRecipeComponent getComponent(int i) {
        if (i < 0 || i >= inputs.length) return null;
        return inputs[i];
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

    public RecipeHelper.Size getSize() {
        return size;
    }
}
