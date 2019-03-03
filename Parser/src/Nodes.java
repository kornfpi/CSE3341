import java.util.*;
public class Nodes {

    private Begin programStart;
    
    public Nodes(Tokenizer tokenizer) {
        this.programStart = new Begin();
    }
    
    public void parseTokens() {
        this.programStart.parseBegin();
    }
    
    public void prettyPrint() {
        this.programStart.printBegin();
    }
    


    
    private class Begin{
        private DeclSeq ds;
        private StmtSeq ss;
        private Begin() {      
            this.ds = new DeclSeq();
            this.ss = new StmtSeq();
        }
        private void parseBegin() {
            Global.matchConsume("program");
            this.ds.parseDeclSeq();
            Global.matchConsume("begin");
            this.ss.parseStmtSeq();
            Global.matchConsume("end");
        }
        private void printBegin() {
            System.out.print("program\n");
            Global.increaseIndent();
            this.ds.printDeclSeq();
            System.out.print(Global.indent + "begin\n");
            Global.increaseIndent();
            this.ss.printStmtSeq();
            Global.decreaseIndent();
            System.out.print(Global.indent + "end");
            Global.decreaseIndent(); 
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
            if(Global.tokenizer.currentToken().symbol.equals("int")) {
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
        private IDList_B idl;
        private Decl() {
            this.idl = new IDList_B(true);
        }
        private void parseDecl() {
            Global.matchConsume("int");
            this.idl.parseIDList();
            Global.matchConsume(";");
        }
        private void printDecl() {
            System.out.print(Global.indent + "int ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        private void execDecl() {
            // Left blank for Project 2
        }
    }
    
    
    private class IDList_B{
        private boolean isDecl;
        private int alt;
        private ID_B id;
        private IDList_B idl;
        private IDList_B(boolean isDecl) {
            this.isDecl = isDecl;
            this.alt = 1;
            this.id = new ID_B(isDecl);
        }
        private void parseIDList() {
            this.id.parseID();
            if(Global.tokenizer.currentToken().symbol.equals(",")) {
                Global.tokenizer.nextToken();
                this.alt = 2;
                this.idl = new IDList_B(isDecl);
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
    
    private class ID_B{
        private String identifier;
        private boolean isDecl;
        public ID_B(boolean isDecl) {
            this.isDecl = isDecl;
            this.identifier = null;
        }
        private void parseID() {
            
            if(isDecl) {
            
                if(Global.tokenizer.currentToken().type.equals("IDENT")) {
                    this.identifier = Global.tokenizer.currentToken().symbol;
                    Global.tokenizer.nextToken(); // Consume name
                    if(Global.hasSymbol(this.identifier)) {
                        System.out.println("Error! Multiple declarations of identifier \"" + this.identifier + "\"");
                        System.exit(0);
                    }else {
                        Global.addSymbol(this.identifier);
                    }
                }else {
                    String tokenSymbol = Global.tokenizer.currentToken().symbol;
                    int tokenLine = Global.tokenizer.currentToken().line;
                    System.out.println("Error! (Line " + tokenLine + ") Expected identifier but found \"" + tokenSymbol + "\"");
                    System.exit(0);
                }
            
            }else {
                
                
                String tokenSymbol = Global.tokenizer.currentToken().symbol;
                int tokenLine = Global.tokenizer.currentToken().line;
                if(Global.tokenizer.currentToken().type.equals("IDENT")) {
                    this.identifier = Global.tokenizer.currentToken().symbol;
                    Global.tokenizer.nextToken(); // Consume name
                    if(!Global.hasSymbol(this.identifier)) {
                        System.out.println("Error! (Line " + tokenLine + ") Undeclared identifier \"" + tokenSymbol + "\"");
                        System.exit(0);
                    }
                }else {
                    System.out.println("Error! (Line " + tokenLine + ") Expected identifier but found \"" + tokenSymbol + "\"");
                    System.exit(0);
                }   
                          
                
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
            if(Global.stmtType() > 0) {
                this.alt = 2;
                this.ss = new StmtSeq();
                this.ss.parseStmtSeq();
            }  
        }
        private void printStmtSeq() {
            this.s.printStmt();
            if(this.alt == 2) {
                this.ss.printStmtSeq();
            }
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
            this.alt = Global.stmtType();
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
                    String tokenSymbol = Global.tokenizer.currentToken().symbol;
                    int tokenLine = Global.tokenizer.currentToken().line;
                    System.out.println("Error! (Line " + tokenLine + ") Expected identifier, if, loop, in, out but found \"" + tokenSymbol + "\"");
                    System.exit(0);
            }     
        }
        private void printStmt() {
            switch (this.alt) {
//                case(1):
//                    this.a.printAssign();
//                    break;
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
        private IDList_B idl;
        private In() {
            this.idl = new IDList_B(false);
        }
        private void parseIn() {
            Global.matchConsume("read");
            this.idl.parseIDList();
            Global.matchConsume(";");
        }
        private void printIn() {
            System.out.print(Global.indent + "read ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        private void execIn() {
            // Left blank for Project 2
        }
    }
    
    private class Out{
        private IDList_B idl;
        private Out() {
            this.idl = new IDList_B(false);
        }
        private void parseOut() {
            Global.matchConsume("write");
            this.idl.parseIDList();
            Global.matchConsume(";");
        }
        private void printOut() {
            System.out.print(Global.indent + "write ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        private void execOut() {
            // Left blank for Project 2
        }
    }
    
}
