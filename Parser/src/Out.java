

public class Out{
        private IDList_B idl;
        public Out() {
            this.idl = new IDList_B(false);
        }
        public void parseOut() {
            Global.matchConsume("write");
            this.idl.parseIDList();
            Global.matchConsume(";");
        }
        public void printOut() {
            System.out.print(Global.indent + "write ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        public void execOut() {
            // Left blank for Project 2
        }
    }
