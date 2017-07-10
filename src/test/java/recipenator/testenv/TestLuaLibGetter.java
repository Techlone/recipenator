package recipenator.testenv;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import recipenator.api.lua.ILuaLibGetter;
import recipenator.api.lua.LuaLibBase;
import recipenator.components.ItemComponent;
import recipenator.components.OreComponent;
import recipenator.lualibs.FurnaceLib;
import recipenator.lualibs.ItemsLib;
import recipenator.lualibs.OreDictLib;
import recipenator.lualibs.RecipesLib;
import recipenator.utils.NamesTree;
import recipenator.utils.OreDictIndexer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
            public OreComponent get(String id) {
                return new OreComponent(id) {

                    @Override
                    public List<ItemComponent> getComponents() {
                        return new ArrayList<ItemComponent>() {{
                            add(new ItemComponent(new NamesTree.NameNode("minecraft:flint")));
                            add(new ItemComponent(new NamesTree.NameNode("minecraft:pumpkin_seeds")));
                            add(new ItemComponent(new NamesTree.NameNode("minecraft:diamond_ore")));
                        }};
                    }
                };
            }
        }
    }
}
