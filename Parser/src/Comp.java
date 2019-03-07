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
    private String compOp;
    private Fac fac1;
    private Fac fac2;
    public Comp() {
        this.fac1 = new Fac();
        this.fac2 = new Fac();
    }
    public void parseComp() {
        Parser.matchConsume("(", "Comp");
        this.fac1.parseFac();
        if(!isComp(Parser.currentToken().symbol)) {
            System.out.println("ERROR expected comp");
            System.exit(0);
        }
        this.compOp = Parser.currentToken().symbol;
        Parser.nextToken();
        this.fac2.parseFac();
        Parser.matchConsume(")", "Comp");
    }
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
    public void execComp() {
        // Left blank for Project 2
    }
    
    private boolean isComp(String inString) {
        String[] compOpsArray = new String[]{"!=","==",">=","<=","<",">"};
        Set<String> compOpsSet = new HashSet<>(Arrays.asList(compOpsArray));
        return compOpsSet.contains(inString);
    }
    
}

