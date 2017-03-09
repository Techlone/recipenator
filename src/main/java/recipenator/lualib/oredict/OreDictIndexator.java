package recipenator.lualib.oredict;

import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.annotation.Metamethod;
import recipenator.utils.lua.MetamethodType;

public class OreDictIndexator {
    @Metamethod(MetamethodType.INDEX)
    public Object index(String id) {
        if (OreDictionary.doesOreNameExist(id))
            return new OreComponent(id);
        return null;
    }
}
