
public class Nodes {

    private Begin programStart;
    
    public Nodes() {
        this.programStart = new Begin();
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
