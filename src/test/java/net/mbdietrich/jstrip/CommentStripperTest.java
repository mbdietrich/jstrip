package net.mbdietrich.jstrip;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class CommentStripperTest {

    @RunWith(Parameterized.class)
    public static class CommentStripperFileAssertions {

        private final String input;
        private final String expectedOutput;
        private final String testName;

        public CommentStripperFileAssertions(String testName, String input, String expectedOutput) {
            super();
            this.testName = testName;
            this.input = input;
            this.expectedOutput = expectedOutput;
        }

        private static String[] allInputFiles() {
            File[] inputs = new File("src/test/resources/inputs").listFiles();

            return Stream.of(inputs).map(f -> f.getAbsolutePath().toString()).toArray(String[]::new);
        }

        private static String outputPathFor(String input) {
            return input.replace("inputs", "outputs");
        }

        @Parameterized.Parameters
        public static Collection<String[]> loadFixtures() throws IOException {
            Collection<String[]> fixtures = new ArrayList<String[]>();

            for (String inputPath : allInputFiles()) {
                String testName = new File(inputPath).getName();

                String outputPath = outputPathFor(inputPath);
                String inputStr = Files.readString(Paths.get(inputPath));
                String outputStr = Files.readString(Paths.get(outputPath));

                fixtures.add(new String[]{testName, inputStr, outputStr});
            }
            return fixtures;
        }

        @Test
        public void testTransform() {
            System.out.println("Testing "+testName);

            String output = CommentStripper.strip(input);
            assertEquals(expectedOutput, output);
        }
    }

    public static class CommentStripperFailCases {
        @Test(expected = IllegalArgumentException.class)
        public void testInvalidInput() {
            String input = """
                        // Invalid java code
                        public class Invalid {
                          this shouldn't be here            
                    """;

            CommentStripper.strip(input);
        }
    }
}