
public class Nodes {

    /**
     * The initial starting node of the program (program node)
     */
    private Begin programStart;
    
    public Nodes() {
        String[] tokens = {"Apple", "Orange", "Grape"};
        matchConsume("Orange", tokens);
        this.programStart = new Begin();
        programStart.parseBegin();
    }
    
    private void matchConsume(String matchString, String[] tokens) {
        if(!matchString.equals(tokens[1])) {
            System.out.println("Error! Expected \"" + tokens[1] + "\", But Was \"" + matchString + "\"" );
            System.exit(0);
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
