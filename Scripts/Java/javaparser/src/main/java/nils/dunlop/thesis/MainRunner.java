package nils.dunlop.thesis;

import java.util.Arrays;
import java.util.List;

public class MainRunner {

    public static void main(String[] args) throws Exception {
        List<String> filePaths = Arrays.asList(
                "C:\\Users\\nilsd\\Desktop\\Thesis\\PMART\\3 - JRefactory v2.6.24\\src\\org\\acm\\seguin\\tools\\build\\CreateVersion.java",
                "C:\\Users\\nilsd\\Desktop\\Thesis\\PMART\\3 - JRefactory v2.6.24\\src\\org\\acm\\seguin\\tools\\build\\CodeStoreBuilder.java"
        );
        for (String filePath : filePaths) {
            System.out.println("Analysing: " + filePath);
            System.out.println("\nRunning StaticFieldCounter:");
            StaticFieldCounter.main(new String[]{filePath});
            System.out.println("\nRunning PrivateConstructorCounter:");
            PrivateConstructorCounter.main(new String[]{filePath});
            System.out.println("\nRunning AbstractMethodCounter:");
            AbstractMethodCounter.main(new String[]{filePath});
            System.out.println("\nRunning FieldCounter:");
            FieldCounter.main(new String[]{filePath});
            System.out.println("\nRunning ObjectFieldCounter:");
            ObjectFieldCounter.main(new String[]{filePath});
            System.out.println("\nRunning ClassFieldCounter");
            ClassFieldCounter.main(new String[]{filePath});
            System.out.println("\nAll counters have been executed for " + filePath + ".\n");
        }
    }
}
