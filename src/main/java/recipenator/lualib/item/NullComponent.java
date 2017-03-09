package recipenator.lualib.item;

import net.minecraft.item.ItemStack;
import recipenator.api.IRecipeComponent;

public class NullComponent implements IRecipeComponent {
    public static final IRecipeComponent NULL = new NullComponent();

    @Override
    public Object mulCount(int multiplier) {
        return this;
    }

    @Override
    public Object withMeta(int meta) {
        return this;
    }

    @Override
    public boolean equals(ItemStack component) {
        return component == null;
    }

    @Override
    public Object getRecipeItem() {
        return null;
    }

    @Override
    public String toString() {
        return "null";
    }

    public static boolean isNull(IRecipeComponent component) {
        return component == NULL;
    }
}
