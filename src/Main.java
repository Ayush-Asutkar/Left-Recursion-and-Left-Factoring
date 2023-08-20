import java.util.Scanner;

public class Main {
    /* Example Input:
    4
    Goal -> Expr
    Expr -> Expr + Term | Expr - Term | Term
    Term -> Term * Factor | Term / Factor | Factor
    Factor -> number | id
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
    public static void main(String[] args) {
        Grammar grammar = takeInputAndCreateGrammar();
        grammar.printRules();

        grammar.applyAlgorithmForRemovalOfLeftRecursion();

        grammar.printRules();
    }
}