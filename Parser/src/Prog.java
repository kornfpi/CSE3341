/**
 * Prog Class
 * Contains sub-nodes and methods associated with <program> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Prog{
    private DeclSeq ds;
    private StmtSeq ss;
    public Prog() {      
        this.ds = new DeclSeq();
        this.ss = new StmtSeq();
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseProg() {
        Parser.matchConsume("program", "Begin");
        this.ds.parseDeclSeq();
        Parser.matchConsume("begin", "Begin");
        this.ss.parseStmtSeq();
        Parser.matchConsume("end", "Begin");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printProg() {
        System.out.print("program\n");
        Parser.increaseIndent();
        this.ds.printDeclSeq();
        System.out.print(Parser.indent() + "begin\n");
        Parser.increaseIndent();
        this.ss.printStmtSeq();
        Parser.decreaseIndent();
        System.out.print(Parser.indent() + "end");
        Parser.decreaseIndent(); 
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execBegin() {
        // Left blank for Project 2
    }
    
}