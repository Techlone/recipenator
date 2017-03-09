package recipenator;

import org.junit.Test;
import recipenator.utils.NamesTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NamesTree_Should {
    //@Test
    public void ParseNameToListNodes() {
        List<String> names;
        try {
            names = Files.readAllLines(Paths.get(".\\src\\test\\names.txt"));
        } catch (IOException ignored) {
            throw new RuntimeException("All is gone!");
        }
        names.forEach(s -> s.toString());
        String name = "appliedenergistics2:item.ItemBasicStorageCell.16k";
        int TIMES = 1;
        long start;

        System.out.print("My ");
        start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            Set<List<String>> collect = names.stream()
                    .map(NamesTree::getNameNodes)
                    .collect(Collectors.toSet());
        }
        System.out.println(System.currentTimeMillis() - start);

        System.out.print("JVM ");
        start = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            //names.forEach(s -> s.toLowerCase().split("\\W+"));
            Set<String[]> collect = names.stream()
                    .map(s -> s.toLowerCase().split("\\W+"))
                    .collect(Collectors.toSet());
            name.toLowerCase().split("\\W+");
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void ParseNames_FromFile() {
        List<String> names;
        try {
            names = Files.readAllLines(Paths.get(".\\src\\test\\names.txt"));
        } catch (IOException ignored) {
            throw new RuntimeException("All is gone!");
        }

        NamesTree.NameNode root = NamesTree.getTreeRoot(names);
        try {
            Files.write(Paths.get(".\\src\\test\\names_internal.txt"), root.toString().getBytes());
        } catch (IOException ignored) {
            throw new RuntimeException("Everything is gone!");
        }
    }
}