package nils.dunlop.thesis;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class MainRunner {

    public static void main(String[] args) throws FileNotFoundException {
        // List of file paths to analyze
        List<String> filePaths = Arrays.asList(
                "Example.java"
        );
        String rootPath = "C:\\Users\\nilsd\\Desktop\\Thesis\\PMART\\3 - JRefactory v2.6.24\\src";

        for (String filePath : filePaths) {
            System.out.println("Analysing: " + filePath);
            System.out.println("\nRunning StaticMethodCounter:");
            StaticMethodCounter.main(new String[]{filePath});
            System.out.println("\nRunning InterfaceCounter:");
            InterfaceCounter.main(new String[]{filePath});
            System.out.println("\nRunning MethodCounter:");
            MethodCounter.main(new String[]{filePath});
            System.out.println("\nRunning OverrideCounter:");
            OverrideCounter.main(new String[]{filePath}, rootPath);
            System.out.println("\nRunning InstanceCounter:");
            InstanceCounter.main(new String[]{filePath});
            System.out.println("\nAll counters have been executed for " + filePath + ".\n");
        }
    }
}