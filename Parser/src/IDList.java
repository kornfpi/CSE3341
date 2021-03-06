/**
 * IDList Class
 * Contains sub-nodes and methods associated with <id-list> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

import java.util.*;

public class IDList{
    
    /**
     * Private members for sub-nodes, declaration type, and alt.
     */
    private boolean isDecl;
    private int alt;
    private ID id;
    private IDList idl;
    
    /**
     * Constructor. Creates private member objects.
     * Sets the variable isDecl if IDList is declarative.
     * @param isDecl determines if IDList is declarative.
     */
    public IDList(boolean isDecl) {
        this.isDecl = isDecl;
        this.alt = 1;
        this.id = new ID(isDecl);
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseIDList() {
        this.id.parseID();
        if(Interpreter.currentToken().symbol.equals(",")) {
            Interpreter.nextToken();
            this.alt = 2;
            this.idl = new IDList(isDecl);
            this.idl.parseIDList();
        }
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printIDList() {
        this.id.printID();
        if(this.alt == 2) {
            System.out.print(", ");
            this.idl.printIDList();
        }
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public ArrayList<String> execIDList() {
        ArrayList<String> variables = new ArrayList<String>();
        variables.add(this.id.execID());
        if(this.alt == 2) {
        	variables.addAll(this.idl.execIDList());
        }
        return variables;
    }
    
}