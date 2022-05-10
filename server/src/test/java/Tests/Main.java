package Tests;

public class Main {

    public static void main(String args[]) {

        System.out.println("Car dealership tests\n\n");

        System.out.println("Repository tests\n");
        RepositoryTests repositoryTests = new RepositoryTests();
        System.out.println("In memory tests");
        repositoryTests.runMemoryTests();
        System.out.println("Txt files tests");
        repositoryTests.runTxtTests();
        System.out.println("Xml files tests");


    }
}
