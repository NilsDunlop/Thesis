package nils.dunlop.thesis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.Position;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

public class AbstractMethodCounter {

    // Method for counting abstract methods in a Java source file
    public static void countAbstractMethods(String sourceFilePath) {
        try {
            // Read the source file
            FileInputStream fileInputStream = new FileInputStream(sourceFilePath);
            JavaParser javaParser = new JavaParser();

            // Parse the file into a compilation unit
            CompilationUnit compilationUnit = javaParser.parse(fileInputStream).getResult().orElseThrow();

            // Counter for tracking the number of abstract methods
            AtomicInteger count = new AtomicInteger();

            // Visitor to traverse the AST and identify abstract methods
            VoidVisitorAdapter<Object> visitor = new VoidVisitorAdapter<>() {
                @Override
                public void visit(MethodDeclaration methodDeclaration, Object arg) {
                    super.visit(methodDeclaration, arg);

                    // Check if the method is abstract
                    if (methodDeclaration.isAbstract()) {
                        count.incrementAndGet();
                        System.out.println("Abstract Method: " + methodDeclaration.getNameAsString() +
                                " Node type: " + methodDeclaration.getClass().getSimpleName() +
                                " Location: " + methodDeclaration.getBegin().map(Position::toString).orElse("unknown"));
                    }
                }
            };

            // Visit the compilation unit to identify abstract methods
            visitor.visit(compilationUnit, null);
            System.out.println("Total abstract methods found: " + count.get());
        } catch (FileNotFoundException e) {
            // Handle file not found exception
            System.err.println("File not found: " + e.getMessage());
        } catch (ParseProblemException e) {
            // Handle parse problem exception
            System.err.println("Parsing problem: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            // Call the method to count abstract methods with the provided source file path
            countAbstractMethods(args[0]);
        } else {
            // Display usage information if no source file path is provided
            System.err.println("Usage: java AbstractMethodCounter <sourceFilePath>");
        }
    }
}