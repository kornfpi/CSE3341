import java.util.*;

public class Parser {

    private static HashMap<String, Integer> symbolTable =new HashMap<String, Integer>();
    private static String indent = "";
    private static Tokenizer tokenizer;
    private static Begin programStart;
    
    public static void main(String[] args) {
        
        if(args.length != 0) {
            tokenizer = new Tokenizer(args[0]);
            parseTokens();
            prettyPrint();
            
        }else { // No argument passed
            System.out.println("[ERROR] No input file specefied in command line!");
            System.exit(0);
        }   
    	
    }
    
    public static Tokenizer.Token currentToken() {
    	return tokenizer.currentToken();
    	
    }
    
    public static void nextToken() {
    	tokenizer.nextToken();
    	
    }
    
    public static void parseTokens() {
    	programStart = new Begin();
    	programStart.parseBegin();
    	
    }
    
    public static void prettyPrint() {
    	
    	programStart.printBegin();
    	
    }
    
    public static boolean hasSymbol(String symbol) {
        return symbolTable.containsKey(symbol);
    }
    
    public static void addSymbol(String symbol) {
        if(!symbolTable.containsKey(symbol)) {
            symbolTable.put(symbol, 0);
        }  
    }
    
    public static void setValue(String symbol, int value) {
        if(symbolTable.containsKey(symbol)) {
            symbolTable.replace(symbol, value);
        }  
    }
    
    public static void matchConsume(String matchString) {
        String tokenSymbol = tokenizer.currentToken().symbol;
        int tokenLine = tokenizer.currentToken().line;
        if(!matchString.equals(tokenSymbol)) {
            System.out.println("Error! (Line " + tokenLine + ") Expected \"" + matchString + "\" but found \"" + tokenSymbol + "\"" );
            System.exit(0);
        }else {
            tokenizer.nextToken();
        }
    }
    
    public static void increaseIndent() {
        indent += "  ";
    }
    
    public static String indent() {
        return indent;
    }
    
    public static void decreaseIndent() {
        if(indent.length() >= 2) {
            indent = indent.substring(0, indent.length() - 2);
        }
    }
    
    public static int stmtType() {
        int type = 0;
        String inString = tokenizer.currentToken().symbol;
        if(Parser.hasSymbol(inString)) type = 1;
        if(inString.equals("if")) type = 2;
        if(inString.equals("while")) type = 3;
        if(inString.equals("read")) type = 4;
        if(inString.equals("write")) type = 5;
        return type;
    }
    
    public static boolean isInt(String inString) {
        boolean isInt = true;
        try {
            Integer.parseInt(inString);
        }catch(NumberFormatException e){
            isInt = false;
        }
        return isInt;
    }
    
}
