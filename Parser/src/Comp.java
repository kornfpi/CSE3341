/**
 * Comp Class
 * Contains sub-nodes and methods associated with <comp> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Comp{
    
    /**
     * Private members for sub-nodes and comparison operator.
     */
    private String compOp;
    private Fac fac1;
    private Fac fac2;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public Comp() {
        this.fac1 = new Fac();
        this.fac2 = new Fac();
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseComp() {
        Interpreter.matchConsume("(", "Comp");
        this.fac1.parseFac();
        if(!isComp(Interpreter.currentToken().symbol)) {
            System.out.println("[Parse Comp Error!] Expected comparison operator, but found " 
                    + Interpreter.currentToken().symbol + ", Line " + Interpreter.currentToken().line);
            System.exit(0);
        }
        this.compOp = Interpreter.currentToken().symbol;
        Interpreter.nextToken();
        this.fac2.parseFac();
        Interpreter.matchConsume(")", "Comp");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printComp() {
        System.out.print("( ");
        this.fac1.printFac();
        System.out.print(" " + this.compOp + " ");
        this.fac2.printFac();
        System.out.print(" )"); 
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public boolean execComp() {
        boolean takeBranch = false;
        int value1 = this.fac1.execFac();
        int value2 = this.fac2.execFac();
        switch(this.compOp) {
            case "!=":
                takeBranch = value1 != value2;
                break;
            case "==":
                takeBranch = value1 == value2;
                break;
            case ">=":
                takeBranch = value1 >= value2;
                break;
            case "<=":
                takeBranch = value1 <= value2;
                break;
            case "<":
                takeBranch = value1 < value2;
                break;
            case ">":
                takeBranch = value1 > value2;
                break;
        }
        return takeBranch;
    }
    
    /**
     * Method to check if string is a comparison operator
     * @param inString string to be checked
     * @return true if inString is comp-op, false otherwise
     */
    private boolean isComp(String inString) {
        String[] compOpsArray = new String[]{"!=","==",">=","<=","<",">"};
        Set<String> compOpsSet = new HashSet<>(Arrays.asList(compOpsArray));
        return compOpsSet.contains(inString);
    }
    
}

