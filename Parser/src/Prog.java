/**
 * Prog Class
 * Contains sub-nodes and methods associated with <program> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Prog{
    
    /**
     * Private members for sub-nodes.
     */
    private DeclSeq ds;
    private StmtSeq ss;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public Prog() {      
        this.ds = new DeclSeq();
        this.ss = new StmtSeq();
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseProg() {
        Interpreter.matchConsume("program", "Begin");
        this.ds.parseDeclSeq();
        Interpreter.matchConsume("begin", "Begin");
        this.ss.parseStmtSeq();
        Interpreter.matchConsume("end", "Begin");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printProg() {
        System.out.print("program\n");
        Interpreter.increaseIndent();
        this.ds.printDeclSeq();
        System.out.print(Interpreter.indent() + "begin\n");
        Interpreter.increaseIndent();
        this.ss.printStmtSeq();
        Interpreter.decreaseIndent();
        System.out.print(Interpreter.indent() + "end\n");
        Interpreter.decreaseIndent(); 
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execProg() {
        // This implementation does not execute Declaration Sequences. This is done during parsing. 
        this.ss.execStmtSeq();
    }
    
}