package nils.dunlop.thesis;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

public class StaticFieldCounter {

    public static void main(String[] args) {
        if (args.length > 0) {
            File sourceFile = new File(args[0]);
            if (!sourceFile.exists() || !sourceFile.isFile()) {
                System.err.println("Error: Invalid source file path.");
                System.exit(1);
            }

            try {
                printStaticFields(sourceFile.toPath());
            } catch (IOException e) {
                System.err.println("Error: Unable to parse the source file.");
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            System.err.println("Usage: java StaticFieldCounter <sourceFilePath>");
        }
    }

    private static void printStaticFields(Path sourceFilePath) throws IOException {
        ParserConfiguration configuration = new ParserConfiguration();

        // Create a SourceRoot instance to parse the source file
        SourceRoot sourceRoot = new SourceRoot(sourceFilePath.getParent(), configuration);

        // Parse the source file and obtain the CompilationUnit
        CompilationUnit compilationUnit = sourceRoot.parse("", sourceFilePath.getFileName().toString());

        AtomicInteger count = new AtomicInteger();

        // Find all field declarations in the compilation unit and filter for static fields
        compilationUnit.findAll(FieldDeclaration.class)
                .stream()
                .filter(field -> field.getModifiers().contains(Modifier.staticModifier()))
                .forEach(field -> {
                    // Increment the counter and print information about each static field
                    count.getAndIncrement();
                    String fieldName = field.getVariable(0).getNameAsString();
                    String fieldType = field.getElementType().toString();
                    String fieldLocation = field.getBegin().map(Position::toString).orElse("unknown");

                    System.out.println("Static Field Name: " + fieldName +
                            ", Type: " + fieldType +
                            ", Location: " + fieldLocation);
                });

        System.out.println("Total static fields: " + count);
    }
}