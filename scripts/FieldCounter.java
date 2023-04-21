import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldCounter {
    public static int countFields(File sourceFile) throws IOException {
        String sourceCode = new String(Files.readAllBytes(sourceFile.toPath()));
        Matcher matcher = Pattern.compile("(public|private|protected|static)?\\s*(final)?\\s*([\\w\\[\\]]+\\s+)*([\\w$]+)\\s*(\\[\\])?\\s*(=\\s*([^;\\n]+)|;)")
                .matcher(sourceCode);
        int fieldCount = 0;
        while (matcher.find()) {
            fieldCount++;
        }
        return fieldCount;
    }
}
