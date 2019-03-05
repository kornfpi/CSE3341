

public class If{
        private int alt;
        private Cond cond;
        private StmtSeq ss1;
        private StmtSeq ss2;
        public If() {
            this.alt = 1;
            this.cond = new Cond();
            this.ss1 = new StmtSeq();
        }
        public void parseIf() {
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
        public void printIf() {
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
        public void execIf() {
            // Left blank for Project 2
        }
    }
