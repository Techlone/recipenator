package recipenator.components;

import net.minecraft.item.ItemStack;
import recipenator.api.component.IRecipeComponent;

import java.util.ArrayList;
import java.util.List;

public class NullComponent implements IRecipeComponent<ItemStack> {
    public static final IRecipeComponent NULL = new NullComponent();

    @Override
    public boolean equals(ItemStack component) {
        return component == null;
    }

    @Override
    public ItemStack getRecipeItem() {
        return null;
    }

    @Override
    public List<ItemStack> getAllItems() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "null";
    }

    public static boolean isNull(IRecipeComponent component) {
        return component == null || component == NULL;
    }
}
