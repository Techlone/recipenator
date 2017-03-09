package recipenator;

import org.junit.Assert;
import org.junit.Test;
import recipenator.utils.RecipeHelper;

import java.util.Objects;

public class RecipeHelper_Should {
    @Test
    public void Rectangulate() {
        String[][] input = {{"11", "12"}, {"21"}, {"31", "32", "33"}};
        String[][] output = {{"11", "12", null}, {"21", null, null}, {"31", "32", "33"}};

        RecipeHelper.Size size = RecipeHelper.rectangulate(input);

        Assert.assertEquals(3, size.width);
        Assert.assertEquals(3, size.height);
        for (int i = 0; i < input.length; i++) {
            Assert.assertArrayEquals(output[i], input[i]);
        }
    }
}
