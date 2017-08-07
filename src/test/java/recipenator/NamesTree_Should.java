package recipenator;

import org.junit.Test;
import recipenator.utils.NamesTree;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NamesTree_Should {
    @Test
    public void ParseNames() {
        try {
            List<String> names = Files.readAllLines(Paths.get(".\\src\\test\\names.txt"));
            NamesTree.NameNode root = NamesTree.getTreeRoot(names);
            Files.write(Paths.get(".\\src\\test\\names_internal.txt"), root.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}