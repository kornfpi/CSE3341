/**
 * If Class
 * Contains sub-nodes and methods associated with <if> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class If{
    
    /**
     * Private members for sub-nodes and alt.
     */
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
        Interpreter.matchConsume("if", "If");
        this.cond.parseCond();
        Interpreter.matchConsume("then", "If");
        this.ss1.parseStmtSeq();
        if(Interpreter.currentToken().symbol.equals("else")) {
            Interpreter.nextToken();
            this.alt = 2;
            this.ss2 = new StmtSeq();
            this.ss2.parseStmtSeq();
        }
        Interpreter.matchConsume("end", "If");
        Interpreter.matchConsume(";", "If");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printIf() {
        System.out.print(Interpreter.indent() + "if ");
        this.cond.printCond();
        System.out.print(" then\n");
        Interpreter.increaseIndent();
        this.ss1.printStmtSeq();
        Interpreter.decreaseIndent();
        if(this.alt == 2) {
            System.out.print(Interpreter.indent() + "else\n");
            Interpreter.increaseIndent();
            this.ss2.printStmtSeq();
            Interpreter.decreaseIndent();
        }
        System.out.print(Interpreter.indent() + "end;\n"); 
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execIf() {
        if(this.alt == 1) {
            if(this.cond.execCond()) {
                this.ss1.execStmtSeq();
            }
        }else {
            if(this.cond.execCond()) {
                this.ss1.execStmtSeq();
            }else {
                this.ss2.execStmtSeq();
            }
        }
    }
    
}
