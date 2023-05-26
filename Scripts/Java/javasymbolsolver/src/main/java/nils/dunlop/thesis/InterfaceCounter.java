package nils.dunlop.thesis;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import com.github.javaparser.Position;
import java.io.File;
import java.nio.file.Paths;

public class InterfaceCounter {
    public static void main(String[] args) {
        File file = new File(args[0]);
        if (!file.exists()) {
            System.err.println("File not found.");
            System.exit(1);
        }

        ParserConfiguration config = new ParserConfiguration();
        SourceRoot sourceRoot = new SourceRoot(Paths.get(file.getParent()), config);

        try {
            CompilationUnit cu = sourceRoot.parse("", file.getName());
            InterfaceVisitor visitor = new InterfaceVisitor();
            visitor.visit(cu, null);
            System.out.println("Total interfaces found: " + visitor.getInterfaceCount());
        } catch (ParseProblemException e) {
            System.err.println("Error parsing the Java source file.");
            e.printStackTrace();
        }
    }

    private static class InterfaceVisitor extends VoidVisitorAdapter<Void> {
        private int interfaceCount;

        public int getInterfaceCount() {
            return interfaceCount;
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, Void arg) {
            if (!classOrInterfaceDeclaration.isInterface()) {
                super.visit(classOrInterfaceDeclaration, arg);
                for (ClassOrInterfaceType implementedInterface : classOrInterfaceDeclaration.getImplementedTypes()) {
                    interfaceCount++;
                    System.out.println(
                            "Found implemented interface '" + implementedInterface.getNameAsString() +
                                    "' in class '" + classOrInterfaceDeclaration.getNameAsString() +
                                    "' at line " + implementedInterface.getBegin().map(Position::toString).orElse("unknown"));
                }
            }
        }
    }
}