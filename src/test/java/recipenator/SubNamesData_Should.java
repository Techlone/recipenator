package recipenator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SubNamesData_Should {
    @Test
    public void FindWildcard_InName() {
        String s = "name*asd";
        boolean b = s.contains("*");
        Assert.assertTrue(b);
    }

    @Test
    public void test() {
//        String[] list = {"asd", "qwe", "zxc", "rty", "fgh"};
        String[] list = {"asd"};
        String[] sublist = Arrays.copyOfRange(list, 1, list.length);
        Assert.assertArrayEquals(new String[]{}, sublist);
    }
}