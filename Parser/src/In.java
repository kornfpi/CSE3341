/**
 * In Class
 * Contains sub-nodes and methods associated with <in> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class In{
    
    /**
     * Private member for sub-node.
     */
    private IDList idl;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public In() {
        this.idl = new IDList(false);
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseIn() {
        Parser.matchConsume("read", "IDList");
        this.idl.parseIDList();
        Parser.matchConsume(";", "IDList");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printIn() {
        System.out.print(Parser.indent() + "read ");
        this.idl.printIDList();
        System.out.print(";\n");
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execIn() {
        // Left blank for Project 2
    }
    
}
