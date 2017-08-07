package recipenator.utils;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.component.IRecipeComponent;

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

    public static ItemStack findEqualItem(Set<ItemStack> input, IRecipeComponent component) {
        for (ItemStack item : input)
            if (component.equals(item))
                return item;
        return null;
    }

    public enum ComparisonType {
        NONE,
        STRICT,
        NONSTICK
    }

    public static boolean areEqual(IRecipeComponent component, ItemStack item) {
        if (component == null) return item == null;
        return item != null && component.equals(item);
    }

    public static boolean areEqual(ItemStack recipeItem, ItemStack inputItem) {
        return areEqual(recipeItem, inputItem, ComparisonType.NONSTICK);
    }

    public static boolean areEqual(ItemStack recipeItem, ItemStack inputItem, ComparisonType tagComparisonType) {
        if (recipeItem == null) return inputItem == null;
        return inputItem != null
                && recipeItem.getItem() == inputItem.getItem()
                && areMetaEqual(recipeItem.getItemDamage(), inputItem.getItemDamage())
                && areTagsEqual(recipeItem.getTagCompound(), inputItem.getTagCompound(), tagComparisonType);
    }

    private static boolean areMetaEqual(int recipeMeta, int inputMeta) {
        return recipeMeta == inputMeta || recipeMeta == OreDictionary.WILDCARD_VALUE || inputMeta == OreDictionary.WILDCARD_VALUE;
    }

    public static boolean areTagsEqual(NBTTagCompound recipeTag, NBTTagCompound inputTag, ComparisonType comparisonType) {
        if (recipeTag == null) return inputTag == null;
        return inputTag != null && (comparisonType == ComparisonType.NONSTICK
                ? doesTagContain(inputTag, recipeTag)
                : comparisonType != ComparisonType.STRICT || recipeTag.equals(inputTag));
    }

    public static boolean doesTagContain(NBTTagCompound base, NBTTagCompound input) {
        for (String tagName : (Set<String>) input.func_150296_c()) {
            if (!base.hasKey(tagName) || !input.getTag(tagName).equals(base.getTag(tagName))) return false;
        }
        return true;
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
