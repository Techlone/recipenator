package recipenator.api;

import net.minecraft.item.ItemStack;

public interface IRecipeComponent<T> {
    Object mulCount(int multiplier);

    Object withMeta(int meta);

    boolean equals(ItemStack component);

    T getRecipeItem();
}
