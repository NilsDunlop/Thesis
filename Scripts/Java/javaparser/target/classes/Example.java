public class Example {

    public static void main(String[] args) {
        Animal myAnimal = new Animal();
        Dog myDog = new Dog();

        myAnimal.makeSound();
        myDog.makeSound();
    }
}

class Animal {
    public void makeSound() {
        System.out.println("The animal makes a sound");
    }

    public void sleep() {
        System.out.println("The animal is sleeping");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("The dog barks");
    }

    public void sleep() {
        System.out.println("The dog is sleeping");
    }
}