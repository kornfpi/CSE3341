/**
 * DeclSeq Class
 * Contains sub-nodes and methods associated with <decl-seq> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class DeclSeq{
    private int alt;
    private Decl d;
    private DeclSeq ds;
    public DeclSeq() {
        this.alt = 1;
        this.d = new Decl();
    }
    public void parseDeclSeq() {
        this.d.parseDecl();
        if(Parser.currentToken().symbol.equals("int")) {
            this.alt = 2;
            this.ds = new DeclSeq();
            this.ds.parseDeclSeq();
        }
    }
    public void printDeclSeq() {
        this.d.printDecl();
        if(this.alt == 2) {
            this.ds.printDeclSeq();
        } 
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execDeclSeq() {
        // Left blank for Project 2
    }
    
}
