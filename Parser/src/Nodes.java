
public class Nodes {

    /**
     * The initial starting node of the program (program node)
     */
    private Tokenizer tokenizer;
    private Begin programStart;
    
    public Nodes(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
        this.programStart = new Begin();
        matchConsume("program");
        programStart.parseBegin();
    }
    
    private void matchConsume(String matchString) {
        String tokenSymbol = this.tokenizer.currentToken().symbol;
        int tokenLine = this.tokenizer.currentToken().line;
        if(!matchString.equals(tokenSymbol)) {
            System.out.println("Error! (Line " + tokenLine + ") Expected \"" + matchString + "\" but found \"" + tokenSymbol + "\"" );
            System.exit(0);
        }else {
            this.tokenizer.nextToken();
        }
    }
    
    private class Begin{
        private Apple appleNode;
        private Banana bananaNode;
        public Begin() {      
            this.appleNode = new Apple();
            this.bananaNode = new Banana();
        }
        public void parseBegin() {
            
        }
        public void printBegin() {
            
        }
        public void execBegin() {
            
        }
    }
    
    private class Apple{
        public String name;
        public Apple() {      
            this.name = "Apple";  
        }
    }
    
    private class Banana{
        public String name;
        public Banana() {      
            this.name = "Banana";  
        }
    }
    
}
