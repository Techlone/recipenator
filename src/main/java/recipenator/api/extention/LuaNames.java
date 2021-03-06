package recipenator.api.extention;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface LuaNames {
    LuaName[] value();
}
