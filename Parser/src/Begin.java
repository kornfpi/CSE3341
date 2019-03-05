
public class Begin{
        private DeclSeq ds;
        private StmtSeq ss;
        public Begin() {      
            this.ds = new DeclSeq();
            this.ss = new StmtSeq();
        }
        public void parseBegin() {
            Global.matchConsume("program");
            this.ds.parseDeclSeq();
            Global.matchConsume("begin");
            this.ss.parseStmtSeq();
            Global.matchConsume("end");
        }
        public void printBegin() {
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
        public void execBegin() {
            // Left blank for Project 2
        }
    }