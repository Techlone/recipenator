package recipenator.testenv;

import recipenator.api.extention.ClassExtension;
import recipenator.api.extention.FieldGetter;
import recipenator.api.extention.FieldSetter;
import recipenator.lualib.item.ItemComponent;

@ClassExtension(ItemComponent.class)
public class TestItemExtension {
    @FieldGetter
    public static String getNode(ItemComponent itemComponent) {
        String s = itemComponent.toString();
        return s.substring(0, s.indexOf(System.lineSeparator()));
    }

    @FieldSetter
    public static void setMyMeta(ItemComponent itemComponent, int meta) {
        itemComponent.setMeta(meta < 32000 ? meta + 32000 : meta);
    }

    public static int someMethod(ItemComponent itemComponent) {
        return itemComponent.hashCode();
    }
}
