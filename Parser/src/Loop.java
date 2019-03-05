


public class Loop{
        private Cond cond;
        private StmtSeq ss;
        public Loop() {
            this.cond = new Cond();
            this.ss = new StmtSeq();
        }
        public void parseLoop() {
            Parser.matchConsume("while");
            this.cond.parseCond();
            Parser.matchConsume("loop");
            this.ss.parseStmtSeq();
            Parser.matchConsume("end");
            Parser.matchConsume(";");
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