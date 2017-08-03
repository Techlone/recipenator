package recipenator.lualibs;

import net.minecraft.nbt.NBTTagByte;
import recipenator.api.extention.LuaName;

public class NbtProvider {
    @LuaName("byte")
    public static NBTTagByte toByte(int num) {
        return new NBTTagByte((byte) num);
    }
}
