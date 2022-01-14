package net.mbdietrich.jstrip;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CommentStripperTest {

    private final String input;
    private final String expectedOutput;

    public CommentStripperTest(String input, String expectedOutput) {
        super();
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    private static String[] allInputFiles(){
        File[] inputs = new File("src/test/resources/inputs").listFiles();

        return Stream.of(inputs).map(f -> f.getAbsolutePath().toString()).toArray(String[]::new);
    }

    private static String outputPathFor(String input){
        return input.replace("inputs", "outputs");
    }

    @Parameterized.Parameters
    public static Collection<String[]> loadFixtures() throws IOException {
        Collection<String[]> fixtures = new ArrayList<String[]>();

        for(String inputPath : allInputFiles()){
            String outputPath = outputPathFor(inputPath);
            String inputStr = Files.readString(Paths.get(inputPath));
            String outputStr = Files.readString(Paths.get(outputPath));

            fixtures.add(new String[]{inputStr, outputStr});
        }
        return fixtures;
    }

    @Test
    public void testTransform() {
        String output = CommentStripper.strip(input);
        assertEquals(output, expectedOutput);
    }

}
