


public class Loop{
        private Cond cond;
        private StmtSeq ss;
        public Loop() {
            this.cond = new Cond();
            this.ss = new StmtSeq();
        }
        public void parseLoop() {
            Global.matchConsume("while");
            this.cond.parseCond();
            Global.matchConsume("loop");
            this.ss.parseStmtSeq();
            Global.matchConsume("end");
            Global.matchConsume(";");
        }
        public void printLoop() {
            System.out.print(Global.indent + "while ");
            this.cond.printCond();
            System.out.print(" loop\n");
            Global.increaseIndent();
            this.ss.printStmtSeq();
            Global.decreaseIndent();
            System.out.print(Global.indent + "end;\n");
            
        }
        public void execLoop() {
            // Left blank for Project 2
        }
    }