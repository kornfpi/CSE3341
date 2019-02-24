import java.util.*;
public class Nodes {

    private String indent;
    private Tokenizer tokenizer;
    private Begin programStart;
    private HashMap<String, Integer> symbolTable;
    
    public Nodes(Tokenizer tokenizer) {
        this.indent = "";
        this.tokenizer = tokenizer;
        this.programStart = new Begin();
        this.symbolTable = new HashMap<String, Integer>();
    }
    
    public void parseTokens() {
        this.programStart.parseBegin();
    }
    
    public void prettyPrint() {
        this.programStart.printBegin();
    }
    
    private void matchConsume(String matchString) {
        String tokenSymbol = this.tokenizer.currentToken().symbol;
        int tokenLine = this.tokenizer.currentToken().line;
        if(!matchString.equals(tokenSymbol)) {
            System.out.println("Error! (Line " + tokenLine + ") Expected \"" + matchString + "\" but found \"" + tokenSymbol + "\"" );
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
    
    private int stmtType(String inString) {
        int type = 0;
        if(this.symbolTable.containsKey(inString)) type = 1;
        if(inString.equals("if")) type = 2;
        if(inString.equals("while")) type = 3;
        if(inString.equals("read")) type = 4;
        if(inString.equals("write")) type = 5;
        return type;
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
            this.ss.parseStmtSeq();
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
                if(symbolTable.containsKey(this.identifier)) {
                    System.out.println("Error! Multiple declarations of identifier \"" + this.identifier + "\"");
                    System.exit(0);
                }else {
                    symbolTable.put(this.identifier, 0);
                }
            }else {
                String tokenSymbol = tokenizer.currentToken().symbol;
                int tokenLine = tokenizer.currentToken().line;
                System.out.println("Error! (Line " + tokenLine + ") Expected identifier but found \"" + tokenSymbol + "\"");
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
        private Stmt s;
        private StmtSeq ss;
        private StmtSeq() {
            this.alt = 1;
            this.s = new Stmt();
        }
        private void parseStmtSeq() {
            this.s.parseStmt();
            if(stmtType(tokenizer.currentToken().symbol) > 0) {
                this.alt = 2;
                this.ss = new StmtSeq();
                this.ss.parseStmtSeq();
            }  
        }
        private void printStmtSeq() {
            increaseIndent();
            this.s.printStmt();
            if(this.alt == 2) {
                this.ss.printStmtSeq();
            }
            decreaseIndent();
        }
        private void execStmtSeq() {
            // Left blank for Project 2
        }
    }
    
    private class Stmt{
        private int alt;
//        private Assign a;
//        private If i_f;
//        private Loop l;
        private In i_n;
        private Out o;
        private Stmt() {
        }
        private void parseStmt() {
            this.alt = stmtType(tokenizer.currentToken().symbol);
            switch (this.alt) {
//                case(1):
//                    this.a.parseAssign();
//                    break;
//                case(2):
//                    this.i_f.parseIf();
//                    break;
//                case(3):
//                    this.loop.parseLoop();
//                    break;
                case(4):
                    this.i_n = new In();
                    this.i_n.parseIn();
                    break;
                case(5):
                    this.o = new Out();
                    this.o.parseOut();
                    break;
                default:
                    String tokenSymbol = tokenizer.currentToken().symbol;
                    int tokenLine = tokenizer.currentToken().line;
                    System.out.println("Error! (Line " + tokenLine + ") Expected identifier, if, loop, in, out but found \"" + tokenSymbol + "\"");
                    System.exit(0);
            }     
        }
        private void printStmt() {
            switch (this.alt) {
//          case(1):
//              this.a.printAssign();
//              break;
//          case(2):
//              this.i_f.printIf();
//              break;
//          case(3):
//              this.loop.printLoop();
//              break;
                case(4):
                    this.i_n.printIn();
                    break;
                case(5):
                    this.o.printOut();
                    break;
            }  
        }
        private void execStmt() {
            // Left blank for Project 2
        }
    }
      
//    private class Assign{
//        private String id;
//        private Expr e;
//        private Assign() {
//            this.id = null;
//            this.e = new Expr();
//        }
//        private void parseStmtSeq() {
//            this.s.parseStmt();
//            if(stmtType(tokenizer.currentToken().symbol) > 0) {
//                this.alt = 2;
//                this.ss = new StmtSeq();
//                this.ss.parseStmtSeq();
//            }  
//        }
//        private void printStmtSeq() {
//            
//        }
//        private void execStmtSeq() {
//            // Left blank for Project 2
//        }
//    }
    
    private class In{
        private ArrayList<String> idList;
        private In() {
            this.idList = new ArrayList<String>();
        }
        private void parseIn() {
            matchConsume("read");
            boolean identifiersRemain = true;
            while(identifiersRemain) {
                String identifier = tokenizer.currentToken().symbol;
                if(symbolTable.containsKey(identifier)) {
                    this.idList.add(identifier);
                    tokenizer.nextToken();
                }else {
                    System.out.println("Error: Undefined Identifier!");
                    System.exit(0);
                }
                if(tokenizer.currentToken().symbol.equals(";")) {
                    identifiersRemain = false;
                    tokenizer.nextToken();
                }else if(tokenizer.currentToken().symbol.equals(",")) {
                    tokenizer.nextToken();
                }else {
                    System.out.println("Error: Expected , or ;");
                    System.exit(0);
                }
            }
        }
        private void printIn() {
            System.out.print(indent + "read ");
            int i = 0;
            while(i < this.idList.size()) {
                System.out.print(this.idList.get(i));
                if(i != this.idList.size() - 1) {
                    System.out.print(", ");
                }
                i++;
            }
            System.out.print(";\n");
        }
        private void execIn() {
            // Left blank for Project 2
        }
    }
 
    private class Out{
        private ArrayList<String> idList;
        private Out() {
            this.idList = new ArrayList<String>();
        }
        private void parseOut() {
            matchConsume("write");
            boolean identifiersRemain = true;
            while(identifiersRemain) {
                String identifier = tokenizer.currentToken().symbol;
                if(symbolTable.containsKey(identifier)) {
                    this.idList.add(identifier);
                    tokenizer.nextToken();
                }else {
                    System.out.println("Error: Undefined Identifier!");
                    System.exit(0);
                }
                if(tokenizer.currentToken().symbol.equals(";")) {
                    identifiersRemain = false;
                    tokenizer.nextToken();
                }else if(tokenizer.currentToken().symbol.equals(",")) {
                    tokenizer.nextToken();
                }else {
                    System.out.println("Error: Expected , or ;");
                    System.exit(0);
                }
            }
        }
        private void printOut() {
            System.out.print(indent + "write ");
            int i = 0;
            while(i < this.idList.size()) {
                System.out.print(this.idList.get(i));
                if(i != this.idList.size() - 1) {
                    System.out.print(", ");
                }
                i++;
            }
            System.out.print(";\n");
        }
        private void execOut() {
            // Left blank for Project 2
        }
    }
    
}
