package recipenator.api.extention;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Repeatable(LuaNames.class)
public @interface LuaName {
    String value() default "";
}
