
public class Test {
    
    public static void main(String[] args) {
        Tokenizer tokenizer;
        if(args.length != 0) {
            tokenizer = new Tokenizer(args[0]);
            //tokenizer.printParseValues();
            Nodes parser = new Nodes(tokenizer);
            parser.prettyPrint();
        }else { // No argument passed
            System.out.println("[ERROR] No input file specefied in command line!");
            System.exit(0);
        }   
    }

}
