/**
 * Expr Class
 * Contains sub-nodes and methods associated with <expr> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Expr{
    
    /**
     * Private members for sub-nodes and alt.
     */
    private int alt;
    private Term term;
    private Expr expr;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public Expr() {
        this.term = new Term();
    }
    
//    /**
//     * Method to parse relevant tokens and symbols 
//     */
//    public void parseExpr() {
//        this.term.parseTerm();
//        String tokenSymbol = Parser.currentToken().symbol;
//        int tokenLine = Parser.currentToken().line;
//        switch (tokenSymbol) {
//            case(";"):
//                this.alt = 1;
//                break;
//            case("+"):
//                this.alt = 2;
//                Parser.nextToken();
//                this.expr = new Expr();
//                this.expr.parseExpr();
//                break;
//            case("-"):
//                this.alt = 3;
//                Parser.nextToken();
//                this.expr = new Expr();
//                this.expr.parseExpr();
//                break;
//            case(")"):
//                this.alt = 4;
//                break;
//            default:
//                System.out.println("Error! Expression (Line " + tokenLine 
//                        + ") Expected \";\", \"-\", or \"+\", but found \"" 
//                        + tokenSymbol + "\"");
//                System.exit(0);
//        }     
//    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseExpr() {
        this.alt = 1;
        this.term.parseTerm();
        String tokenSymbol = Parser.currentToken().symbol;
        int tokenLine = Parser.currentToken().line;
        switch (tokenSymbol) {
            case("+"):
                this.alt = 2;
                Parser.nextToken();
                this.expr = new Expr();
                this.expr.parseExpr();
                break;
            case("-"):
                this.alt = 3;
                Parser.nextToken();
                this.expr = new Expr();
                this.expr.parseExpr();
                break;
        }     
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printExpr() {
        this.term.printTerm();
        switch (this.alt) {
            case(2):
                System.out.print(" + ");
                this.expr.printExpr();
                break;
            case(3):
                System.out.print(" - ");
                this.expr.printExpr();
                break;
        }
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public int execExpr() {
        int value1 = 0, value2 = 0, finalValue = 0;
        switch (this.alt) {
            case(1): // Term Only
                finalValue = this.term.execTerm();
                break;
            case(2): // Term + Expr
                value1 = this.term.execTerm();
                value2 = this.expr.execExpr();
                finalValue = value1 + value2;
                //CHECK FOR OVERFLOW
                break;
            case(3): // Term - Expr
                value1 = this.term.execTerm();
                value2 = this.expr.execExpr();
                finalValue = value1 - value2;
                //CHECK FOR UNDERFLOW
                break;
        }
        return finalValue;
    }
    
}
