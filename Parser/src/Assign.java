

public class Assign{
        private ID id;
        private Expr expr;
        public Assign() {
            this.id = new ID(false);
            this.expr = new Expr();
        }
        public void parseAssign() {
            this.id.parseID();
            Parser.matchConsume("=");
            this.expr.parseExpr();
            Parser.matchConsume(";");
        }
        public void printAssign() {
            System.out.print(Parser.indent());
            this.id.printID();
            System.out.print(" = ");
            this.expr.printExpr();
            System.out.print(";\n");
            
        }
        public void execAssign() {
            // Left blank for Project 2
        }
    }
