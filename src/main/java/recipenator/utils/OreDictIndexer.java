package recipenator.utils;

import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.lua.Metatable;
import recipenator.api.metamethod.Metamethod;
import recipenator.api.metamethod.MetamethodType;
import recipenator.components.OreComponent;

public class OreDictIndexer extends Metatable {
    public Object get(String id) {

    }

    public Object index(Object id) {
        
        if (OreDictionary.doesOreNameExist(id))
            return new OreComponent(id);
        return null;
    }
}
