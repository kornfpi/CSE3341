

public class Stmt{
        private int alt;
        private Assign a;
        private If i_f;
        private Loop loop;
        private In i_n;
        private Out o;
        public Stmt() {
        }
        public void parseStmt() {
            this.alt = Parser.stmtType();
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
                    String tokenSymbol = Parser.currentToken().symbol;
                    int tokenLine = Parser.currentToken().line;
                    System.out.println("Error! Statement (Line " + tokenLine + ") Expected identifier, if, loop, in, or out but found \"" + tokenSymbol + "\"");
                    System.exit(0);
            }     
        }
        public void printStmt() {
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
        public void execStmt() {
            // Left blank for Project 2
        }
    }
