

public class Fac{
        private int alt;
        private int intValue;
        private ID_B id;
        private Expr expr;
        public Fac() {
        }
        public void parseFac() {
            String type = Global.tokenizer.currentToken().type;
            String tokenSymbol = Global.tokenizer.currentToken().symbol;
            int tokenLine = Global.tokenizer.currentToken().line;
            switch (type) {
                case("IDENT"):
                    if(Global.isInt(tokenSymbol)) {
                        this.alt = 1;
                        this.intValue = Integer.parseInt(tokenSymbol);
                        Global.tokenizer.nextToken();
                    }else {
                        this.alt = 2;
                        this.id = new ID_B(false);
                        this.id.parseID();
                    }
                    break;
                case("SPEC"):
                    this.alt = 3;
                    Global.matchConsume("(");
                    this.expr = new Expr();
                    this.expr.parseExpr();
                    Global.matchConsume(")");
                    break;
                default:
                    System.out.println("Error! Factor (Line " + tokenLine + ") Expected identifier, integer, or \"(\", but found \"" + tokenSymbol + "\"");
                    System.exit(0);
            }   
        }
        public void printFac() {
            switch (this.alt) {
                case(1):
                    System.out.print(this.intValue);
                    break;
                case(2):
                    this.id.printID();
                    break;
                case(3):
                    System.out.print("( ");
                    this.expr.printExpr();
                    System.out.print(" )");
                    break;
            }
        }
        public void execFac() {
            // Left blank for Project 2
        }
    }
    