package recipenator.components;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import recipenator.api.component.IRecipeComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OrePatternComponent extends OreComponent {
    private final List<String> ids = new ArrayList<>();

    public OrePatternComponent(String orePattern) {
        this(orePattern, 1, 0, null);
    }

    protected OrePatternComponent(String orePattern, int count, int meta, NBTTagCompound tag) {
        super(orePattern, count, meta, tag);
        initIds(orePattern);
    }

    private void initIds(String orePattern) {
        Pattern pattern = Pattern.compile(orePattern.replace("*", ".+"));
        for (String id : OreDictionary.getOreNames()) {
            if (pattern.matcher(id).matches()) this.ids.add(id);
        }
    }

    @Override
    public void add(IRecipeComponent<?>... components) {
        for (String id : ids) {
            for (IRecipeComponent<?> component : components) {
                for (ItemStack item : component.getAllItems()) {
                    OreDictionary.registerOre(id, item);
                }
            }
        }
    }

    @Override
    public List<ItemStack> getRecipeItem() {
        return ids.stream().flatMap(s -> OreDictionary.getOres(s).stream()).collect(Collectors.toList());
    }

    @Override
    protected IRecipeComponent<List<ItemStack>> newInstance(int count, int meta, NBTTagCompound tag) {
        return new OrePatternComponent(id, count, meta, tag);
    }
}
