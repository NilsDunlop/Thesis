package nils.dunlop.thesis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class ObjectFieldCounter {

    public static void main(String[] args) {
        if (args.length > 0) {
            File javaFile = new File(args[0]);

            if (!javaFile.exists() || !javaFile.isFile()) {
                System.out.println("File does not exist or is not a valid Java file.");
                return;
            }

            countObjectFields(javaFile);
        } else {
            System.err.println("Usage: java ObjectFieldCounter <sourceFilePath>");
        }
    }

    // Method to count the number of object fields in a Java file
    private static void countObjectFields(File javaFile) {
        JavaParser javaParser = new JavaParser();
        try {
            ParseResult<CompilationUnit> parseResult = javaParser.parse(javaFile);

            if (parseResult.isSuccessful()) {
                CompilationUnit compilationUnit = parseResult.getResult().orElseThrow();
                AtomicInteger count = new AtomicInteger();

                // Traverse the compilation unit's AST nodes
                compilationUnit.walk(node -> {
                    // Check if the node represents a field declaration
                    if (node instanceof FieldDeclaration) {
                        FieldDeclaration fieldDeclaration = (FieldDeclaration) node;
                        // Iterate over the variables in the field declaration
                        fieldDeclaration.getVariables().forEach(variableDeclarator -> {
                            // Check if the variable's type is a reference type (object)
                            if (variableDeclarator.getType().isReferenceType()) {
                                // Increment the counter and print information about the object field
                                count.incrementAndGet();
                                System.out.printf(
                                        "Object Field: %s, Node Type: %s, Location: %s%n",
                                        variableDeclarator.getNameAsString(),
                                        node.getClass().getSimpleName(),
                                        node.getBegin().orElseThrow()
                                );
                            }
                        });
                    }
                });

                System.out.printf("Total object fields: %d%n", count.get());
            } else {
                System.out.println("Failed to parse the Java file.");
            }
        } catch (IOException e) {
            System.out.println("Error reading the Java file: " + e.getMessage());
        }
    }
}