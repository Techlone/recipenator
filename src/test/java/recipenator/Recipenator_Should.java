package recipenator;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Recipenator_Should {
    @Test
    public void PrepareName() {
        Path p1 = Paths.get("C:\\asd\\zyx\\folder");
        Path p2 = Paths.get("C:\\asd\\zyx\\mydir\\test");
        Path pr = p1.relativize(p2);
        System.out.println(System.getProperty("user.dir"));
        System.out.println(pr);
    }
}
