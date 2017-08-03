package recipenator.testenv;

import recipenator.components.ItemComponent;
import recipenator.components.OreComponent;
import recipenator.utils.NamesTree;
import recipenator.utils.OreDictIndexer;

import java.util.ArrayList;
import java.util.List;

public class TestOreDictIndexer extends OreDictIndexer {
    public static OreComponent get(String id) {
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
