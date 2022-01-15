package net.mbdietrich.jstrip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class CommentStripperTest {

    // Happy-path tests. Load the `input` files and check the result matches
    // the corresponding `output` file.
    @RunWith(Parameterized.class)
    public static class CommentStripperInputTests {

        private final String testName;
        private final String input;
        private final String expectedOutput;

        public CommentStripperInputTests(String testName, String input, String expectedOutput) {
            super();
            this.testName = testName;
            this.input = input;
            this.expectedOutput = expectedOutput;
        }

        // List of absolute paths of input files
        private static String[] allInputFiles() {
            File[] inputs = new File("src/test/resources/inputs").listFiles();

            return Stream.of(inputs)
                         .map(f -> f.getAbsolutePath().toString())
                         .toArray(String[]::new);
        }

        // Given an input path, return the corresponding path for the expected output
        private static String outputPathFor(String input) {
            return input.replace("inputs", "outputs");
        }

        // Load the test matrix
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
        public void runTestScenario() {
            System.out.println("Testing "+testName);

            String output = CommentStripper.strip(input);
            assertEquals(expectedOutput, output);
        }
    }

    // Fail-path tests. Load the `failCases` files and confirm each of them raises
    // an IllegalArgumentException.
    @RunWith(Parameterized.class)
    public static class CommentStripperFailureTests {

        private final String testName;
        private final String input;

        public CommentStripperFailureTests(String testName, String input) {
            super();
            this.testName = testName;
            this.input = input;
        }

        // List of absolute paths of fail-case files
        private static String[] allFailCaseFiles() {
            File[] inputs = new File("src/test/resources/failCases").listFiles();

            return Stream.of(inputs)
                         .map(f -> f.getAbsolutePath().toString())
                         .toArray(String[]::new);
        }

        // Load the test matrix
        @Parameterized.Parameters
        public static Collection<String[]> loadFixtures() throws IOException {
            Collection<String[]> fixtures = new ArrayList<String[]>();

            for (String inputPath : allFailCaseFiles()) {
                String testName = new File(inputPath).getName();
                String inputStr = Files.readString(Paths.get(inputPath));

                fixtures.add(new String[]{testName, inputStr});
            }
            return fixtures;
        }

        @Test(expected = IllegalArgumentException.class)
        public void testErrorIsThrown() {
            CommentStripper.strip(input);
        }
    }
}