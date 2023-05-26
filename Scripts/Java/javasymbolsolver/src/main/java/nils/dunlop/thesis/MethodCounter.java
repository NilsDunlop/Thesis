package nils.dunlop.thesis;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;

public class MethodCounter {
    public static void main(String[] args) {
        File sourceFile = new File(args[0]);
        if (!sourceFile.exists()) {
            System.err.println("Error: File not found.");
            System.exit(1);
        }

        try {
            // Create a JavaParser instance
            JavaParser javaParser = new JavaParser();

            // Parse the source file
            CompilationUnit cu = javaParser.parse(sourceFile).getResult().orElseThrow(() -> new ParseException("Failed to parse the source file."));

            // Create a MethodVisitor instance
            MethodVisitor methodVisitor = new MethodVisitor();

            // Visit the compilation unit to count methods
            methodVisitor.visit(cu, null);

            System.out.println("Total methods found: " + methodVisitor.getMethodCount());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        private int methodCount = 0;

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);

            // Increment the method count
            methodCount++;

            // Print information about the method
            System.out.println("Method: " + n.getName() + " | " + "Node type: " + n.getClass().getSimpleName() + " | " + "Location: " + n.getRange().map(Object::toString).orElse("Unknown"));
        }

        public int getMethodCount() {
            return methodCount;
        }
    }
}