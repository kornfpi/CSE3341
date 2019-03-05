


public class Loop{
        private Cond cond;
        private StmtSeq ss;
        public Loop() {
            this.cond = new Cond();
            this.ss = new StmtSeq();
        }
        public void parseLoop() {
            Parser.matchConsume("while", "Loop");
            this.cond.parseCond();
            Parser.matchConsume("loop", "Loop");
            this.ss.parseStmtSeq();
            Parser.matchConsume("end", "Loop");
            Parser.matchConsume(";", "Loop");
        }
        public void printLoop() {
            System.out.print(Parser.indent() + "while ");
            this.cond.printCond();
            System.out.print(" loop\n");
            Parser.increaseIndent();
            this.ss.printStmtSeq();
            Parser.decreaseIndent();
            System.out.print(Parser.indent() + "end;\n");
            
        }
        public void execLoop() {
            // Left blank for Project 2
        }
    }