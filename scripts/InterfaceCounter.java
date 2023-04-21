import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InterfaceCounter {
    private static final String INTERFACE_REGEX = ".*\\bimplements\\b(.*)\\{.*";
    private static final Pattern INTERFACE_PATTERN = Pattern.compile(INTERFACE_REGEX);

    public static int countInterfaces(File file) {
        int interfaceCount = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                Matcher matcher = INTERFACE_PATTERN.matcher(line);
                if (matcher.matches()) {
                    String interfaces = matcher.group(1);
                    interfaceCount += interfaces.split(",").length;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return interfaceCount;
    }
}