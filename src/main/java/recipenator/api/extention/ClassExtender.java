package recipenator.api.extention;

public class ClassExtender {
    public static void extendBy(Class extension) {
        ClassExtension ext = (ClassExtension) extension.getAnnotation(ClassExtension.class);
        if (ext == null)
            throw new RuntimeException("Extension class not annotated: " + extension.getName());
        org.luaj.vm2.lib.jse.ClassExtender.extend(ext.value(), extension);
    }
}