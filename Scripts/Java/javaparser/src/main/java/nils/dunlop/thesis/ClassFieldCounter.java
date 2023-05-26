package nils.dunlop.thesis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassFieldCounter {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java ClassFieldCounter <path_to_java_file>");
            System.exit(1);
        }

        String filePath = args[0];
        try {
            new ClassFieldCounter().countClassFields(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }

    private void countClassFields(String filePath) throws IOException {
        CompilationUnit cu;
        try {
            cu = new JavaParser().parse(Paths.get(filePath)).getResult().orElse(null);
        } catch (ParseProblemException e) {
            throw new IOException("Could not parse file: " + filePath, e);
        }

        if (cu != null) {
            cu.accept(new ClassVisitor(), null);
        } else {
            System.out.println("No content could be parsed from the file.");
        }
    }

    private static class ClassVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Void arg) {
            super.visit(n, arg);

            AtomicInteger count = new AtomicInteger();
            n.getMembers().stream()
                    .filter(member -> member instanceof FieldDeclaration)
                    .map(member -> (FieldDeclaration) member)
                    .forEach(field -> {
                        field.getVariables().forEach(variable -> {
                            if (variable.getType().asString().equals(n.getNameAsString())) {
                                count.getAndIncrement();
                                System.out.println("Found field of class's own type in class " + n.getName()
                                        + " at line " + field.getBegin().get().line);
                            }
                        });
                    });
            System.out.println("Number of fields of own type in class " + n.getName() + ": " + count.get());
        }
    }
}