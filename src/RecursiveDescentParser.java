public class RecursiveDescentParser {
    /*
     * The given grammar is:
     * Non-terminal: {S, A, B}
     * Start symbol: S
     * Terminal symbols: {n, +, x}
     * Production Rule:
     *      S -> n B
     *      B -> n B A B | ε
     *      A -> + | x
     */

    private String input;
    private int currentIndex;
    private String sententialForm;

    public RecursiveDescentParser(String input) {
        this.input = input;
        this.currentIndex = 0;
    }

    public boolean applyParsing() {
        this.sententialForm = "S";
        boolean isAccepted = parseS();
        System.out.println("Final sentential form: " + this.sententialForm);

        if (isAccepted  &&  (currentIndex == input.length())  &&  checkFinalSententialFormDoesNotContainNonTerminals()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkFinalSententialFormDoesNotContainNonTerminals() {
        return (!this.sententialForm.contains("A"))  &&  (!this.sententialForm.contains("B"))  &&  (!this.sententialForm.contains("S"));
    }

    private void printStep(String rule) {
        System.out.println("Current sentential form: " + this.sententialForm);
        String remainingInput = this.input.substring(this.currentIndex);
        System.out.println("Remaining input: " + remainingInput);


        System.out.println("Rule applying: " + rule);
        System.out.println();
    }

    private boolean parseS() {
        printStep("S -> n B");
        this.sententialForm = this.sententialForm.replaceFirst("S", "nB");

        if (this.currentIndex < this.input.length()) {
            if (parseTerminal('n')  &&  parseB()) {
                return true;
            }
        }

        //backtracking
        this.sententialForm = this.sententialForm.replaceFirst("nB", "S");
        return false;
    }

    private boolean parseB() {
        printStep("B -> n B A B");
        this.sententialForm = this.sententialForm.replaceFirst("B", "nBAB");

        if (this.currentIndex < this.input.length()) {
            if (parseTerminal('n')  &&  parseB()  &&  parseA()  &&  parseB()) {
                return true;
            }
        }

        printStep("B -> ε");
        //accepting with epsilon
        this.sententialForm = this.sententialForm.replaceFirst("nBAB", "ε");
//        System.out.println("B with ε");
        return true; // epsilon
    }

    private boolean parseA() {
        printStep("A -> +");
        this.sententialForm = this.sententialForm.replaceFirst("A", "+");

        if (this.currentIndex < this.input.length()) {
            if (parseTerminal('+')) {
                return true;
            }
        }

        this.sententialForm = this.sententialForm.replaceFirst("\\+", "A");

        printStep("A -> x");
        this.sententialForm = this.sententialForm.replaceFirst("A","x");
        if (this.currentIndex < this.input.length()) {
            if (parseTerminal('x')) {
                return true;
            }
        }

        this.sententialForm = this.sententialForm.replaceFirst("x", "A");

        return false;
    }

    private boolean parseTerminal(char terminal) {
        printStep("Parsing Terminal: " + terminal);

        if (this.currentIndex < this.input.length()) {
            if (this.input.charAt(this.currentIndex) == terminal) {
                this.currentIndex++;
                return true;
            }
        }
        System.out.println("Could not parse terminal: " + terminal +"\n");
        return false;
    }

    //for testing
    public static void main(String[] args) {
//        RecursiveDescentParser recursiveDescentParser = new RecursiveDescentParser("nn+n");
//        RecursiveDescentParser recursiveDescentParser = new RecursiveDescentParser("nnn");
//        RecursiveDescentParser recursiveDescentParser = new RecursiveDescentParser("nn+nx");
//        RecursiveDescentParser recursiveDescentParser = new RecursiveDescentParser("nn");
//        RecursiveDescentParser recursiveDescentParser = new RecursiveDescentParser("nnx");
        RecursiveDescentParser recursiveDescentParser = new RecursiveDescentParser("nn+n+nn+n+x");

        boolean check = recursiveDescentParser.applyParsing();
        if (check) {
            System.out.println("Accepted");
        } else {
            System.out.println("Not Accepted");
        }
    }
}
