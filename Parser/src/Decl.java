/**
 * Decl Class
 * Contains sub-nodes and methods associated with <decl> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Decl{
    
    /**
     * Private member for sub-node.
     */
    private IDList idl;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public Decl() {
        this.idl = new IDList(true);
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseDecl() {
        Interpreter.matchConsume("int", "Decl");
        this.idl.parseIDList();
        Interpreter.matchConsume(";", "Decl");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printDecl() {
        System.out.print(Interpreter.indent() + "int ");
        this.idl.printIDList();
        System.out.print(";\n");
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execDecl() {
        // This implementation does not execute Declarations. This is done during parsing. 
    }
    
}
