import java.util.Scanner;

public class Main {
    /* Example Input:
    4
    Goal -> Expr
    Expr -> Expr + Term | Expr - Term | Term
    Term -> Term * Factor | Term / Factor | Factor
    Factor -> number | id

    3
    S -> aAd | aB
    A -> a | ab
    B -> ccd|ddc
    */
    private static Grammar takeInputAndCreateGrammar() {
        Grammar grammar = new Grammar();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of lines for input: ");
        int lines = sc.nextInt();

        System.out.println("Enter the production rule in the form: A -> B | C");
        String backSlash = sc.nextLine();

        for (int i=1; i<=lines; i++) {
            String input = sc.nextLine();
            grammar.addRule(input);
        }

        return grammar;
    }

    private static void handleCode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Following are the two functionality this code provides:");
        System.out.println("\t1.To remove left recursion\n" +
                "\t2.To do left factoring\n" +
                "\t3.To check whether input string is accepted by a grammar");

        System.out.print("Please enter you choice (either 1 or 2 or 3): ");
        int choice = scanner.nextInt();

        System.out.println();
        switch (choice) {
            case 1: {
                Grammar grammar = takeInputAndCreateGrammar();
                System.out.println("Input rules:");
                grammar.printRules();

                grammar.applyAlgorithmForRemovalOfLeftRecursion();
                System.out.println("After removal of left recursion:");
                grammar.printRules();
                break;
            }

            case 2: {
                Grammar grammar = takeInputAndCreateGrammar();
                System.out.println("Input rules:");
                grammar.printRules();

                grammar.applyAlgorithmForProducingAnEquivalentLeftFactored();
                System.out.println("After doing left factoring:");
                grammar.printRules();
                break;
            }

            case 3: {
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter the string: ");
                String input = sc.nextLine();
                RecursiveDescentParser recursiveDescentParser = new RecursiveDescentParser(input);
                boolean check = recursiveDescentParser.applyParsing();
                if (check) {
                    System.out.println("The input string " + input + " is accepted");
                } else {
                    System.out.println("The input string " + input + " is not accepted");
                }
                break;
            }

            default: {
                System.out.println("ERROR!!! Please enter a valid choice!");
                System.out.println("Exiting...");
                break;
            }
        }
    }

    public static void main(String[] args) {
        handleCode();
    }
}