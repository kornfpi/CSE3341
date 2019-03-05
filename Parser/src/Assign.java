

public class Assign{
        private ID_B id;
        private Expr expr;
        public Assign() {
            this.id = new ID_B(false);
            this.expr = new Expr();
        }
        public void parseAssign() {
            this.id.parseID();
            Global.matchConsume("=");
            this.expr.parseExpr();
            Global.matchConsume(";");
        }
        public void printAssign() {
            System.out.print(Global.indent);
            this.id.printID();
            System.out.print(" = ");
            this.expr.printExpr();
            System.out.print(";\n");
            
        }
        public void execAssign() {
            // Left blank for Project 2
        }
    }
