package nils.dunlop.thesis;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
public class StaticMethodCounter {

    public static class StaticMethodVisitor extends VoidVisitorAdapter<AtomicInteger> {

        public static class ReturnStatementVisitor extends VoidVisitorAdapter<Void> {
            @Override
            public void visit(ReturnStmt n, Void arg) {
                super.visit(n, arg);
                System.out.print(" with return statement: " + n.toString().trim());
            }
        }

        @Override
        public void visit(MethodDeclaration n, AtomicInteger count) {
            super.visit(n, count);
            if (n.isStatic()) {
                System.out.print("Found static method: " + n.getName()
                        + " at line " + n.getRange().map(range -> range.begin.line).orElse(-1));

                ReturnStatementVisitor returnStatementVisitor = new ReturnStatementVisitor();
                n.accept(returnStatementVisitor, null);

                System.out.println();
                count.incrementAndGet();
            }
        }
    }

        public static void main(String[] args) {
        Path sourceFilePath = Paths.get(args[0]);
        try {
            JavaParser javaParser = new JavaParser();
            ParseResult<CompilationUnit> parseResult = javaParser.parse(sourceFilePath);
            if (parseResult.isSuccessful()) {
                CompilationUnit compilationUnit = parseResult.getResult().get();
                StaticMethodVisitor staticMethodVisitor = new StaticMethodVisitor();
                AtomicInteger staticMethodCount = new AtomicInteger(0);
                staticMethodVisitor.visit(compilationUnit, staticMethodCount);
                System.out.println("Total static methods found: " + staticMethodCount.get());
            } else {
                System.out.println("Failed to parse the Java source file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}