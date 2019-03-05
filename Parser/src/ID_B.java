    public class ID_B{
        private String identifier;
        private boolean isDecl;
        public ID_B(boolean isDecl) {
            this.isDecl = isDecl;
            this.identifier = null;
        }
        public void parseID() {
            
            if(isDecl) {
            
                if(Global.tokenizer.currentToken().type.equals("IDENT")) {
                    this.identifier = Global.tokenizer.currentToken().symbol;
                    Global.tokenizer.nextToken(); // Consume name
                    if(Global.hasSymbol(this.identifier)) {
                        System.out.println("Error! Multiple declarations of identifier \"" + this.identifier + "\"");
                        System.exit(0);
                    }else {
                        Global.addSymbol(this.identifier);
                    }
                }else {
                    String tokenSymbol = Global.tokenizer.currentToken().symbol;
                    int tokenLine = Global.tokenizer.currentToken().line;
                    System.out.println("Error! (Line " + tokenLine + ") Expected identifier but found \"" + tokenSymbol + "\"");
                    System.exit(0);
                }
            
            }else {
                
                
                String tokenSymbol = Global.tokenizer.currentToken().symbol;
                int tokenLine = Global.tokenizer.currentToken().line;
                if(Global.tokenizer.currentToken().type.equals("IDENT")) {
                    this.identifier = Global.tokenizer.currentToken().symbol;
                    Global.tokenizer.nextToken(); // Consume name
                    if(!Global.hasSymbol(this.identifier)) {
                        System.out.println("Error! (Line " + tokenLine + ") Undeclared identifier \"" + tokenSymbol + "\"");
                        System.exit(0);
                    }
                }else {
                    System.out.println("Error! (Line " + tokenLine + ") Expected identifier but found \"" + tokenSymbol + "\"");
                    System.exit(0);
                }   
                          
                
            }
            
        }
        public void printID() {
            System.out.print(this.identifier);
        }
        public void execID() {
            // Left blank for Project 2
        }
    }
    