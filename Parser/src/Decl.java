

public class Decl{
        private IDList idl;
        public Decl() {
            this.idl = new IDList(true);
        }
        public void parseDecl() {
            Parser.matchConsume("int", "Decl");
            this.idl.parseIDList();
            Parser.matchConsume(";", "Decl");
        }
        public void printDecl() {
            System.out.print(Parser.indent() + "int ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        public void execDecl() {
            // Left blank for Project 2
        }
    }
