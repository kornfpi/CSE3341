

public class In{
        private IDList_B idl;
        public In() {
            this.idl = new IDList_B(false);
        }
        public void parseIn() {
            Global.matchConsume("read");
            this.idl.parseIDList();
            Global.matchConsume(";");
        }
        public void printIn() {
            System.out.print(Global.indent + "read ");
            this.idl.printIDList();
            System.out.print(";\n");
        }
        public void execIn() {
            // Left blank for Project 2
        }
    }
