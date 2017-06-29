import org.junit.Assert;
import org.junit.Test;
import recipenator.api.extention.LuaField;
import recipenator.components.ItemComponent;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

public class Sandbox {
    @Test
    public void checkMethodReturnsVoid() {
        class Empty {
            public void method() {
            }
        }

        Class<?> returnType = getReturnTypeOfFirstMethod(Empty.class);

        Assert.assertFalse(Object.class.isAssignableFrom(returnType));
        Assert.assertTrue(void.class.isAssignableFrom(returnType));
    }

    @Test
    public void checkMethodReturnsSomething() {
        class Empty {
            public Empty method() {
                return new Empty();
            }
        }

        Class<?> returnType = getReturnTypeOfFirstMethod(Empty.class);

        Assert.assertFalse(void.class.isAssignableFrom(returnType));
        Assert.assertTrue(Object.class.isAssignableFrom(returnType));
    }

    private Class<?> getReturnTypeOfFirstMethod(Class<?> clazz) {
        return clazz.getMethods()[0].getReturnType();
    }

    @Test
    public void generic() {
        class MyField<V> extends LuaField<ItemComponent, V> {
            public MyField(Function<ItemComponent, V> getter) {
                super(getter);
            }
        }
        class MyFieldInt extends MyField<Integer> {
            public MyFieldInt(Function<ItemComponent, Integer> getter) {
                super(getter);
            }
        }
        class Test {
            public LuaField<ItemComponent, Integer> someField = new MyFieldInt(i -> i.toString().length());
        }

        Field field = Test.class.getFields()[0];
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        System.out.println(genericType);
        Class<?> firstClass = (Class<?>) genericType.getActualTypeArguments()[0];
        Assert.assertEquals(ItemComponent.class, firstClass);
    }
}
