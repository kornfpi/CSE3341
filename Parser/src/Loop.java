/**
 * Loop Class
 * Contains sub-nodes and methods associated with <loop> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Loop{
    
    /**
     * Private members for sub-nodes.
     */
    private Cond cond;
    private StmtSeq ss;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public Loop() {
        this.cond = new Cond();
        this.ss = new StmtSeq();
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseLoop() {
        Parser.matchConsume("while", "Loop");
        this.cond.parseCond();
        Parser.matchConsume("loop", "Loop");
        this.ss.parseStmtSeq();
        Parser.matchConsume("end", "Loop");
        Parser.matchConsume(";", "Loop");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printLoop() {
        System.out.print(Parser.indent() + "while ");
        this.cond.printCond();
        System.out.print(" loop\n");
        Parser.increaseIndent();
        this.ss.printStmtSeq();
        Parser.decreaseIndent();
        System.out.print(Parser.indent() + "end;\n"); 
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execLoop() {
        while(this.cond.execCond()) {
            this.ss.execStmtSeq();
        }
    }
    
}