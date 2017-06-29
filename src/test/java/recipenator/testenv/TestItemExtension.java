package recipenator.testenv;

import recipenator.api.extention.LuaField;
import recipenator.api.extention.LuaName;
import recipenator.api.metamethod.Metamethod;
import recipenator.api.metamethod.MetamethodType;
import recipenator.components.ItemComponent;

import java.util.Random;

public class TestItemExtension {
    @LuaName("hash")
    public static LuaField<ItemComponent, Integer> myField = new LuaField<>(Object::hashCode);

    public static void printRnd(ItemComponent item) {
        System.out.println(new Random(item.hashCode()).nextInt());
    }

    @Metamethod(MetamethodType.NEWINDEX)
    public static void newIndex(ItemComponent item, Object key, Object value) {
        System.out.println("New key!");
    }
}
