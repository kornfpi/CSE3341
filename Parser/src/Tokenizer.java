/**
 * Tokenizer class which takes CORE language file as input, and converts into properly formatted CORE object code.
 * Follows all instructions as required by PA1 documentation. For CSE3341, SP19, Professor Joshi
 * Instantiation of this class will not produce any output unless the Tokenizer.printParseValues() method 
 * is called upon from the instantiation class. When this class is executed on it's own with a command line argument, 
 * the main method will parse all tokens from args[0] and print thir object code to the screen. Other args are ignored.
 * 
 * @author John E Wolford
 * @date 1/31/2019
 */

import java.io.*;
import java.util.*;

public class Tokenizer {
    
    /**
     * Special characters from CORE grammar, as Array and Set
     */
    public static final Character[] SPEC_CHARS_ARRAY = new Character[]{';',',','=','!',
            '[',']','(',')','+','-','*','>','<'};
    public static final Set<Character> SPEC_CHARS = new HashSet<>(Arrays.asList(SPEC_CHARS_ARRAY));
    
    /**
     * Reserved words from CORE grammar, as Array and Set
     */
    public static final String[] RES_WORDS_ARRAY = new String[]{"program", "begin", "end",
            "int", "if", "then", "else", "while", "loop", "read", "write", "and", "or"};
    public static final Set<String> RES_WORDS = new HashSet<>(Arrays.asList(RES_WORDS_ARRAY));
    
    /**
     * Comparison operators from CORE grammar, as Array and Set
     */
    public static final String[] COMP_OPS_ARRAY = new String[]{"!=","==",">=","<=",};
    public static final Set<String> COMP_OPS = new HashSet<>(Arrays.asList(COMP_OPS_ARRAY));
   
    /**
     * Buffered reader for reading lines from input file
     */
    private BufferedReader input;
    
    /**
     * String to denote input file location
     */
    private String inFile;
    
    /**
     * Total number of lines parsed by the Tokenizer from the input file
     */
    private int lineCount;
    
    /**
     * ArrayList of Token objects that have been parsed from input file
     */
    private ArrayList<Token> tokensParsed;
    
    /**
     * index of current token in tokensParsed, used for returning specific tokens
     */
    private int tokenIndex;
    
    /**
     * Map of CORE characters and data types to their associated integer parse values
     * Populated by call to popConvMap()
     */
    private Map<String, Integer> convMap;
    
    /**
     * Constructor
     * Automatically parses input file, but does not print parsed values.
     * @param inputFile the file to be converted (passed by command line)
     */
    public Tokenizer(String inputFile) {
        this.inFile = inputFile;
        this.lineCount = 0;
        this.tokensParsed = new ArrayList<Token>();
        this.tokenIndex = 0;
        this.convMap = popConvMap();
        openInputFile();  
        parseToTokens();
        closeInputFile();
    }
    
    /** 
     * Main method for running Tokenizer as main instead of as an object.
     * This method will automatically print out parsed values to the console.
     * @param args[0] location of input file to be converted
     */
    public static void main(String[] args) {
        Tokenizer tokenizer;
        if(args.length != 0) {
            tokenizer = new Tokenizer(args[0]);
            tokenizer.printParseValues();
        }else { // No argument passed
            System.out.println("[Tokenizer Error!] No input file specefied in command line!");
            System.exit(0);
        }   
    }
    
    /**
     * This method places all reserved symbols into a map with their corresponding 
     * object code. Note the shifting of the index of the > and < chars to correspond
     * with the addition of operator values which come before them in the project assignment.
     * @return a Map which pairs a valid symbol to it's integer parse value
     */
    private static Map<String, Integer> popConvMap() {
        Map<String, Integer> convMap = new HashMap<String, Integer>();
        int i = 1;
        for(String resWord : RES_WORDS_ARRAY) {
            convMap.put(resWord, i);
            i++;
        }
        for(char specChar : SPEC_CHARS_ARRAY) {
            if(specChar == '>') {
                convMap.put("" + specChar, i + 4);
                i++;
                continue;
            }
            if(specChar == '<') {
                convMap.put("" + specChar, i + 4);
                i++;
                continue;
            }
            convMap.put("" + specChar, i);
            i++;
        }
        for(String op : COMP_OPS_ARRAY) {
            convMap.put(op, i - 2);
            i++;
        }
        return convMap;
    }
    
    /**
     * Private method to open input file and catch IOExceptions
     */
    private void openInputFile() {
        try {   
            this.input = new BufferedReader(new FileReader(this.inFile));  
        }catch (IOException e) {
            System.out.println("[Tokenizer Error!] Invalid input file \"" + this.inFile + "\"!\n" + e);
            System.exit(0);
        } 
    }
    
    /**
     * Public method to close input file and catch IOExceptions
     */
    private void closeInputFile() {  
        try {   
            this.input.close();  
        }catch (IOException e) {
            System.out.println("[Tokenizer Error!] Unable to close input file \"" + this.inFile + "\"!\n" + e);
            System.exit(0);
        }
    } 
    
    /**
     * Private method to get next line of text from input file and handle IOExceptions
     * @returns the next line of the input file
     */
    private String getLine() {  
        String tempLine = null;
        try {   
            tempLine = this.input.readLine();
        }catch (IOException e) {
            System.out.println("[Tokenizer Error!] Unable to read line from input file!\n" + e);
            System.exit(0);
        }
        return tempLine;
    } 

    /**
     * Method which passes complex strings from input to tokenizing methods until
     * all strings from the input have been tokenized. Keeps track of the number of
     * lines parsed from the input.
     */
    public void parseToTokens() {
        String currentLine = null;
        while((currentLine = getLine()) != null) {
            lineCount ++;
            tokenizeLine(currentLine);
        }
        // Add EOF token
        Token newToken = new Token("EOF", this.lineCount + 1, "EOF", 33);
        this.tokensParsed.add(newToken);
    }
    
    /**
     * Method to retrive a token from a given index within this.tokensParsed
     * @return token object at tokenIndex from this.tokensParsed
     */
    public Token currentToken() {
        return this.tokensParsed.get(tokenIndex);
    }
    
    /**
     * increments tokenIndex by 1
     */
    public void nextToken() {
        if(this.tokenIndex < this.tokensParsed.size() - 1) {
            this.tokenIndex ++;
        }
    }
    
    /**
     * Reports the total number of tokens parsed by the tokenizer
     * @return the number of entries in this.tokensParsed
     */
    public int tokenCount() {
        return this.tokensParsed.size();
    }
    
    /**
     * Method to tokenize a complex string into individual tokens
     * @param currentLine the line to be parsed into individual tokens
     */
    private void tokenizeLine(String currentLine) {
        for (int i = 0 ; i < currentLine.length() ; i++) {
            String symbol = null, type = null;
            if(checkWhite(currentLine.charAt(i))) { // Skip symbol: whitespace
                continue;
            }else if (!SPEC_CHARS.contains(currentLine.charAt(i))){ // Symbol is identifier
                checkChar(currentLine.charAt(i));
                int nextBreak = getNextBreak(i, currentLine);
                symbol = currentLine.substring(i, i + nextBreak);
                type = "IDENT";
                i += (nextBreak - 1);
            }else if(isOperator(i, currentLine)) { // Symbol is operator
                symbol = currentLine.substring(i, i + 2);
                type = "COMP";
                i++;
            }else { // Symbol is special char
                symbol = "" + currentLine.charAt(i);
                type = "SPEC";
            }
            Token newToken = new Token(symbol, this.lineCount, type, getParseValue(symbol));
            this.tokensParsed.add(newToken);
        }
    }
    
    /**
     * Method to check if char is in valid range for CORE grammar rules,
     * reports error and exits on detection of invalid char as defined
     * by the ASCII 8bit standard.
     * @param inChar the char to be checked
     */
    private void checkChar(char inChar) { 
        boolean a = (inChar >= 48 && inChar <= 57);
        boolean b = (inChar >= 65 && inChar <= 90);
        boolean c = (inChar >= 97 && inChar <= 122);
        if(!(a||b||c)) {
            System.out.print("[Tokenizer Error!] Invalid character found! '"); 
            System.out.println(inChar + "' Line: " + this.lineCount);
            System.exit(0);
        }   
    }
    
    /**
     * Method to check if character is non-visible/white space char as
     * given by the ASCII 8bit standard.
     * @param inChar the char to be checked
     * @return true if char is invisible/whitespace
     */
    private boolean checkWhite(char inChar) {
        int charInt = inChar;
        return (charInt < 33 || charInt == 127);
    }
    
    /**
     * Method which finds the next non-identifier char of a given string
     * used for pasing identifier tokens from more complex strings
     * @param index current char which is identifier in input string
     * @param currentLine the current line being tokenized
     * @return the number of chars between index and next break 
     */
    private int getNextBreak(int index, String currentLine) {
        int nextBreak = 1, j = index + 1;
        while(isIdentChar(j, currentLine)) {
            nextBreak ++;
            j++;
        }  
        return nextBreak; 
    }
    
    /**
     * Conditional check to determine if char is end of identifier string
     * @param currentIndex index location of char in currentLine
     * @param currentLine the line currently being parsed
     * @return true if char is an identifier char, false otherwise
     */
    private boolean isIdentChar(int currentIndex, String currentLine){
        boolean a = currentIndex < currentLine.length();
        boolean b = a && !SPEC_CHARS.contains(currentLine.charAt(currentIndex));
        boolean c = a && !checkWhite(currentLine.charAt(currentIndex));
        if(b && c) checkChar(currentLine.charAt(currentIndex));
        return b && c;
    }
    
    /**
     * Conditional check to determine if pair of chars is operator type symbol
     * @param currentIndex index location of char in currentLine
     * @param currentLine the line currently being parsed
     * @return true if pair is operator, false otherwise
     */
    private boolean isOperator(int currentIndex, String currentLine){
        boolean a = (currentIndex + 1) < currentLine.length(); 
        return a && COMP_OPS.contains(currentLine.substring(currentIndex, currentIndex + 2));
    }
    
    /**
     * Method to find the object code for a given symbol based upon CORE object code rules.
     * @param inputSymbol the symbol to convert to object code
     * @return the object code of the input symbol
     */
    private int getParseValue(String inputSymbol) {
        int parseValue = 0;
        if(inputSymbol.length() > 8) {
            System.out.print("[Tokenizer Error!] Invalid identifier (length > 8) found! '"); 
            System.out.println(inputSymbol + "' Line: " + this.lineCount);
            System.exit(0);
        }else if(this.convMap.containsKey(inputSymbol)) {
            parseValue = this.convMap.get(inputSymbol);
        }else if(isInt(inputSymbol)){
            parseValue = 31;
        }else if(checkIdent(inputSymbol)){
            parseValue = 32;
        }
        return parseValue;
    }
    
    /**
     * Method to check if string is an integer type based on CORE grammar
     * @param inputSymbol the string symbol to be checked
     * @return true if string is properly formatted integer, false otherwise
     */
    private boolean isInt(String inputSymbol) {
        boolean isInteger = true;
        try { 
            int test = Integer.parseInt(inputSymbol); 
            if(!inputSymbol.equals(Integer.toString(test))) {
                System.out.print("[Tokenizer Error!] Invalid identifier (leading zeros) found! '"); 
                System.out.println(inputSymbol + "' Line: " + this.lineCount);
                System.exit(0);   
            }
        } catch(NumberFormatException e) { 
            isInteger = false; 
        } 
        return isInteger;
    }
    
    /**
     * This method will check that a given string meets all of the
     * CORE grammar rules which define a properly formatted identifier type. 
     * @param inputSymbol string parsed from input to be checked for syntax
     * @return true if string is proper identifier, false otherwise
     * @requires that the inputSymbol has already been determined to not be integer type
     */
    private boolean checkIdent(String inputSymbol) {
        if(!inputSymbol.toUpperCase().equals(inputSymbol)) {
            System.out.print("[Tokenizer Error!] Invalid identifier (lower case chars) found! '"); 
            System.out.println(inputSymbol + "' Line: " + this.lineCount);
            System.exit(0);
        }else if(Character.isDigit(inputSymbol.charAt(0)) ) {
            System.out.print("[Tokenizer Error!] Invalid identifier (leading digits) found! '"); 
            System.out.println(inputSymbol + "' Line: " + this.lineCount);
            System.exit(0);
        }else if(inputSymbol.split("[0123456789]+").length > 1 ) {
            System.out.print("[Tokenizer Error!] Invalid identifier (mixed digits) found! '"); 
            System.out.println(inputSymbol + "' Line: " + this.lineCount);
            System.exit(0);
        }
        return true;
    }
    
    /**
     * Private debugging method which prints all data fields from each token
     * in this.tokensParsed. This method is not used locally except for purposes
     * of testing new Tokenizer functionality.
     */
    private void printAllTokens(){
        for(int i = 0 ; i < this.tokensParsed.size() ; i++){
            Token tempToken = this.tokensParsed.get(i);
            System.out.print("Line: " + (tempToken.line + 1) + " Token: " 
            + tempToken.symbol + "\nType: " + tempToken.type);
            System.out.println(tempToken.parsedValue);
            System.out.println("\n");
        }
    }
    
    /**
     * Method to print all the parse values from each token in this.tokensParsed
     * Makes repeated calls to currentToken() and nextToken() methods, included
     * to satisfy project requirements.
     */
    public void printParseValues(){
        for(int i = 0 ; i < this.tokensParsed.size() ; i++){
            System.out.println(currentToken().parsedValue);
            nextToken();
        }
    }
    
    /**
     * Public class to define public members which define a token type object.
     * All data members are public access and have no get/set methods. All
     * fields must be defined on instantiation, there is no no-argument constructor.
     */
    public class Token{
        
        /**
         * Original string which defines the token
         */
        public String symbol;
        
        /**
         * Line of input file from which token was parsed
         */
        public int line;
        
        /**
         * Stores the type of token
         */
        public String type;
        
        /**
         * Integer value of parsed token
         */
        public int parsedValue;
        
        /**
         * Public constructor class for creating Token object
         * @param symbolIn the string value of the token as parsed from input
         * @param lineIn the line from the input source corresponding to the token
         * @param typeIn general type of the token associated with symbol
         * @param parseValue the CORE object code corresponding to the token
         */
        public Token(String symbolIn, int lineIn, String typeIn, int parseValue) {
            this.symbol = symbolIn;
            this.line = lineIn;
            this.type = typeIn;
            this.parsedValue = parseValue;
        }
        
    }
    
}
