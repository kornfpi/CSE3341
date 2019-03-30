/**
 * Parser Class
 * Contains Main method, must be passed an input file as command line argument 0. 
 * Build around node-implementation of parse tree, requires separate class for each node.. 
 * Holds symbol table, indentation, and tokenizer for access by node classes.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

import java.util.*;

public class Parser {

    /**
     * Map which holds ID string values and maps them to their current integer value.
     */
    private static HashMap<String, Integer> symbolTable =new HashMap<String, Integer>();
    
    /**
     * Indent string for use in node class print methods.
     */
    private static String indent = "";
    
    /**
     * Tokenizer object for parsing input file into tokens
     */
    private static Tokenizer tokenizer;
    
    /**
     * First node of the input program
     */
    private static Prog programStart;
    
    /**
     * Main method, must be passed input file in command line args[0]
     * Will automatically parse and print input file. 
     * @param args[0] the input file to be parsed
     */
    public static void main(String[] args) {
        if(args.length != 0) { // args[0] exists
            // Tokenize, parse, and print
            tokenizer = new Tokenizer(args[0]);
            parseTokens();
            prettyPrint();
        }else { // No argument passed
            System.out.println("[Parser Error!] No input file specefied in command line!");
            System.exit(0);
        }   
    }
    
    /**
     * Method to access private tokenizer currentToken() method.
     * @return token at current tokenizer location
     */
    protected static Tokenizer.Token currentToken() {
    	return tokenizer.currentToken();
    }
    
    /**
     * Method to access private tokenizer nextToken() method.
     */
    protected static void nextToken() {
    	tokenizer.nextToken();
    }
    
    /**
     * This method calls the parseBegin method on the programStart node.
     * This will have the affect of parsing all tokens of a properly tokenized
     * input file into their respective node representation.
     */
    protected static void parseTokens() {
    	programStart = new Prog();
    	programStart.parseProg();
    	// Check that no tokens remain to be parsed
    	if(!tokenizer.currentToken().symbol.equals("EOF")) {
    	    System.out.println("[Parser Error!] Tokens after final end!");
    	    System.exit(0);
    	}
    }
    
    protected static void execProgram() {
    	programStart.execProg();
    }
    
    /**
     * This method calls the printBegin() method on the programStart node.
     * This has the effect of printing the entire parsed input file in
     * a properly formatted way according to project guidelines.
     */
    protected static void prettyPrint() {
    	programStart.printProg();
    }
    
    /**
     * Method checks if input string exists as key in symbolTable.
     * @param symbol the string to check if exists as key.
     * @return true if string is key, false otherwise.
     */
    protected static boolean hasSymbol(String symbol) {
        return symbolTable.containsKey(symbol);
    }
    
    /**
     * Returns value of key in symbol table
     * @param symbol the key to lookup in the symbol table.
     * @return the value of the passed key in the symbol table.
     */
    protected static int getSymbolValue(String symbol) {
        return symbolTable.get(symbol);
    }
    
    /**
     * This method will place a key into the symbol table with the value
     * of zero, if that key is not already added to the symbolTable.
     * @param symbol the key to be added to the symbol table.
     */
    protected static void addSymbol(String symbol) {
        if(!symbolTable.containsKey(symbol)) {
            symbolTable.put(symbol, 999999999);
        }  
    }
    
    /**
     * Method to change the value of a key in the symbol table, if that
     * key exists in the symbol table.
     * @param symbol key which value is to be changed
     * @param value the new value of the key mapping
     */
    protected static void setValue(String symbol, int value) {
        if(symbolTable.containsKey(symbol)) {
            symbolTable.replace(symbol, value);
        }  
    }
    
    /**
     * Method to check an input string against the symbol value of the current token.
     * Will throw informative error and exit program if strings do not match. Will
     * move tokenizer to next token if they do match. 
     * @param matchString the string to match against currentToken().symbol
     * @param method description of calling method for error tracking
     */
    protected static void matchConsume(String matchString, String method) {
        String tokenSymbol = tokenizer.currentToken().symbol;
        int tokenLine = tokenizer.currentToken().line;
        if(!matchString.equals(tokenSymbol)) {
            System.out.println("[Parse " + method + " Error!] (Line " + tokenLine 
                    + ") Expected \"" + matchString + "\" but found \"" + tokenSymbol + "\"" );
            System.exit(0);
        }else {
            tokenizer.nextToken();
        }
    }
    
    /**
     * Increases indent by two spaces
     */
    protected static void increaseIndent() {
        indent += "  ";
    }
    
    /**
     * Returns the current indentation.
     * @return indent
     */
    protected static String indent() {
        return indent;
    }
    
    /**
     * Decreases indent by two spaces if the indent is not less than 
     * two spaces already.
     */
    protected static void decreaseIndent() {
        if(indent.length() >= 2) {
            indent = indent.substring(0, indent.length() - 2);
        }
    }
    
    /**
     * This method enumerates the possible stmt types and checks which
     * matches currentToken().symbol. If none match, returns zero.
     * @return enumerated statement type of currentToken().symbol
     */
    protected static int stmtType() {
        int type = 0;
        String inString = tokenizer.currentToken().symbol;
        if(Parser.hasSymbol(inString)) type = 1;
        if(inString.equals("if")) type = 2;
        if(inString.equals("while")) type = 3;
        if(inString.equals("read")) type = 4;
        if(inString.equals("write")) type = 5;
        return type;
    }
    
    /**
     * Method to check if string can be interpreted as integer value.
     * @param inString the string to be checked
     * @return true if string can be integer, false otherwise
     */
    protected static boolean isInt(String inString) {
        boolean isInt = true;
        try {
            Integer.parseInt(inString);
        }catch(NumberFormatException e){
            isInt = false;
        }
        return isInt;
    }
    
}
