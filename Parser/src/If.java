/**
 * If Class
 * Contains sub-nodes and methods associated with <if> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class If{
    private int alt;
    private Cond cond;
    private StmtSeq ss1;
    private StmtSeq ss2;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public If() {
        this.alt = 1;
        this.cond = new Cond();
        this.ss1 = new StmtSeq();
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseIf() {
        Parser.matchConsume("if", "If");
        this.cond.parseCond();
        Parser.matchConsume("then", "If");
        this.ss1.parseStmtSeq();
        if(Parser.currentToken().symbol.equals("else")) {
            Parser.nextToken();
            this.alt = 2;
            this.ss2 = new StmtSeq();
            this.ss2.parseStmtSeq();
        }
        Parser.matchConsume("end", "If");
        Parser.matchConsume(";", "If");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printIf() {
        System.out.print(Parser.indent() + "if ");
        this.cond.printCond();
        System.out.print(" then\n");
        Parser.increaseIndent();
        this.ss1.printStmtSeq();
        Parser.decreaseIndent();
        if(this.alt == 2) {
            System.out.print(Parser.indent() + "else\n");
            Parser.increaseIndent();
            this.ss2.printStmtSeq();
            Parser.decreaseIndent();
        }
        System.out.print(Parser.indent() + "end;\n"); 
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execIf() {
        // Left blank for Project 2
    }
    
}
