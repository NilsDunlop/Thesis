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

    private static void countObjectFields(File javaFile) {
        JavaParser javaParser = new JavaParser();
        try {
            ParseResult<CompilationUnit> parseResult = javaParser.parse(javaFile);

            if (parseResult.isSuccessful()) {
                CompilationUnit compilationUnit = parseResult.getResult().orElseThrow();
                AtomicInteger count = new AtomicInteger();

                compilationUnit.walk(node -> {
                    if (node instanceof FieldDeclaration) {
                        FieldDeclaration fieldDeclaration = (FieldDeclaration) node;
                        fieldDeclaration.getVariables().forEach(variableDeclarator -> {
                            if (variableDeclarator.getType().isReferenceType()) {
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
