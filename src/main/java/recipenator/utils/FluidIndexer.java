package recipenator.utils;

import net.minecraftforge.fluids.FluidRegistry;
import recipenator.components.FluidComponent;

public class FluidIndexer {
    public final Object __index(Object id) {
        return id instanceof String ? get((String) id) : index(id);
    }

    protected Object index(Object id) {
        return null;
    }

    public static FluidComponent get(String id) {
        if (FluidRegistry.isFluidRegistered(id))
            return new FluidComponent(id);
        return null;
    }
}
