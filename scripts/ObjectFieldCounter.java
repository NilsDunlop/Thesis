import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectFieldCounter {
    public static int countObjectFields(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int count = 0;
        Pattern pattern = Pattern.compile(".*\\b(private|public|protected)?\\s*(static)?\\s*([a-zA-Z_$][a-zA-Z0-9_$]*)\\s+([a-zA-Z_$][a-zA-Z0-9_$]*)\\s*(=.*)?;");
        // Matches any line that defines an object field

        while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches() && !matcher.group(3).equals("void") && !matcher.group(3).equals("boolean") && !matcher.group(3).equals("int")
                    && !matcher.group(3).equals("long") && !matcher.group(3).equals("float") && !matcher.group(3).equals("double")
                    && !matcher.group(3).equals("char") && !matcher.group(3).equals("byte") && !matcher.group(3).equals("short")) {
                count++;
            }
        }
        reader.close();
        return count;
    }
}
