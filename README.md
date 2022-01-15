# jstrip

Strips comments from valid Java code. Built as a solution to the following assignment:

> Write a function that takes a string parameter and returns a string. The input will be the contents
of a Java source code file. The output will be the same code without any comments.
> 
 ## Usage

As a library:
 
```java
import net.mbdietrich.jstrip.CommentStripper;

// ...

String input = Files.readString(Paths.get("/path/to/code"));

String output = CommentStripper.strip(input); // Returns LINTed code, with comments removed
```

As an executable:

```bash
cat <FILENAME> | java -jar jstrip-0.0.1.jar
```

## Discussion

Identifying and removing comments can be completed with regular expressions; however, comments may also be present in a String literal. This could still be handled by a regular expression, but is more cleanly handled by interpreting the AST and removing any comment nodes.

[javaparser](https://github.com/javaparser/javaparser) is an open source tool with an active community, designed for quickly parsing and manipulating ASTs. The input string is parsed, and if it is valid, the comment nodes are removed, and the transformed AST output as a string.

## AST vs. Regular Expression

### Pros

- Integrates with a tool built around formal Java grammar, minimizing risk of defects/unhandled edge cases
- Easier to maintain in case of future changes. Regular expressions can be cumbersome to reverse-engineer, vs. a simple API that abstracts away syntax rules.
- LINTing and validation configurable in the solution.
- Can be integrated either as a dependency, or as a standalone executable.

### Cons

- Larger total codebase
- Dependency on third-party libraries, potentially introduces bugs and vulnerabilities.
- A JVM cold start for each scenario may introduce performance issues.
- Dependent on library updates to support future language versions. At time of writing, Java 13 is supported, with partial support for Java 14.
- Less guarantees on preservation of the prevailing code style. Theoretically possible with [LexicalPreservationPrinter](https://www.javadoc.io/doc/com.github.javaparser/javaparser-core/latest/com/github/javaparser/printer/lexicalpreservation/LexicalPreservingPrinter.html) but it has associated open issues. 
