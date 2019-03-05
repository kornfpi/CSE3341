

public class DeclSeq{
        private int alt;
        private Decl d;
        private DeclSeq ds;
        public DeclSeq() {
            this.alt = 1;
            this.d = new Decl();
        }
        public void parseDeclSeq() {
            this.d.parseDecl();
            if(Parser.currentToken().symbol.equals("int")) {
                this.alt = 2;
                this.ds = new DeclSeq();
                this.ds.parseDeclSeq();
            }
        }
        public void printDeclSeq() {
            this.d.printDecl();
            if(this.alt == 2) {
                this.ds.printDeclSeq();
            } 
        }
        public void execDeclSeq() {
            // Left blank for Project 2
        }
    }
