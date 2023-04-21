import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File sourceFile = new File("C:\\Users\\nilsd\\Desktop\\Thesis\\PMART\\Adapter\\Netbeans\\25 - XMLDataObject$InfoParser (adapter).java");
        try {
            int staticFieldCount = StaticFieldCounter.countStaticFields(sourceFile);
            System.out.println("Number of static fields in " + sourceFile.getName() + ": " + staticFieldCount);

            int privateConstructorCount = PrivateConstructorCounter.countPrivateConstructors(sourceFile);
            System.out.println("Number of private constructors: " + privateConstructorCount);

            int staticMethodCount = StaticMethodCounter.countStaticMethods(sourceFile);
            System.out.println("Number of static methods in " + sourceFile.getName() + ": " + staticMethodCount);

            int abstractMethodCount = AbstractMethodCounter.countAbstractMethods(sourceFile);
            System.out.println("Number of abstract methods in " + sourceFile.getName() + ": " + abstractMethodCount);

            int interfaceCount = InterfaceCounter.countInterfaces(sourceFile);
            System.out.println("Number of interfaces implemented: " + sourceFile.getName() + ": " + interfaceCount);

            int fieldCount = FieldCounter.countFields(sourceFile);
            System.out.println("Number of fields in " + sourceFile.getName() + ": " + fieldCount);

            int objectFieldCount = ObjectFieldCounter.countObjectFields(sourceFile);
            System.out.println("Number of object fields: " + objectFieldCount);

            int methodCount = MethodCounter.countMethods(sourceFile);
            System.out.println("Number of methods in " + sourceFile.getName() + ": " + methodCount);

            int classesWithOwnTypeCount = ClassFieldCounter.countClassesWithOwnTypeField(sourceFile);
            System.out.println("Number of classes with a field of their own type: " + classesWithOwnTypeCount);

            int overrideCount = MethodOverrideCounter.countOverriddenMethods(sourceFile);
            System.out.println("Number of overridden methods: " + sourceFile.getName() + ": " + overrideCount);

            int instanceMethodCount = InstanceMethodCounter.countInstanceMethods(sourceFile);
            System.out.println("Number of instance methods generating instances: " + instanceMethodCount);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}