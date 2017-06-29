package recipenator.utils;

import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.metamethod.Metamethod;
import recipenator.api.metamethod.MetamethodType;
import recipenator.components.OreComponent;

public class OreDictIndexator {
    @Metamethod(MetamethodType.INDEX)
    public Object index(String id) {
        if (OreDictionary.doesOreNameExist(id))
            return new OreComponent(id);
        return null;
    }
}
