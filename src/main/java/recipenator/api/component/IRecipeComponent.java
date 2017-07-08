package recipenator.api.component;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public interface IRecipeComponent<T> {
    default IRecipeComponent<T> multiply(int multiplier) {
        return this;
    }

    default IRecipeComponent<T> setMeta(int meta) {
        return this;
    }

    default IRecipeComponent<T> setTag(NBTTagCompound tag) {
        return this;
    }

    boolean equals(ItemStack component);

    T getRecipeItem();

    default List<ItemStack> getAllItems() {
        return new ArrayList<>();
    }
}
