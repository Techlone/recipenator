package recipenator.components;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import recipenator.api.component.IRecipeComponent;
import recipenator.api.component.RecipeComponentBase;

import java.util.Collections;
import java.util.List;

public class FluidComponent extends RecipeComponentBase<FluidStack> {
    private String id;

    public FluidComponent(String id) {
        this(id, 1, 0, null);
    }

    public FluidComponent(String id, int count, int meta, NBTTagCompound tag) {
        super(count, meta, tag);
        this.id = id;
    }

    @Override
    public FluidStack getRecipeItem() {
        return new FluidStack(FluidRegistry.getFluid(id), count, tag);
    }

    @Override
    public List<ItemStack> getAllItems() {
        return Collections.emptyList();
    }

    @Override
    public boolean equals(ItemStack component) {
        return false;
    }

    @Override
    protected IRecipeComponent<FluidStack> newInstance(int count, int meta, NBTTagCompound tag) {
        return new FluidComponent(id, count, 0, tag);
    }
}
