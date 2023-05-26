package nils.dunlop.thesis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ConstructorDeclaration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class PrivateConstructorCounter {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                JavaParser javaParser = new JavaParser();
                CompilationUnit cu = javaParser.parse(Paths.get(args[0]).toFile()).getResult().orElse(null);

                if (cu != null) {
                    AtomicInteger count = new AtomicInteger();

                    // Find all constructor declarations in the compilation unit and filter for private constructors
                    cu.findAll(ConstructorDeclaration.class).stream()
                            .filter(c -> c.getModifiers().contains(Modifier.privateModifier()))
                            .forEach(c -> {
                                // Increment the counter and print information about each private constructor
                                count.getAndIncrement();
                                String constructorName = c.getNameAsString();
                                String constructorLocation = c.getBegin().map(Position::toString).orElse("unknown");

                                System.out.println("Private Constructor: " + constructorName +
                                        ", Location: " + constructorLocation);
                            });

                    System.out.println("Total private constructors: " + count);
                } else {
                    System.err.println("Error parsing the source file: Could not obtain CompilationUnit.");
                }
            } catch (IOException | ParseProblemException e) {
                System.err.println("Error parsing the source file: " + e.getMessage());
            }
        } else {
            System.err.println("Usage: java PrivateConstructorCounter <sourceFilePath>");
        }
    }
}