import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstanceMethodCounter {

    public static int countInstanceMethods(File sourceFile) {
        int count = 0;

        try {
            String source = Files.readString(sourceFile.toPath(), StandardCharsets.UTF_8);
            Pattern pattern = Pattern.compile("\\bnew\\s+[A-Z][a-zA-Z0-9_]*\\(");

            Matcher matcher = pattern.matcher(source);
            while (matcher.find()) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error reading source file: " + e.getMessage());
        }

        return count;
    }
}
