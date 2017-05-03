package recipenator;

import org.junit.Assert;
import org.junit.Test;
import recipenator.utils.lua.JavaMethodEx;

public class JavaMethodEx_Should {
    @Test
    public void ConvertMethodNameToFieldName_Simple() {
        String result = JavaMethodEx.getFieldName("myCoolField");
        Assert.assertEquals("my_cool_field", result);
    }

    @Test
    public void ConvertMethodNameToFieldName_GetContains() {
        String result = JavaMethodEx.getFieldName("getMyCoolField");
        Assert.assertEquals("my_cool_field", result);
    }

    @Test
    public void ConvertMethodNameToFieldName_SeveralUpper() {
        String result = JavaMethodEx.getFieldName("getMMMMyCoolField");
        Assert.assertEquals("mmmmy_cool_field", result);
    }
    @Test
    public void ConvertMethodNameToFieldName_SimpleWithNum() {
        String result = JavaMethodEx.getFieldName("myCoolField123");
        Assert.assertEquals("my_cool_field123", result);
    }

    @Test
    public void ConvertMethodNameToFieldName_GetContainsWithNum() {
        String result = JavaMethodEx.getFieldName("getMy123CoolField");
        Assert.assertEquals("my123_cool_field", result);
    }

    @Test
    public void ConvertMethodNameToFieldName_SeveralUpperWithNum() {
        String result = JavaMethodEx.getFieldName("getMM123MMy1CoolField");
        Assert.assertEquals("mm123_mmy1_cool_field", result);
    }
}
