/**
 * Term Class
 * Contains sub-nodes and methods associated with <term> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Term{
    
    /**
     * Private members for sub-nodes and alt.
     */
    private int alt;
    private Fac fac;
    private Term term;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public Term() {
        this.fac = new Fac();
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseTerm() {
        this.alt = 1;
        this.fac.parseFac();
        if(Interpreter.currentToken().symbol.equals("*")) {
            Interpreter.nextToken();
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
    public int execTerm() {
        long value1 = 0, finalValue = 0;
        finalValue = this.fac.execFac();
        if(this.alt == 2) {
            value1 = (long)this.term.execTerm();
            finalValue = finalValue * value1;
            Interpreter.checkOverflow(finalValue);
        }
        return (int)finalValue;
    }
    
}
    