import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticMethodCounter {
    public static int countStaticMethods(File sourceFile) throws IOException {
        String sourceCode = new String(Files.readAllBytes(sourceFile.toPath()));
        Matcher matcher = Pattern.compile("(public|private|protected)?\\s*(static)\\s*(final)?\\s*([\\w\\[\\]]+\\s+)*([\\w$]+)\\s*\\(")
                .matcher(sourceCode);
        int staticMethodCount = 0;
        while (matcher.find()) {
            staticMethodCount++;
        }
        return staticMethodCount;
    }
}
