    public class ID{
        private String identifier;
        private boolean isDecl;
        public ID(boolean isDecl) {
            this.isDecl = isDecl;
            this.identifier = null;
        }
        public void parseID() {
            
            if(isDecl) {
            
                if(Parser.currentToken().type.equals("IDENT")) {
                    this.identifier = Parser.currentToken().symbol;
                    Parser.nextToken(); // Consume name
                    if(Parser.hasSymbol(this.identifier)) {
                        System.out.println("Error! Multiple declarations of identifier \"" + this.identifier + "\"");
                        System.exit(0);
                    }else {
                        Parser.addSymbol(this.identifier);
                    }
                }else {
                    String tokenSymbol = Parser.currentToken().symbol;
                    int tokenLine = Parser.currentToken().line;
                    System.out.println("Error! (Line " + tokenLine + ") Expected identifier but found \"" + tokenSymbol + "\"");
                    System.exit(0);
                }
            
            }else {
                
                
                String tokenSymbol = Parser.currentToken().symbol;
                int tokenLine = Parser.currentToken().line;
                if(Parser.currentToken().type.equals("IDENT")) {
                    this.identifier = Parser.currentToken().symbol;
                    Parser.nextToken(); // Consume name
                    if(!Parser.hasSymbol(this.identifier)) {
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
    