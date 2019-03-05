

public class IDList{
        private boolean isDecl;
        private int alt;
        private ID id;
        private IDList idl;
        public IDList(boolean isDecl) {
            this.isDecl = isDecl;
            this.alt = 1;
            this.id = new ID(isDecl);
        }
        public void parseIDList() {
            this.id.parseID();
            if(Parser.currentToken().symbol.equals(",")) {
                Parser.nextToken();
                this.alt = 2;
                this.idl = new IDList(isDecl);
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