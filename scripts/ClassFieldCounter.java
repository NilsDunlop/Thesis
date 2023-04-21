import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassFieldCounter {

    public static int countClassesWithOwnTypeField(File file) throws IOException {
        String fileContent = new String(Files.readAllBytes(file.toPath()));
        int count = 0;
        Pattern classPattern = Pattern.compile("class\\s+(\\w+)\\s*\\{([^{}]*\\{[^{}]*\\}[^{}]*)*[^{}]*?(private|protected|public)\\s+\\1\\s+");
        Matcher classMatcher = classPattern.matcher(fileContent);
        while (classMatcher.find()) {
            count++;
        }
        return count;
    }
}