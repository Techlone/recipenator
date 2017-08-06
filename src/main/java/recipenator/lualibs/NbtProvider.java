package recipenator.lualibs;

import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import recipenator.api.extention.LuaName;

public class NbtProvider {
    @LuaName("byte")
    public static NBTTagByte toByte(double num) {
        return new NBTTagByte((byte) num);
    }

    @LuaName("short")
    public static NBTTagShort toShort(double num) {
        return new NBTTagShort((short) num);
    }

    @LuaName("long")
    public static NBTTagLong toLong(double num) {
        return new NBTTagLong((long) num);
    }

    @LuaName("float")
    public static NBTTagFloat toFloat(double num) {
        return new NBTTagFloat((float) num);
    }
}
