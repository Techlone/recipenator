package org.luaj.vm2.lib.jse;

import org.junit.Assert;
import org.junit.Test;

import static org.luaj.vm2.lib.jse.JavaClass.getLuaName;

public class JavaClass_getLuaName_Should {
    @Test
    public void ConvertMethodNameToFieldName_Simple() {
        String result = getLuaName("myCoolField");
        Assert.assertEquals("my_cool_field", result);
    }

    @Test
    public void ConvertMethodNameToFieldName_GetContains() {
        String result = getLuaName("getMyCoolField");
        Assert.assertEquals("my_cool_field", result);
    }

    @Test
    public void ConvertMethodNameToFieldName_SeveralUpper() {
        String result = getLuaName("getMMMMyCoolField");
        Assert.assertEquals("mmmmy_cool_field", result);
    }
    @Test
    public void ConvertMethodNameToFieldName_SimpleWithNum() {
        String result = getLuaName("myCoolField123");
        Assert.assertEquals("my_cool_field123", result);
    }

    @Test
    public void ConvertMethodNameToFieldName_GetContainsWithNum() {
        String result = getLuaName("getMy123CoolField");
        Assert.assertEquals("my123_cool_field", result);
    }

    @Test
    public void ConvertMethodNameToFieldName_SeveralUpperWithNum() {
        String result = getLuaName("getMM123MMy1CoolField");
        Assert.assertEquals("mm123_mmy1_cool_field", result);
    }
}
