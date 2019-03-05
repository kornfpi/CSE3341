
public class Begin{
        private DeclSeq ds;
        private StmtSeq ss;
        public Begin() {      
            this.ds = new DeclSeq();
            this.ss = new StmtSeq();
        }
        public void parseBegin() {
            Parser.matchConsume("program");
            this.ds.parseDeclSeq();
            Parser.matchConsume("begin");
            this.ss.parseStmtSeq();
            Parser.matchConsume("end");
        }
        public void printBegin() {
            System.out.print("program\n");
            Parser.increaseIndent();
            this.ds.printDeclSeq();
            System.out.print(Parser.indent() + "begin\n");
            Parser.increaseIndent();
            this.ss.printStmtSeq();
            Parser.decreaseIndent();
            System.out.print(Parser.indent() + "end");
            Parser.decreaseIndent(); 
        }
        public void execBegin() {
            // Left blank for Project 2
        }
    }