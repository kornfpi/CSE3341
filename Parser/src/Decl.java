

public class Decl{
        private IDList_B idl;
        public Decl() {
            this.idl = new IDList_B(true);
        }
        public void parseDecl() {
            Global.matchConsume("int");
            this.idl.parseIDList();
            Global.matchConsume(";");
        }
        public void printDecl() {
            System.out.print(Global.indent + "int ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        public void execDecl() {
            // Left blank for Project 2
        }
    }
