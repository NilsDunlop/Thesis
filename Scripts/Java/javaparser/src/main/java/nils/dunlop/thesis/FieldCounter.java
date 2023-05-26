package nils.dunlop.thesis;

import java.io.File;
import com.github.javaparser.Position;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.type.Type;

public class FieldCounter {
    public static void printFields(File file) throws Exception {
        // Parse the Java file into a CompilationUnit
        CompilationUnit cu = StaticJavaParser.parse(file);
        int count = 0;
        // Find all field declarations in the compilation unit
        for (FieldDeclaration field : cu.findAll(FieldDeclaration.class)) {
            count++;
            // Get the name, type, and location of each field
            String fieldName = field.getVariable(0).getNameAsString();
            Type fieldType = field.getElementType();
            String fieldLocation = field.getBegin().map(Position::toString).orElse("unknown");

            System.out.println("Field Name: " + fieldName +
                    ", Field Type: " + fieldType +
                    ", Field Location: " + fieldLocation);
        }
        System.out.println("Total amount of fields found: " + count);
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            File file = new File(args[0]);
            // Call the printFields method with the provided file
            printFields(file);
        } else {
            System.err.println("Usage: java FieldCounter <sourceFilePath>");
        }
    }
}