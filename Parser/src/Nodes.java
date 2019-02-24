
public class Nodes {

    /**
     * The initial starting node of the program (program node)
     */
    private Tokenizer tokenizer;
    private Begin programStart;
    
    public Nodes(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.programStart = new Begin();
        programStart.parseBegin();
    }
    
    private void matchConsume(String matchString) {
        String tokenSymbol = this.tokenizer.currentToken().symbol;
        int tokenLine = this.tokenizer.currentToken().line;
        if(!matchString.equals(tokenSymbol)) {
            System.out.println("Error! (Line " + tokenLine + ") Expected \"" 
                    + matchString + "\" but found \"" + tokenSymbol + "\"" );
            System.exit(0);
        }else {
            this.tokenizer.nextToken();
        }
    }
    
    private class Begin{
        private DeclSeq ds;
        private StmtSeq ss;
        public Begin() {      
            this.ds = new DeclSeq();
            this.ss = new StmtSeq();
        }
        public void parseBegin() {
            matchConsume("program");
            this.ds.parseDeclSeq();
            matchConsume("begin");
            this.ss.parseStmtSeq();
            matchConsume("end");
        }
        public void printBegin() {
            
        }
        public void execBegin() {
            
        }
    }
    
    private class DeclSeq{
        private int alt;
        private Decl d;
        private DeclSeq ds;
        public DeclSeq() {
            this.alt = 1;
            this.d = new Decl();
            this.ds = null;
        }
        public void parseDeclSeq() {
            this.d.parseDecl();
            if(tokenizer.currentToken().symbol.equals("int")) {
                this.alt = 2;
                this.ds = new DeclSeq();
                this.ds.parseDeclSeq();
            }
        }
        public void printDeclSeq() {
            
        }
        public void execDeclSeq() {
            
        }
    }
    
    private class Decl{
        private IDList idl;
        public Decl() {
            this.idl = new IDList();
        }
        public void parseDecl() {
            matchConsume("int");
            this.idl.parseIDList();
            matchConsume(";");
        }
        public void printDecl() {
            
        }
        public void execDecl() {
            
        }
    }
    
    private class IDList{
        private int alt;
        private ID id;
        private IDList idl;
        public IDList() {
            this.alt = 1;
            this.id = new ID();
            this.idl = null;
        }
        public void parseIDList() {
            this.id.parseID();
            if(tokenizer.currentToken().symbol.equals(",")) {
                tokenizer.nextToken();
                this.alt = 2;
                this.idl = new IDList();
                this.idl.parseIDList();
            }
        }
        public void printIDList() {
            
        }
        public void execIDList() {
            
        }
    }
    
    private class ID{
        private String name;
        public ID() {
            this.name = null;
        }
        public void parseID() {
            if(tokenizer.currentToken().type.equals("IDENT")) {
                this.name = tokenizer.currentToken().symbol;
                tokenizer.nextToken(); // Consume name
            }else {
                System.out.println("ERROR");
                System.out.println(tokenizer.currentToken().symbol);
                System.exit(0);
            }
            
        }
        public void printID() {
            
        }
        public void execID() {
            
        }
    }
    
    private class StmtSeq{
        private int alt;
        private Decl d;
        private DeclSeq ds;
        public StmtSeq() {

        }
        public void parseStmtSeq() {
            
        }
        public void printStmtSeq() {
            
        }
        public void execStmtSeq() {
            
        }
    }
    

    
}
