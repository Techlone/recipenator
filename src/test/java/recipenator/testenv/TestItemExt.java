package recipenator.testenv;

import recipenator.api.extention.ClassExtension;
import recipenator.api.extention.FieldGetter;
import recipenator.lualib.item.ItemComponent;

@ClassExtension(ItemComponent.class)
public class TestItemExt {
    @FieldGetter
    public static void some_field(ItemComponent item) {
        System.out.println(item.toString());
    }
}
