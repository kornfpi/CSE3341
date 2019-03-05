

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
            Parser.matchConsume("if");
            this.cond.parseCond();
            Parser.matchConsume("then");
            this.ss1.parseStmtSeq();
            if(Parser.currentToken().symbol.equals("else")) {
                Parser.nextToken();
                this.alt = 2;
                this.ss2 = new StmtSeq();
                this.ss2.parseStmtSeq();
            }
            Parser.matchConsume("end");
            Parser.matchConsume(";");
        }
        public void printIf() {
            System.out.print(Parser.indent() + "if ");
            this.cond.printCond();
            System.out.print(" then\n");
            Parser.increaseIndent();
            this.ss1.printStmtSeq();
            Parser.decreaseIndent();
            if(this.alt == 2) {
                System.out.print(Parser.indent() + "else\n");
                Parser.increaseIndent();
                this.ss2.printStmtSeq();
                Parser.decreaseIndent();
            }
            System.out.print(Parser.indent() + "end;\n");
            
        }
        public void execIf() {
            // Left blank for Project 2
        }
    }
