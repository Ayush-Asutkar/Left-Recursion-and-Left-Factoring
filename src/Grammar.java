import model.ProductionRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grammar {
    private List<ProductionRule> productionRules;

    public Grammar() {
        this.productionRules = new ArrayList<>();
    }

    public void addRule (String leftHandSide, Set<String> rightHandSide) {
        ProductionRule newProductionRule;
        if (this.productionRules.contains(new ProductionRule(leftHandSide))) {
            int index = this.productionRules.indexOf(new ProductionRule(leftHandSide));
            newProductionRule = this.productionRules.get(index);
            newProductionRule.addAllRightHandSide(rightHandSide);
        } else {
            newProductionRule = new ProductionRule(leftHandSide);
            newProductionRule.addAllRightHandSide(rightHandSide);
            this.productionRules.add(newProductionRule);
        }
    }

    /**
     * To add the production rule to the grammar
     * @param rule is of format: A -> B | C | D
     */
    public void addRule (String rule) {
        rule = rule.replaceAll(" ","");
//        System.out.println(rule);

//        System.out.println(Arrays.toString(rule.split("->")));

        String[] ruleSplit = rule.split("->");
        String leftSide = ruleSplit[0];
//        System.out.println(leftSide);


        String[] rightSide = ruleSplit[1].split("\\|");

        Set<String> rightSideSet = new HashSet<>(List.of(rightSide));
        this.addRule(leftSide, rightSideSet);
    }

    /**
     * Prints the production rules of the grammar
     */
    public void printRules() {
        System.out.println("Following are the rules in the given grammar:");
        for (ProductionRule productionRule: this.productionRules) {
            System.out.println(productionRule);
        }
        System.out.println();
    }

    private void solveNonImmediateLR(ProductionRule first, ProductionRule second) {

        String secondLeftHandSide = second.getLeftHandSide();

        Set<String> newRulesOfFirst = new HashSet<>();

        for (String rule: first.getRightHandSide()) {
            if (rule.startsWith(secondLeftHandSide)) {
                for (String innerRule: second.getRightHandSide()) {
                    newRulesOfFirst.add(innerRule + rule.substring(secondLeftHandSide.length()));
                }
            } else {
                newRulesOfFirst.add(rule);
            }
        }
        first.setNewRightHandSide(newRulesOfFirst);
    }

    private void solveImmediateLR(ProductionRule first) {
        String leftHandSide = first.getLeftHandSide();
        String newName = leftHandSide + "'";

        Set<String> leftRecursiveOne = new HashSet<>();
        Set<String> nonLeftRecursiveOne = new HashSet<>();

        // Check if there is left recursion
        for (String rule: first.getRightHandSide()) {
            if (rule.startsWith(leftHandSide)) {
                leftRecursiveOne.add(rule.substring(leftHandSide.length()));
            } else {
                nonLeftRecursiveOne.add(rule);
            }
        }

        // if no left recursion exists
        if (leftRecursiveOne.size() == 0) {
            return;
        }

        Set<String> changeRuleForFirst = new HashSet<>();
        Set<String> newRuleForNewName = new HashSet<>();

        if (nonLeftRecursiveOne.size() == 0) {
            changeRuleForFirst.add(newName);
        }

        for (String beta: nonLeftRecursiveOne) {
            changeRuleForFirst.add(beta + newName);
        }

        for (String alpha: leftRecursiveOne) {
            newRuleForNewName.add(alpha + newName);
        }

        //Amend the original rule
        first.setNewRightHandSide(changeRuleForFirst);

        //add new production rule
        newRuleForNewName.add("\u03B5");

        ProductionRule newProductionRule = new ProductionRule(newName);
        newProductionRule.setNewRightHandSide(newRuleForNewName);
        this.productionRules.add(newProductionRule);
    }

    public void applyAlgorithmForRemovalOfLeftRecursion() {
        int size = productionRules.size();

        for (int i=0; i<size; i++) {
            for (int j=0; j<i; j++) {
                this.solveNonImmediateLR(productionRules.get(i), productionRules.get(j));
            }
            solveImmediateLR(productionRules.get(i));
        }
    }

    // for testing
    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.addRule("S -> Sa | c | d");
        grammar.addRule("S -> Sb");
        grammar.printRules();

        grammar.applyAlgorithmForRemovalOfLeftRecursion();
        grammar.printRules();
    }
}
