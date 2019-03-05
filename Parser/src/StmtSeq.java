

public class StmtSeq{
        private int alt;
        private Stmt s;
        private StmtSeq ss;
        public StmtSeq() {
            this.alt = 1;
            this.s = new Stmt();
        }
        public void parseStmtSeq() {
            this.s.parseStmt();
            if(Parser.stmtType() > 0) {
                this.alt = 2;
                this.ss = new StmtSeq();
                this.ss.parseStmtSeq();
            }  
        }
        public void printStmtSeq() {
            this.s.printStmt();
            if(this.alt == 2) {
                this.ss.printStmtSeq();
            }
        }
        public void execStmtSeq() {
            // Left blank for Project 2
        }
    }
