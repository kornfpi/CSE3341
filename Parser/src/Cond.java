/**
 * Cond Class
 * Contains sub-nodes and methods associated with <cond> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Cond{
    
    /**
     * Private members for sub-nodes and alt.
     */
    private int alt;
    private Comp comp;
    private Cond cond1;
    private Cond cond2;
    
    /**
     * No argument constructor. 
     */
    public Cond() {
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseCond() {
        if(Interpreter.currentToken().symbol.equals("!")) {
            Interpreter.nextToken();
            this.alt = 2;
            this.cond1 = new Cond();
            this.cond1.parseCond();
        }else if(Interpreter.currentToken().symbol.equals("[")) {
            Interpreter.nextToken();
            this.cond1 = new Cond();
            this.cond1.parseCond();
            if(Interpreter.currentToken().symbol.equals("and")) {
                Interpreter.nextToken();
                this.alt = 3;
                this.cond2 = new Cond();
                this.cond2.parseCond();
                Interpreter.matchConsume("]", "Cond");
            }else if(Interpreter.currentToken().symbol.equals("or")) {
                Interpreter.nextToken();
                this.alt = 4;
                this.cond2 = new Cond();
                this.cond2.parseCond();
                Interpreter.matchConsume("]", "Cond");
            }else {
                System.out.print("ERROR");
                System.exit(0);
            }
        }else {
            this.alt = 1;
            this.comp = new Comp();
            this.comp.parseComp();
        }  
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printCond() {
        switch(this.alt) {
            case(1):
                this.comp.printComp();
                break;
            case(2):
                System.out.print("!");
                this.cond1.printCond();
                break;
            case(3):
                System.out.print("[ ");
                this.cond1.printCond();
                System.out.print(" and ");
                this.cond2.printCond();
                System.out.print(" ]");
                break;
            case(4):
                System.out.print("[ ");
                this.cond1.printCond();
                System.out.print(" or ");
                this.cond2.printCond();
                System.out.print(" ]");
                break;
        }
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public boolean execCond() {
        boolean takeBranch = false, cond1 = false, cond2 = false;
        switch(this.alt) {
            case(1):
                takeBranch = this.comp.execComp();
                break;
            case(2):
                takeBranch = !this.cond1.execCond();
                break;
            case(3):
                cond1 = this.cond1.execCond();
                cond2 = this.cond2.execCond();
                takeBranch = cond1 && cond2;
                break;
            case(4):
                cond1 = this.cond1.execCond();
                cond2 = this.cond2.execCond();
                takeBranch = cond1 || cond2;
                break;
        }
        return takeBranch;
    }
    
}
    
