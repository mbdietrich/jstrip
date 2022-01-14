package net.mbdietrich.jstrip;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

public class CommentStripper {
    public static String strip(String input){
        try {
            JavaParser javaParser = new JavaParser();
            ParseResult<CompilationUnit> result = javaParser.parse(input);
            if(!result.isSuccessful()){
                throw new IllegalArgumentException("Invalid Java: "+result.getProblems());
            }
            CompilationUnit tree = result.getResult().get();

            stripComments(tree);

            return tree.toString();
        } catch (ParseProblemException e) {
            throw new IllegalArgumentException("Invalid Java: "+e.getMessage());
        }
    }

    // Remove all comment nodes from the AST
    private static void stripComments(Node tree){
        for(Node comment : tree.getAllContainedComments()){
            comment.remove();
        }
    }
}
