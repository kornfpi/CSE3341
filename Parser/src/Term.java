/**
 * Term Class
 * Contains sub-nodes and methods associated with <term> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Term{
    private int alt;
    private Fac fac;
    private Term term;
    public Term() {
        this.fac = new Fac();
    }
    public void parseTerm() {
        this.alt = 1;
        this.fac.parseFac();
        if(Parser.currentToken().symbol.equals("*")) {
            Parser.nextToken();
            this.alt = 2;
            this.term = new Term();
            this.term.parseTerm();
        }
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printTerm() {
        this.fac.printFac();
        if(this.alt == 2) {
            System.out.print(" * ");
            this.term.printTerm();
        }
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execTerm() {
        // Left blank for Project 2
    }
    
}
    