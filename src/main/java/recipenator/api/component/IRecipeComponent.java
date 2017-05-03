package recipenator.api.component;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IRecipeComponent<T> {
    Object multiply(int multiplier);

    Object setMeta(int meta);

    Object setTag(NBTTagCompound tag);

    boolean equals(ItemStack component);

    T getRecipeItem();
}
