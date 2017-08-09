package recipenator.components;

import net.minecraft.item.ItemStack;
import recipenator.api.component.IRecipeComponent;

import java.util.ArrayList;
import java.util.List;

public class AnyComponent implements IRecipeComponent<ItemStack> {
    public static final IRecipeComponent INSTANCE = new AnyComponent();

    @Override
    public boolean equals(ItemStack component) {
        return true;
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
        return "any";
    }
}
