package nils.dunlop.thesis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InstanceCounter {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(args[0]);
        JavaParser javaParser = new JavaParser();
        ParseResult<CompilationUnit> parseResult = javaParser.parse(fileInputStream);
        CompilationUnit cu = parseResult.getResult().orElse(null);

        if (cu != null) {
            InstanceCreationVisitor visitor = new InstanceCreationVisitor();
            visitor.visit(cu, null);
            System.out.println("Methods generating instances: " + visitor.getInstanceCount());
        }
    }

    private static class InstanceCreationVisitor extends VoidVisitorAdapter<Void> {
        private int instanceCount = 0;

        int getInstanceCount() {
            return instanceCount;
        }

        @Override
        public void visit(MethodDeclaration methodDeclaration, Void arg) {
            methodDeclaration.getBody().ifPresent(body -> {
                body.findAll(ObjectCreationExpr.class).forEach(objectCreationExpr -> {
                    instanceCount++;
                    System.out.println("Node name: " + objectCreationExpr.getType().getNameAsString() + ", Type: " + objectCreationExpr.getType() + ", Location: " + objectCreationExpr.getRange().map(Object::toString).orElse("Unknown"));
                });
            });

            super.visit(methodDeclaration, arg);
        }
    }
}
