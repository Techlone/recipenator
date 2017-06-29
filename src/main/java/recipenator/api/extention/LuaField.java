package recipenator.api.extention;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class LuaField<T, V> {
    public final Function<T, V> getter;
    public final BiConsumer<T, V> setter;

    public LuaField(Function<T, V> getter) {
        this(getter, null);
    }

    public LuaField(BiConsumer<T, V> setter) {
        this(null, setter);
    }

    public LuaField(Function<T, V> getter, BiConsumer<T, V> setter) {
        this.getter = getter;
        this.setter = setter;
    }
}
