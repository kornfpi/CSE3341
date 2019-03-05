

public class Out{
        private IDList idl;
        public Out() {
            this.idl = new IDList(false);
        }
        public void parseOut() {
            Parser.matchConsume("write", "Out");
            this.idl.parseIDList();
            Parser.matchConsume(";", "Out");
        }
        public void printOut() {
            System.out.print(Parser.indent() + "write ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        public void execOut() {
            // Left blank for Project 2
        }
    }
