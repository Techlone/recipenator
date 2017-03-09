package recipenator.api.annotation;

import recipenator.utils.lua.MetamethodType;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Metamethod {
    MetamethodType value();
}
