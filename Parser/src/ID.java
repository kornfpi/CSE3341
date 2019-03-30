/**
 * ID Class
 * Contains sub-nodes and methods associated with <id> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class ID{
    
    /**
     * Private members for declaration type and ID string.
     */
    private String identifier;
    private boolean isDecl;
    
    /**
     * Constructor. Creates private member objects.
     * Sets the variable isDecl if ID is declarative.
     * @param isDecl determines if ID is declarative.
     */
    public ID(boolean isDecl) {
        this.isDecl = isDecl;
        this.identifier = null;
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseID() {
        if(isDecl) {
            parseDecl();
        }else {
            parseNonDecl(); 
        } 
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printID() {
        System.out.print(this.identifier);
    }
    
    /**
     * Method to 'execute' node. Returns key value for symbol table.
     */
    public String execID() {
        return this.identifier;
    }
    
    /**
     * Method to parse declarative ID. Adds symbol to symbol table,
     * or terminates program if symbol has already been declared.
     */
    public void parseDecl() {
        if(Parser.currentToken().type.equals("IDENT")) {
            this.identifier = Parser.currentToken().symbol;
            Parser.nextToken(); // Consume name
            if(Parser.hasSymbol(this.identifier)) {
                System.out.println("Error! Multiple declarations of identifier \"" 
            + this.identifier + "\"");
                System.exit(0);
            }else {
                Parser.addSymbol(this.identifier);
            }
        }else {
            String tokenSymbol = Parser.currentToken().symbol;
            int tokenLine = Parser.currentToken().line;
            System.out.println("Error! (Line " + tokenLine 
                    + ") Expected identifier but found \"" + tokenSymbol + "\"");
            System.exit(0);
        }  
    }
    
    /**
     * Method to parse non declarative id. Makes sure that identifier
     * has been previously declared. Terminates program if has not. 
     */
    public void parseNonDecl() {
        String tokenSymbol = Parser.currentToken().symbol;
        int tokenLine = Parser.currentToken().line;
        if(Parser.currentToken().type.equals("IDENT")) {
            this.identifier = Parser.currentToken().symbol;
            Parser.nextToken(); // Consume name
            if(!Parser.hasSymbol(this.identifier)) {
                System.out.println("Error! (Line " + tokenLine 
                        + ") Undeclared identifier \"" + tokenSymbol + "\"");
                System.exit(0);
            }
        }else {
            System.out.println("Error! (Line " + tokenLine 
                    + ") Expected identifier but found \"" + tokenSymbol + "\"");
            System.exit(0);
        }  
    }
    
}
