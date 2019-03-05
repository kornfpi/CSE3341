

public class Term{
        private int alt;
        private Fac fac;
        private Term term;
        public Term() {
            this.fac = new Fac();
        }
        public void parseTerm() {
            this.alt = 1;
            this.fac.parseFac();
            if(Global.tokenizer.currentToken().symbol.equals("*")) {
                Global.tokenizer.nextToken();
                this.alt = 2;
                this.term = new Term();
                this.term.parseTerm();
            }
        }
        public void printTerm() {
            this.fac.printFac();
            if(this.alt == 2) {
                System.out.print(" * ");
                this.term.printTerm();
            }
        }
        public void execTerm() {
            // Left blank for Project 2
        }
    }
    