import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodCounter {

    public static int countMethods(File sourceFile) throws IOException {
        String sourceCode = new String(Files.readAllBytes(sourceFile.toPath()));
        Matcher matcher = Pattern.compile("(public|protected|private|static|abstract|final|synchronized) +[\\w\\<\\>\\[\\]]+ +(\\w+) *\\((.*?)\\) *\\{").matcher(sourceCode);
        int methodCount = 0;
        while (matcher.find()) {
            methodCount++;
        }
        return methodCount;
    }
}