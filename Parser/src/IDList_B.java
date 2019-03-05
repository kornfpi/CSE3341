

public class IDList_B{
        private boolean isDecl;
        private int alt;
        private ID_B id;
        private IDList_B idl;
        public IDList_B(boolean isDecl) {
            this.isDecl = isDecl;
            this.alt = 1;
            this.id = new ID_B(isDecl);
        }
        public void parseIDList() {
            this.id.parseID();
            if(Global.tokenizer.currentToken().symbol.equals(",")) {
                Global.tokenizer.nextToken();
                this.alt = 2;
                this.idl = new IDList_B(isDecl);
                this.idl.parseIDList();
            }
        }
        public void printIDList() {
            this.id.printID();
            if(this.alt == 2) {
                System.out.print(", ");
                this.idl.printIDList();
            }
        }
        public void execIDList() {
            // Left blank for Project 2
        }
    }