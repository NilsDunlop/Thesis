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

    public static void countAbstractMethods(String sourceFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFilePath);
            JavaParser javaParser = new JavaParser();
            CompilationUnit compilationUnit = javaParser.parse(fileInputStream).getResult().orElseThrow();

            AtomicInteger count = new AtomicInteger();
            VoidVisitorAdapter<Object> visitor = new VoidVisitorAdapter<>() {
                @Override
                public void visit(MethodDeclaration methodDeclaration, Object arg) {
                    super.visit(methodDeclaration, arg);
                    if (methodDeclaration.isAbstract()) {
                        count.incrementAndGet();
                        System.out.println("Abstract Method: " + methodDeclaration.getNameAsString() +
                                " Node type: " + methodDeclaration.getClass().getSimpleName() +
                                " Location: " + methodDeclaration.getBegin().map(Position::toString).orElse("unknown"));
                    }
                }
            };

            visitor.visit(compilationUnit, null);
            System.out.println("Total abstract methods found: " + count.get());
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (ParseProblemException e) {
            System.err.println("Parsing problem: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            countAbstractMethods(args[0]);
        } else {
            System.err.println("Usage: java AbstractMethodCounter <sourceFilePath>");
        }
    }
}
