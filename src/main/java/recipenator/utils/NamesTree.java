package recipenator.utils;

import java.util.*;

public class NamesTree {
    public static NameNode getTreeRoot(Collection names) {
        NameNode nn = new NameNode();

        Map<String, List<String>> nameToNodes = new HashMap<>();
        for (Object o : names) {
            String s = String.valueOf(o);
            nameToNodes.put(s, NamesTree.getNameNodes(s));
        }

        for (String name : nameToNodes.keySet()) {
            NameNode current = nn;
            for (String id : nameToNodes.get(name)) {
                NameNode next = current.next(id);
                if (next == null)
                    current.children.put(id, next = new NameNode());
                current = next;
            }
            current.name = name;
        }

        return nn;
    }

    public static List<String> getNameNodes(String name) {
        char[] data = name.toCharArray();
        int i = 0;
        int l = data.length;
        List<String> result = new ArrayList<>();

        do {
            int j = i;
            while (j < l && isInternalSymbol(data[j])) { //take id
                data[j] = Character.toLowerCase(data[j]);
                j++;
            }
            result.add(String.copyValueOf(data, i, j - i));
            i = j;
            while (i < l && !Character.isLetterOrDigit(data[i])) i++; //skip bad chars
        } while (i < l);

        return result;
    }

    public static boolean isInternalSymbol(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    public static class NameNode {
        private final Map<String, NameNode> children = new HashMap<>();
        private String name;

        public NameNode() {
            this(null);
        }

        public NameNode(String name) {
            this.name = name;
        }

        public NameNode next(String id) {
            return children.get(id);
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return toStringBuilder("", "\t").toString();
        }

        private StringBuilder toStringBuilder(String inittab, String addtab) {
            String nl = System.lineSeparator();
            StringBuilder sb = new StringBuilder();
            if (name != null) sb.append(inittab).append("Name: ").append(name);
            if (children.isEmpty()) return sb;
            if (name != null) sb.append(nl);
            for (Map.Entry<String, NameNode> data : children.entrySet()) {
                StringBuilder nsb = data.getValue().toStringBuilder(inittab + addtab, addtab);
                sb.append(nl).append(inittab).append(data.getKey()).append(nl).append(nsb);
            }
            return sb;
        }
    }
}
