package recipenator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;

public class ForgeEventHandler {
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
            for (int i : OreDictionary.getOreIDs(event.itemStack)) {
                event.toolTip.add(EnumChatFormatting.ITALIC + "- " + OreDictionary.getOreName(i));
            }
    }
}
