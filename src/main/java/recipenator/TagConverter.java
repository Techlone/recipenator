package recipenator;

import net.minecraft.nbt.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class TagConverter {
    private final StringBuilder sb;
    private int deep;

    public TagConverter(NBTTagCompound tag) {
        this.deep = -1;
        this.sb = new StringBuilder();
        writeCompound(tag);
    }

    private void writeCompound(NBTTagCompound tag) {
        deep++;
        Set<String> names = tag.func_150296_c();
        if (names.size() == 0) {
            sb.append("{}");
            return;
        }
        sb.append("{");
        for (String tagName : names) {
            sb.append(System.lineSeparator());
            sb.append(repeat("  ", deep));
            if (isValidLuaId(tagName)) sb.append(tagName);
            else sb.append("[\"").append(tagName).append("\"]");
            writeTag(sb.append(" = "), tag.getTag(tagName));
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("}");
        deep--;
    }

    private void writeTag(StringBuilder sb, NBTBase tag) {
        if (tag instanceof NBTTagByte) {
            sb.append("nbt.byte(").append(((NBTTagByte) tag).func_150290_f()).append(")");
        } else if (tag instanceof NBTTagShort) {
            sb.append("nbt.short(").append(((NBTTagShort) tag).func_150289_e()).append(")");
        } else if (tag instanceof NBTTagInt) {
            sb.append(((Integer) ((NBTTagInt) tag).func_150287_d()).toString());
        } else if (tag instanceof NBTTagLong) {
            sb.append("nbt.float(").append(((NBTTagLong) tag).func_150291_c()).append(")");
        } else if (tag instanceof NBTTagFloat) {
            sb.append("nbt.float(").append(((NBTTagFloat) tag).func_150288_h()).append(")");
        } else if (tag instanceof NBTTagDouble) {
            sb.append(((Double) ((NBTTagDouble) tag).func_150286_g()).toString());
        } else if (tag instanceof NBTTagString) {
            sb.append("\"").append(((NBTTagString) tag).func_150285_a_()).append("\"");
        } else if (tag instanceof NBTTagByteArray) {
            byte[] bytes = ((NBTTagByteArray) tag).func_150292_c();
            String[] sBytes = new String[bytes.length];
            for (int i = 0; i < bytes.length; i++) sBytes[i] = String.valueOf(bytes[i]);
            sb.append("nbt.bytes(").append(String.join(", ", sBytes)).append(")");
        } else if (tag instanceof NBTTagIntArray) {
            int[] ints = ((NBTTagIntArray) tag).func_150302_c();
            String[] sInts = new String[ints.length];
            for (int i = 0; i < ints.length; i++) sInts[i] = String.valueOf(ints[i]);
            sb.append("nbt.ints(").append(String.join(", ", sInts)).append(")");
        } else if (tag instanceof NBTTagCompound) {
            writeCompound((NBTTagCompound) tag);
        } else if (tag instanceof NBTTagList) {
            writeList(sb, (NBTTagList) tag);
        }
    }

    private void writeList(StringBuilder sb, NBTTagList list) {
        List<NBTBase> nbtList = getNbtList(list);
        if (nbtList.size() == 0) {
            sb.append("{}");
            return;
        }
        writeTag(sb.append("{"), nbtList.get(0));
        for (int i = 1; i < nbtList.size(); i++) {
            writeTag(sb.append(", "), nbtList.get(i));
        }
        sb.append("}");
    }

    private List<NBTBase> getNbtList(NBTTagList list) {
        try {
            Field tagList = NBTTagList.class.getDeclaredField("tagList");
            if (!tagList.isAccessible())
                tagList.setAccessible(true);
            return (List<NBTBase>) tagList.get(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String repeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }

    private boolean isValidLuaId(String name) {
        if (!Character.isLetter(name.charAt(0)) && name.charAt(0) != '_') return false;
        for (char symbol : name.toCharArray()) {
            if (!Character.isLetterOrDigit(symbol) && symbol != '_') return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
