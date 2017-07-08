package recipenator.testenv;

import recipenator.api.lua.ILuaLibGetter;
import recipenator.api.lua.LuaLibBase;
import recipenator.api.metamethod.Metamethod;
import recipenator.api.metamethod.MetamethodType;
import recipenator.components.OreComponent;
import recipenator.lualibs.FurnaceLib;
import recipenator.lualibs.ItemsLib;
import recipenator.lualibs.OreDictLib;
import recipenator.lualibs.RecipesLib;
import recipenator.utils.NamesTree;
import recipenator.utils.OreDictIndexer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TestLuaLibGetter implements ILuaLibGetter {
    @Override
    public Set<LuaLibBase> get() {
        try {
            Collection<String> names = Files.readAllLines(Paths.get(".\\src\\test\\names.txt"));
            return new HashSet<LuaLibBase>() {{
                add(new ItemsLib(NamesTree.getTreeRoot(names)));
                add(new TestOreDictLib());
                add(new RecipesLib());
                add(new FurnaceLib());
            }};
        } catch (Exception ignored) {
            throw new RuntimeException("All is gone!");
        }
    }

    class TestOreDictLib extends OreDictLib {
        @Override
        public OreDictIndexer get() {
            return new TestOreDictIndexer();
        }

        class TestOreDictIndexer extends OreDictIndexer {
            @Override
            @Metamethod(MetamethodType.INDEX)
            public Object index(String id) {
                return new OreComponent(id);
            }

            @Metamethod(MetamethodType.CALL)
            public Object call(Object id) {
                return null;
            }
        }
    }
}
