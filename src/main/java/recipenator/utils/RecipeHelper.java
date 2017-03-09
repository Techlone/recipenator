package recipenator.utils;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import recipenator.api.IRecipeComponent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class RecipeHelper {
    public static Set<ItemStack> getInputSet(InventoryCrafting inventory) {
        int l = inventory.getSizeInventory();
        Set<ItemStack> input = new HashSet<>(l);
        for (int i = 0; i < l; i++) {
            ItemStack item = inventory.getStackInSlot(i);
            if (item != null) input.add(item);
        }
        return input;
    }

    public static ItemStack findEqualsItem(Set<ItemStack> input, IRecipeComponent component) {
        for (ItemStack item : input)
            if (component.equals(item))
                return item;
        return null;
    }

    public static boolean isEquals(IRecipeComponent component, ItemStack item) {
        if (component == null) return item == null;
        return item != null && component.equals(item);
    }

    public static boolean isEquals(ItemStack recipeItem, ItemStack inputItem) {
        if (recipeItem == null) return inputItem == null;
        return inputItem != null && recipeItem.isItemEqual(inputItem) && isEqualsTag(recipeItem, inputItem);
    }

    public static boolean isEqualsTag(ItemStack recipeItem, ItemStack inputItem) {
        NBTTagCompound recipeTag = recipeItem.getTagCompound();
        NBTTagCompound inputTag = inputItem.getTagCompound();
        if (recipeTag == null) return inputTag == null;
        return inputTag != null && recipeTag.equals(inputTag);
    }

    public static boolean returnFirstTrueInDoubleCycle(BiFunction<Integer, Integer, Boolean> func, int xm, int ym) {
        for (int x = 0; x < xm; x++)
            for (int y = 0; y < ym; y++)
                if (func.apply(x, y)) return true;
        return false;
    }

    public static Object[] getRecipeInputs(IRecipeComponent[] source) {
        Object[] result = new Object[source.length];
        for (int i = 0; i < source.length; i++)
            result[i] = source[i].getRecipeItem();
        return result;
    }

    public static <T> Size rectangulate(T[][] matrix) {
        if (matrix.length == 0) {
            return Size.ZERO;
        } else if (matrix.length == 1) {
            return new Size(matrix[0].length, 1);
        }

        int maxl = 0;
        for (T[] line : matrix)
            if (maxl < line.length)
                maxl = line.length;

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length == maxl) continue;
            matrix[i] = Arrays.copyOf(matrix[i], maxl);
        }

        return new Size(maxl, matrix.length);
    }

    public static class Size {
        public static Size ZERO = new Size(0, 0);

        public final int width;
        public final int height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
