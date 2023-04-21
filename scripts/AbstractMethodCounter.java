import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractMethodCounter {
    public static int countAbstractMethods(File sourceFile) throws IOException {
        String sourceCode = new String(Files.readAllBytes(sourceFile.toPath()));
        Matcher matcher = Pattern.compile("(public|private|protected)?\\s*(abstract)\\s*(final)?\\s*([\\w\\[\\]]+\\s+)*([\\w$]+)\\s*\\(")
                .matcher(sourceCode);
        int abstractMethodCount = 0;
        while (matcher.find()) {
            abstractMethodCount++;
        }
        return abstractMethodCount;
    }
}