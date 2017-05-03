package org.luaj.vm2.lib.jse;

public class ClassExtender {
    public static void extend(Class base, Class extension) {
        JavaClass.forClass(base).extendBy(extension);
    }
}
