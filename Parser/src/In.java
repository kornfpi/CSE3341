

public class In{
        private IDList idl;
        public In() {
            this.idl = new IDList(false);
        }
        public void parseIn() {
            Parser.matchConsume("read", "IDList");
            this.idl.parseIDList();
            Parser.matchConsume(";", "IDList");
        }
        public void printIn() {
            System.out.print(Parser.indent() + "read ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        public void execIn() {
            // Left blank for Project 2
        }
    }