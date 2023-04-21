import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrivateConstructorCounter {

    public static int countPrivateConstructors(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String content = scanner.useDelimiter("\\Z").next();
        scanner.close();

        int count = 0;
        Pattern pattern = Pattern.compile("private\\s+[a-zA-Z0-9_]+\\s*\\(");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            count++;
        }

        return count;
    }
}