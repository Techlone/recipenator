package recipenator;

import net.minecraft.nbt.NBTTagCompound;
import org.junit.Assert;
import org.junit.Test;
import recipenator.utils.RecipeHelper;

import java.util.HashMap;
import java.util.Map;

public class RecipeHelper_Should {
    @Test
    public void MakeRectangularMatrix() {
        String[][] input = {{"11", "12"}, {"21"}, {"31", "32", "33"}};
        String[][] output = {{"11", "12", null}, {"21", null, null}, {"31", "32", "33"}};

        RecipeHelper.Size size = RecipeHelper.rectangulate(input);

        Assert.assertEquals(3, size.width);
        Assert.assertEquals(3, size.height);
        for (int i = 0; i < input.length; i++) {
            Assert.assertArrayEquals(output[i], input[i]);
        }
    }

    @Test
    public void ComparesNbt() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("name", "Drill");
            put("tier", 3);
        }};
        NBTTagCompound input = createNbt(map);

        map.put("charge", 123123123.123);
        NBTTagCompound base = createNbt(map);

        Assert.assertTrue(RecipeHelper.doesTagContain(base, input));
        Assert.assertFalse(input.equals(base));
    }

    private NBTTagCompound createNbt(Map<String, Object> map) {
        NBTTagCompound tag = new NBTTagCompound();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String)
                tag.setString(entry.getKey(), (String) entry.getValue());
            if (entry.getValue() instanceof Integer)
                tag.setInteger(entry.getKey(), (Integer) entry.getValue());
            if (entry.getValue() instanceof Double)
                tag.setDouble(entry.getKey(), (Double) entry.getValue());
        }
        return tag;
    }
}
