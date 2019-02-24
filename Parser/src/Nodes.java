
public class Nodes {

    private String indent;
    private Tokenizer tokenizer;
    private Begin programStart;
    
    public Nodes(Tokenizer tokenizer) {
        this.indent = "";
        this.tokenizer = tokenizer;
        this.programStart = new Begin();
        programStart.parseBegin();
    }
    
    public void prettyPrint() {
        this.programStart.printBegin();
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
    
    private void increaseIndent() {
        this.indent += "  ";
    }
    
    private void decreaseIndent() {
        if(indent.length() >= 2) {
            this.indent = this.indent.substring(0, this.indent.length() - 2);
        }
    }
    
    private class Begin{
        private DeclSeq ds;
        private StmtSeq ss;
        private Begin() {      
            this.ds = new DeclSeq();
            this.ss = new StmtSeq();
        }
        private void parseBegin() {
            matchConsume("program");
            this.ds.parseDeclSeq();
            matchConsume("begin");
            //this.ss.parseStmtSeq();
            //matchConsume("end");
        }
        private void printBegin() {
            System.out.print("program\n");
            increaseIndent();
            this.ds.printDeclSeq();
            System.out.print(indent + "begin\n");
            increaseIndent();
            this.ss.printStmtSeq();
            decreaseIndent();
            System.out.print(indent + "end");
            decreaseIndent(); 
        }
        private void execBegin() {
            // Left blank for Project 2
        }
    }
    
    private class DeclSeq{
        private int alt;
        private Decl d;
        private DeclSeq ds;
        private DeclSeq() {
            this.alt = 1;
            this.d = new Decl();
            this.ds = null;
        }
        private void parseDeclSeq() {
            this.d.parseDecl();
            if(tokenizer.currentToken().symbol.equals("int")) {
                this.alt = 2;
                this.ds = new DeclSeq();
                this.ds.parseDeclSeq();
            }
        }
        private void printDeclSeq() {
            this.d.printDecl();
            if(this.alt == 2) {
                this.ds.printDeclSeq();
            } 
        }
        private void execDeclSeq() {
            // Left blank for Project 2
        }
    }
    
    private class Decl{
        private IDList idl;
        private Decl() {
            this.idl = new IDList();
        }
        private void parseDecl() {
            matchConsume("int");
            this.idl.parseIDList();
            matchConsume(";");
        }
        private void printDecl() {
            System.out.print(indent + "int ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        private void execDecl() {
            // Left blank for Project 2
        }
    }
    
    private class IDList{
        private int alt;
        private ID id;
        private IDList idl;
        private IDList() {
            this.alt = 1;
            this.id = new ID();
            this.idl = null;
        }
        private void parseIDList() {
            this.id.parseID();
            if(tokenizer.currentToken().symbol.equals(",")) {
                tokenizer.nextToken();
                this.alt = 2;
                this.idl = new IDList();
                this.idl.parseIDList();
            }
        }
        private void printIDList() {
            this.id.printID();
            if(this.alt == 2) {
                System.out.print(", ");
                this.idl.printIDList();
            }
        }
        private void execIDList() {
            // Left blank for Project 2
        }
    }
    
    private class ID{
        private String identifier;
        public ID() {
            this.identifier = null;
        }
        private void parseID() {
            if(tokenizer.currentToken().type.equals("IDENT")) {
                this.identifier = tokenizer.currentToken().symbol;
                tokenizer.nextToken(); // Consume name
            }else {
                System.out.println("ERROR");
                System.out.println(tokenizer.currentToken().symbol);
                System.exit(0);
            }
            
        }
        private void printID() {
            System.out.print(this.identifier);
        }
        private void execID() {
            // Left blank for Project 2
        }
    }
    
    private class StmtSeq{
        private int alt;
        private Decl d;
        private DeclSeq ds;
        private StmtSeq() {

        }
        private void parseStmtSeq() {
            
        }
        private void printStmtSeq() {
            
        }
        private void execStmtSeq() {
            
        }
    }
      
}
