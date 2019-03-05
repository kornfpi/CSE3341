
public class Cond{
        private int alt;
        private Comp comp;
        private Cond cond1;
        private Cond cond2;
        public Cond() {
        }
        public void parseCond() {
            if(Parser.currentToken().symbol.equals("!")) {
                Parser.nextToken();
                this.alt = 2;
                this.cond1 = new Cond();
                this.cond1.parseCond();
            }else if(Parser.currentToken().symbol.equals("[")) {
                Parser.nextToken();
                this.cond1 = new Cond();
                this.cond1.parseCond();
                if(Parser.currentToken().symbol.equals("and")) {
                    Parser.nextToken();
                    this.alt = 3;
                    this.cond2 = new Cond();
                    this.cond2.parseCond();
                    Parser.matchConsume("]");
                }else if(Parser.currentToken().symbol.equals("or")) {
                    Parser.nextToken();
                    this.alt = 4;
                    this.cond2 = new Cond();
                    this.cond2.parseCond();
                    Parser.matchConsume("]");
                }else {
                    System.out.print("ERROR");
                    System.exit(0);
                }
            }else {
                this.alt = 1;
                this.comp = new Comp();
                this.comp.parseComp();
            }  
        }
        public void printCond() {
            switch(this.alt) {
                case(1):
                    this.comp.printComp();
                    break;
                case(2):
                    System.out.print("!");
                    this.cond1.printCond();
                    break;
                case(3):
                    System.out.print("[ ");
                    this.cond1.printCond();
                    System.out.print(" and ");
                    this.cond2.printCond();
                    System.out.print(" ]");
                    break;
                case(4):
                    System.out.print("[ ");
                    this.cond1.printCond();
                    System.out.print(" or ");
                    this.cond2.printCond();
                    System.out.print(" ]");
                    break;
            }

        }
        public void execCond() {
            // Left blank for Project 2
        }
    }
    
