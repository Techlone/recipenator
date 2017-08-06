package recipenator.components;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import recipenator.api.component.IRecipeComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BundleComponent extends OreComponent {
    private final List<IRecipeComponent<?>> components = new ArrayList<>();

    public BundleComponent(IRecipeComponent<?>... components) {
        super(null);
        add(components);
    }

    @Override
    public void add(IRecipeComponent<?>... components) {
        for (IRecipeComponent<?> item : components) {
            if (item instanceof BundleComponent) {
                this.components.addAll(((BundleComponent) item).components);
            } else {
                this.components.add(item);
            }
        }
    }

    @Override
    public List<ItemStack> getRecipeItem() {
        return components.stream().flatMap(c -> c.getAllItems().stream()).collect(Collectors.toList());
    }

    @Override
    protected IRecipeComponent<List<ItemStack>> newInstance(int count, int meta, NBTTagCompound tag) {
        return null;
    }
}
