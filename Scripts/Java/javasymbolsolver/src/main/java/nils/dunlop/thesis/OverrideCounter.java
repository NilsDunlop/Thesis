package nils.dunlop.thesis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class OverrideCounter {

    public static void main(String[] args, String rootPath) {
        File file = new File(args[0]);
        File projectRoot = new File(rootPath);

        try {
            CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
            combinedTypeSolver.add(new ReflectionTypeSolver());
            combinedTypeSolver.add(new JavaParserTypeSolver(projectRoot));

            ParserConfiguration config = new ParserConfiguration();
            config.setSymbolResolver(new JavaSymbolSolver(combinedTypeSolver));
            JavaParser javaParser = new JavaParser(config);

            CompilationUnit cu = javaParser.parse(file).getResult().orElseThrow(() -> new NoSuchElementException("No CompilationUnit found"));
            Set<String> overriddenMethods = new HashSet<>();
            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
            for (ClassOrInterfaceDeclaration c : classes) {
                List<MethodDeclaration> methods = c.getMethods();
                for (MethodDeclaration m : methods) {
                    if (isOverridingMethod(m.resolve())) {
                        String methodInfo = m.getNameAsString() + "(" + getMethodParameterTypes(m) + ")";
                        overriddenMethods.add(methodInfo);
                        System.out.println("Overridden method: " + methodInfo + " | " + "Node type: " + m.getClass().getSimpleName() + " | " + "Location: " + m.getBegin().map(Object::toString).orElse("Unknown"));
                    }
                }
            }
            System.out.println("Number of overridden methods: " + overriddenMethods.size());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file);
        } catch (ParseProblemException e) {
            System.out.println("Error parsing the source file: " + e.getMessage());
        }
    }

    private static boolean isOverridingMethod(ResolvedMethodDeclaration method) {
        ResolvedReferenceTypeDeclaration declaringType = method.declaringType();
        for (ResolvedReferenceType ancestor : declaringType.getAncestors()) {
            if (ancestor.getTypeDeclaration().isPresent()) {
                ResolvedReferenceTypeDeclaration ancestorDecl = ancestor.getTypeDeclaration().get();
                for (ResolvedMethodDeclaration ancestorMethod : ancestorDecl.getDeclaredMethods()) {
                    if (haveEqualSignatures(method, ancestorMethod)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean haveEqualSignatures(ResolvedMethodDeclaration method1, ResolvedMethodDeclaration method2) {
        if (!method1.getName().equals(method2.getName())) {
            return false;
        }

        int method1ParamsCount = method1.getNumberOfParams();
        int method2ParamsCount = method2.getNumberOfParams();

        if (method1ParamsCount != method2ParamsCount) {
            return false;
        }

        for (int i = 0; i < method1ParamsCount; i++) {
            ResolvedType method1ParamType = method1.getParam(i).getType();
            ResolvedType method2ParamType = method2.getParam(i).getType();
            if (!method1ParamType.isAssignableBy(method2ParamType)) {
                return false;
            }
        }

        return true;
    }


    private static String getMethodParameterTypes(MethodDeclaration method) {
        List<Parameter> parameters = method.getParameters();
        StringBuilder sb = new StringBuilder();
        for (Parameter p : parameters) {
            sb.append(p.getTypeAsString()).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
