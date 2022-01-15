package net.mbdietrich.jstrip;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * Thin wrapper for building the executable JAR.
 *
 * @author mbdietrich
 */
public class Main {

    public static void main(String[] args){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            StringBuilder input = new StringBuilder();
            String nextLine = null;
            do {
                nextLine = reader.readLine();
                if(nextLine != null) {
                    input.append(nextLine+'\n');
                }
            } while(nextLine != null);

            System.out.println(CommentStripper.strip( input.toString().strip() ));
        } catch (Exception e) {
            System.err.println(e);
        }


    }
}
