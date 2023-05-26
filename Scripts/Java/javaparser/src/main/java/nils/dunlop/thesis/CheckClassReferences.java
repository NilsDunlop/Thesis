package nils.dunlop.thesis;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.File;
import java.io.IOException;

public class CheckClassReferences {

    public static void main(String[] args) throws IOException {
        // Replace the file path with the path to your Java file
        File javaFile = new File("C:\\Users\\nilsd\\Desktop\\Thesis\\PMART\\6 - JHotDraw v5.1\\JHotDraw v5.1\\src\\CH\\ifa\\draw\\standard\\DragTracker.java");
        CompilationUnit cu = StaticJavaParser.parse(javaFile);

        // Retrieve the class declaration from the compilation unit
        ClassOrInterfaceDeclaration classDecl = cu.getClassByName("DragTracker").orElse(null);
        if (classDecl != null) {
            // Check if the class is referred to by other classes
            boolean isReferredTo = new ClassReferenceVisitor(classDecl.getNameAsString()).isReferredTo(cu);
            if (isReferredTo) {
                System.out.println("The class " + classDecl.getNameAsString() + " is referred to by other classes.");
            } else {
                System.out.println("The class " + classDecl.getNameAsString() + " is not referred to by other classes.");
            }
        } else {
            System.out.println("The specified class was not found in the file.");
        }
    }

    /**
     * A visitor that checks whether a class is referred to by other classes.
     */
    private static class ClassReferenceVisitor extends VoidVisitorAdapter<Void> {
        private String className;
        private boolean isReferredTo = false;

        public ClassReferenceVisitor(String className) {
            this.className = className;
        }

        /**
         * Returns whether the class is referred to by other classes.
         */
        public boolean isReferredTo(CompilationUnit cu) {
            visit(cu, null);
            return isReferredTo;
        }

        @Override
        public void visit(NameExpr n, Void arg) {
            super.visit(n, arg);
            if (n.getNameAsString().equals(className)) {
                isReferredTo = true;
            }
        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            // Skip the method body to avoid visiting the class references within it
        }
    }
}