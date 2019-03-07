/**
 * Assign Class
 * Contains sub-nodes and methods associated with <assign> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Assign{
    
    /**
     * ID sub-node
     */
    private ID id;
    
    /**
     * Expr sub-node
     */
    private Expr expr;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public Assign() {
        this.id = new ID(false);
        this.expr = new Expr();
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseAssign() {
        this.id.parseID();
        Parser.matchConsume("=", "Assign");
        this.expr.parseExpr();
        Parser.matchConsume(";", "Assign");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printAssign() {
        System.out.print(Parser.indent());
        this.id.printID();
        System.out.print(" = ");
        this.expr.printExpr();
        System.out.print(";\n");  
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execAssign() {
        // Left blank for Project 2
    }
    
}
