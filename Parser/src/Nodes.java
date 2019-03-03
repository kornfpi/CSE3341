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
        private Assign a;
        private If i_f;
        private Loop loop;
        private In i_n;
        private Out o;
        private Stmt() {
        }
        private void parseStmt() {
            this.alt = Global.stmtType();
            switch (this.alt) {
                case(1):
                    this.a = new Assign();
                    this.a.parseAssign();
                    break;
                case(2):
                    this.i_f = new If();
                    this.i_f.parseIf();
                    break;
                case(3):
                    this.loop = new Loop();
                    this.loop.parseLoop();
                    break;
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
                    System.out.println("Error! Statement (Line " + tokenLine + ") Expected identifier, if, loop, in, or out but found \"" + tokenSymbol + "\"");
                    System.exit(0);
            }     
        }
        private void printStmt() {
            switch (this.alt) {
                case(1):
                    this.a.printAssign();
                    break;
                case(2):
                    this.i_f.printIf();
                    break;
                case(3):
                    this.loop.printLoop();
                    break;
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
      
    private class Assign{
        private ID_B id;
        private Expr expr;
        private Assign() {
            this.id = new ID_B(false);
            this.expr = new Expr();
        }
        private void parseAssign() {
            this.id.parseID();
            Global.matchConsume("=");
            this.expr.parseExpr();
            Global.matchConsume(";");
        }
        private void printAssign() {
            System.out.print(Global.indent);
            this.id.printID();
            System.out.print(" = ");
            this.expr.printExpr();
            System.out.print(";\n");
            
        }
        private void execAssign() {
            // Left blank for Project 2
        }
    }
    
    
    private class If{
        private int alt;
        private Cond cond;
        private StmtSeq ss1;
        private StmtSeq ss2;
        private If() {
            this.alt = 1;
            this.cond = new Cond();
            this.ss1 = new StmtSeq();
        }
        private void parseIf() {
            Global.matchConsume("if");
            this.cond.parseCond();
            Global.matchConsume("then");
            this.ss1.parseStmtSeq();
            if(Global.tokenizer.currentToken().symbol.equals("else")) {
                Global.tokenizer.nextToken();
                this.alt = 2;
                this.ss2 = new StmtSeq();
                this.ss2.parseStmtSeq();
            }
            Global.matchConsume("end");
            Global.matchConsume(";");
        }
        private void printIf() {
            System.out.print(Global.indent + "if ");
            this.cond.printCond();
            System.out.print(" then\n");
            Global.increaseIndent();
            this.ss1.printStmtSeq();
            Global.decreaseIndent();
            if(this.alt == 2) {
                System.out.print(Global.indent + "else\n");
                Global.increaseIndent();
                this.ss2.printStmtSeq();
                Global.decreaseIndent();
            }
            System.out.print(Global.indent + "end;\n");
            
        }
        private void execIf() {
            // Left blank for Project 2
        }
    }
 
    private class Loop{
        private Cond cond;
        private StmtSeq ss;
        private Loop() {
            this.cond = new Cond();
            this.ss = new StmtSeq();
        }
        private void parseLoop() {
            Global.matchConsume("while");
            this.cond.parseCond();
            Global.matchConsume("loop");
            this.ss.parseStmtSeq();
            Global.matchConsume("end");
            Global.matchConsume(";");
        }
        private void printLoop() {
            System.out.print(Global.indent + "while ");
            this.cond.printCond();
            System.out.print(" loop\n");
            Global.increaseIndent();
            this.ss.printStmtSeq();
            Global.decreaseIndent();
            System.out.print(Global.indent + "end;\n");
            
        }
        private void execLoop() {
            // Left blank for Project 2
        }
    }
    
    
    
    private class Comp{
        private String compOp;
        private Fac fac1;
        private Fac fac2;
        private Comp() {
            this.fac1 = new Fac();
            this.fac2 = new Fac();
        }
        private void parseComp() {
            Global.matchConsume("(");
            this.fac1.parseFac();
            if(!isComp(Global.tokenizer.currentToken().symbol)) {
                System.out.println("ERROR expected comp");
                System.exit(0);
            }
            this.compOp = Global.tokenizer.currentToken().symbol;
            Global.tokenizer.nextToken();
            this.fac2.parseFac();
            Global.matchConsume(")");
        }
        private void printComp() {
            System.out.print("( ");
            this.fac1.printFac();
            System.out.print(" " + this.compOp + " ");
            this.fac2.printFac();
            System.out.print(" )");
            
        }
        private void execComp() {
            // Left blank for Project 2
        }
        
        private boolean isComp(String inString) {
            String[] compOpsArray = new String[]{"!=","==",">=","<=","<",">"};
            Set<String> compOpsSet = new HashSet<>(Arrays.asList(compOpsArray));
            return compOpsSet.contains(inString);
        }
        
    }
    
    
    
    private class Cond{
        private int alt;
        private Comp comp;
        private Cond cond1;
        private Cond cond2;
        private Cond() {
        }
        private void parseCond() {
            if(Global.tokenizer.currentToken().symbol.equals("!")) {
                Global.tokenizer.nextToken();
                this.alt = 2;
                this.cond1 = new Cond();
                this.cond1.parseCond();
            }else if(Global.tokenizer.currentToken().symbol.equals("[")) {
                Global.tokenizer.nextToken();
                this.cond1 = new Cond();
                this.cond1.parseCond();
                if(Global.tokenizer.currentToken().symbol.equals("and")) {
                    Global.tokenizer.nextToken();
                    this.alt = 3;
                    this.cond2 = new Cond();
                    this.cond2.parseCond();
                    Global.matchConsume("]");
                }else if(Global.tokenizer.currentToken().symbol.equals("or")) {
                    Global.tokenizer.nextToken();
                    this.alt = 4;
                    this.cond2 = new Cond();
                    this.cond2.parseCond();
                    Global.matchConsume("]");
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
        private void printCond() {
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
        private void execCond() {
            // Left blank for Project 2
        }
    }
    
    
       
    private class Expr{
        private int alt;
        private Term term;
        private Expr expr;
        private Expr() {
            this.term = new Term();
        }
        private void parseExpr() {
            this.term.parseTerm();
            String tokenSymbol = Global.tokenizer.currentToken().symbol;
            int tokenLine = Global.tokenizer.currentToken().line;
            switch (tokenSymbol) {
                case(";"):
                    this.alt = 1;
                    break;
                case("+"):
                    this.alt = 2;
                    Global.tokenizer.nextToken();
                    this.expr = new Expr();
                    this.expr.parseExpr();
                    break;
                case("-"):
                    this.alt = 3;
                    Global.tokenizer.nextToken();
                    this.expr = new Expr();
                    this.expr.parseExpr();
                    break;
                case(")"):
                    this.alt = 4;
                    break;
                default:
                    System.out.println("Error! Expression (Line " + tokenLine + ") Expected \";\", \"-\", or \"+\", but found \"" + tokenSymbol + "\"");
                    System.exit(0);
            }     
        }
        private void printExpr() {
            this.term.printTerm();
            switch (this.alt) {
                case(2):
                    System.out.print(" + ");
                    this.expr.printExpr();
                    break;
                case(3):
                    System.out.print(" - ");
                    this.expr.printExpr();
                    break;
            }
        }
        private void execExpr() {
            // Left blank for Project 2
        }
    }
    
    private class Term{
        private int alt;
        private Fac fac;
        private Term term;
        private Term() {
            this.fac = new Fac();
        }
        private void parseTerm() {
            this.alt = 1;
            this.fac.parseFac();
            if(Global.tokenizer.currentToken().symbol.equals("*")) {
                Global.tokenizer.nextToken();
                this.alt = 2;
                this.term = new Term();
                this.term.parseTerm();
            }
        }
        private void printTerm() {
            this.fac.printFac();
            if(this.alt == 2) {
                System.out.print(" * ");
                this.term.printTerm();
            }
        }
        private void execTerm() {
            // Left blank for Project 2
        }
    }
    
    
    private class Fac{
        private int alt;
        private int intValue;
        private ID_B id;
        private Expr expr;
        private Fac() {
        }
        private void parseFac() {
            String type = Global.tokenizer.currentToken().type;
            String tokenSymbol = Global.tokenizer.currentToken().symbol;
            int tokenLine = Global.tokenizer.currentToken().line;
            switch (type) {
                case("IDENT"):
                    if(Global.isInt(tokenSymbol)) {
                        this.alt = 1;
                        this.intValue = Integer.parseInt(tokenSymbol);
                        Global.tokenizer.nextToken();
                    }else {
                        this.alt = 2;
                        this.id = new ID_B(false);
                        this.id.parseID();
                    }
                    break;
                case("SPEC"):
                    this.alt = 3;
                    Global.matchConsume("(");
                    this.expr = new Expr();
                    this.expr.parseExpr();
                    Global.matchConsume(")");
                    break;
                default:
                    System.out.println("Error! Factor (Line " + tokenLine + ") Expected identifier, integer, or \"(\", but found \"" + tokenSymbol + "\"");
                    System.exit(0);
            }   
        }
        private void printFac() {
            switch (this.alt) {
                case(1):
                    System.out.print(this.intValue);
                    break;
                case(2):
                    this.id.printID();
                    break;
                case(3):
                    System.out.print("( ");
                    this.expr.printExpr();
                    System.out.print(" )");
                    break;
            }
        }
        private void execFac() {
            // Left blank for Project 2
        }
    }
    
    
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
