/**
 * Fac Class
 * Contains sub-nodes and methods associated with <fac> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Fac{
    
    /**
     * Private members for sub-nodes and alt, and integer value.
     */
    private int alt;
    private int intValue;
    private ID id;
    private Expr expr;
    
    /**
     * No argument constructor. 
     */
    public Fac() {
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseFac() {
        String type = Parser.currentToken().type;
        String tokenSymbol = Parser.currentToken().symbol;
        int tokenLine = Parser.currentToken().line;
        switch (type) {
            case("IDENT"):
                if(Parser.isInt(tokenSymbol)) {
                    this.alt = 1;
                    this.intValue = Integer.parseInt(tokenSymbol);
                    Parser.nextToken();
                }else {
                    this.alt = 2;
                    this.id = new ID(false);
                    this.id.parseID();
                }
                break;
            case("SPEC"):
                this.alt = 3;
                Parser.matchConsume("(", "Fac");
                this.expr = new Expr();
                this.expr.parseExpr();
                Parser.matchConsume(")", "Fac");
                break;
            default:
                System.out.println("Error! Factor (Line " + tokenLine 
                        + ") Expected identifier, integer, or \"(\", but found \"" 
                        + tokenSymbol + "\"");
                System.exit(0);
        }   
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printFac() {
        switch (this.alt) {
            case(1):
                System.out.print(this.intValue);
                break;
            case(2):
                this.id.printID();
                break;
            case(3):
                System.out.print("( ");
                this.expr.printExpr();
                System.out.print(" )");
                break;
        }
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public int execFac() {
        int finalValue = 0;
        switch (this.alt) {
            case(1):
                finalValue = this.intValue;
                break;
            case(2):
                String identifier = this.id.execID();
                finalValue = Parser.getSymbolValue(identifier);
                break;
            case(3):
                finalValue = this.expr.execExpr();
                break;
        }
        return finalValue;
    }
    
}
