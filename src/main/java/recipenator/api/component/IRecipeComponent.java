package recipenator.api.component;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IRecipeComponent<T> {
    T getRecipeItem();
    List<ItemStack> getAllItems();
    boolean equals(ItemStack component);
}
