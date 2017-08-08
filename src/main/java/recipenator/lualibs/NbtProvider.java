package recipenator.lualibs;

import net.minecraft.nbt.*;
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

    @LuaName("bytes")
    public static NBTTagByteArray toBytes(byte... bytes) {
        return new NBTTagByteArray(bytes);
    }

    @LuaName("ints")
    public static NBTTagIntArray toInts(int... ints) {
        return new NBTTagIntArray(ints);
    }
}
