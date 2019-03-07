/**
 * Expr Class
 * Contains sub-nodes and methods associated with <expr> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Expr{
    private int alt;
    private Term term;
    private Expr expr;
    public Expr() {
        this.term = new Term();
    }
    public void parseExpr() {
        this.term.parseTerm();
        String tokenSymbol = Parser.currentToken().symbol;
        int tokenLine = Parser.currentToken().line;
        switch (tokenSymbol) {
            case(";"):
                this.alt = 1;
                break;
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
            case(")"):
                this.alt = 4;
                break;
            default:
                System.out.println("Error! Expression (Line " + tokenLine + ") Expected \";\", \"-\", or \"+\", but found \"" + tokenSymbol + "\"");
                System.exit(0);
        }     
    }
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
    public void execExpr() {
        // Left blank for Project 2
    }
    
}
