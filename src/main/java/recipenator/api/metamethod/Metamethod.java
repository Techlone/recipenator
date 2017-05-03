package recipenator.api.metamethod;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Metamethod {
    MetamethodType value();
}
