package recipenator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;
import recipenator.utils.NamesTree;

import java.util.Collections;

public class ForgeEventHandler {
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            event.toolTip.add("");
            String luaName = NamesTree.getLuaName(event.itemStack);
            event.toolTip.add(luaName);

            if (event.itemStack.stackTagCompound != null) {
                String tag = new TagConverter(event.itemStack.stackTagCompound).toString();
                String[] lines = tag.split(System.lineSeparator());
                lines[0] = ":tag(" + lines[0];
                lines[lines.length - 1] += ")";
                Collections.addAll(event.toolTip, lines);
            }

            for (int i : OreDictionary.getOreIDs(event.itemStack))
                event.toolTip.add(EnumChatFormatting.ITALIC + "- " + OreDictionary.getOreName(i));
        }
    }
}
