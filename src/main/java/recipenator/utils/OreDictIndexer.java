package recipenator.utils;

import net.minecraftforge.oredict.OreDictionary;
import recipenator.components.OreComponent;

public class OreDictIndexer {
    public final Object __index(Object id) {
        return id instanceof String ? get((String) id) : index(id);
    }

    public Object index(Object id) {
        return null;
    }

    public OreComponent get(String id) {
        if (id.contains("*"))
            throw new RuntimeException("Not supperted");
        if (OreDictionary.doesOreNameExist(id))
            return new OreComponent(id);
        return null;
    }
}
