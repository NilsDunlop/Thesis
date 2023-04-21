import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodOverrideCounter {

    public static int countOverriddenMethods(File sourceFile) throws IOException {
        String sourceCode = readSourceCodeFromFile(sourceFile.toPath());
        // Extract all class definitions and their methods
        Pattern classPattern = Pattern.compile("class\\s+(\\w+)\\s*(extends\\s+(\\w+)\\s*)?\\{([^}]+)}");
        Matcher classMatcher = classPattern.matcher(sourceCode);
        int numOverriddenMethods = 0;
        Set<String> overriddenMethods = new HashSet<>();

        while (classMatcher.find()) {
            String className = classMatcher.group(1);
            String parentClassName = classMatcher.group(3);
            String methodBody = classMatcher.group(4);

            // If the class extends another class, extract the parent class's methods
            if (parentClassName != null) {
                Pattern parentClassPattern = Pattern.compile("class\\s+" + parentClassName + "\\s*\\{([^}]+)}");
                Matcher parentClassMatcher = parentClassPattern.matcher(sourceCode);
                if (parentClassMatcher.find()) {
                    String parentMethodBody = parentClassMatcher.group(1);
                    Pattern parentMethodPattern = Pattern.compile("(public|private|protected)\\s+\\w+\\s+(\\w+)\\((.*?)\\)");
                    Matcher parentMethodMatcher = parentMethodPattern.matcher(parentMethodBody);
                    while (parentMethodMatcher.find()) {
                        String methodName = parentMethodMatcher.group(2);
                        overriddenMethods.add(methodName);
                    }
                }
            }

            // Extract the current class's methods and check if they override a method in the parent class
            Pattern methodPattern = Pattern.compile("(public|private|protected)\\s+\\w+\\s+(\\w+)\\((.*?)\\)");
            Matcher methodMatcher = methodPattern.matcher(methodBody);
            while (methodMatcher.find()) {
                String methodName = methodMatcher.group(2);
                if (overriddenMethods.contains(methodName)) {
                    numOverriddenMethods++;
                }
            }
        }

        return numOverriddenMethods;
    }

    private static String readSourceCodeFromFile(Path path) throws IOException {
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }

}
