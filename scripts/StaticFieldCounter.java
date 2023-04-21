import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticFieldCounter {
    public static int countStaticFields(File sourceFile) throws IOException {
        String sourceCode = new String(Files.readAllBytes(sourceFile.toPath()));
        Matcher matcher = Pattern.compile("(public|private|protected)?\\s*(static)\\s*(final)?\\s*([\\w\\[\\]]+\\s+)*([\\w$]+)\\s*(\\[\\])?\\s*(=\\s*([^;\\n]+)|;)")
                .matcher(sourceCode);
        int staticFieldCount = 0;
        while (matcher.find()) {
            staticFieldCount++;
        }
        return staticFieldCount;
    }
}