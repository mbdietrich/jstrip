package net.mbdietrich.jstrip;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

/**
 * A utility class for removing comments from a String of valid Java code.
 * Call {@link #strip(String)}.
 *
 * @author mbdietrich
 */
public class CommentStripper {

    /**
     * Given a String containing valid Java code, returns the equivalent code with
     * all comments removed. The code will be linted and validated.
     *
     * @param input The Java code to be stripped of comments.
     * @return The equivalent code, without any comments.
     * @throws IllegalArgumentException Thrown if the input could not be parsed as valid Java.
     */
    public static String strip(String input){
            // Parse the input string and validate it
            ParseResult<CompilationUnit> result = new JavaParser().parse(input);
            if(!result.isSuccessful()){
                throw new IllegalArgumentException("Invalid Java: "+result.getProblems());
            }

            // Extract the tree from the result and remove all Comment nodes
            CompilationUnit tree = result.getResult().get();
            tree.getAllContainedComments()
                    .stream()
                    .forEach(c -> c.remove());

            // Return the tree as a String
            return tree.toString();
    }
}
