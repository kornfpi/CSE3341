/**
 * Tokenizer class which takes CORE  language file as input and converts it to proper object file.
 * @author John E Wolford
 * @date 1/23/2019
 */

import java.io.*;
import java.util.*;

public class Tokenizer {
    
    /**
     * Special characters, delimiters, and special character arrangements stored as a set
     */
    public static final Character[] SPEC_CHARS_ARRAY = new Character[]{';',',','=','!',
            '[',']','(',')','+','-','*','<','>'};
    public static final String[] RES_WORDS_ARRAY = new String[]{"program", "begin", "end",
            "int", "if", "then", "else", "while", "loop", "read", "write", "and", "or"};
    public static final String[] SPEC_OPS_ARRAY = new String[]{"!=","==",">=","<=",""};
    public static final Set<Character> SPEC_CHARS = new HashSet<>(Arrays.asList(SPEC_CHARS_ARRAY));
    public static final Set<String> SPEC_OPS = new HashSet<>(Arrays.asList(SPEC_OPS_ARRAY));
    public static final Set<String> RES_WORDS = new HashSet<>(Arrays.asList(RES_WORDS_ARRAY));
   
    /**
     * Buffered reader for reading lines from input file
     */
    private BufferedReader input;
    
    /**
     * String to denote input file location
     */
    private String inFile;
    
    /**
     * Amount of lines parsed by tokenizer
     */
    private int lineCount;
    
    /**
     * ArrayList of Token objects that have been parsed from input file
     */
    private ArrayList<Token> tokensParsed;
    
    /**
     * index of current token in tokensParsed
     */
    private int tokenIndex;
    
    /**
     * Constructor
     * @param inputFile the file to be converted passed by command line
     */
    public Tokenizer(String inputFile) {
        this.inFile = inputFile;
        this.lineCount = 0;
        this.tokensParsed = new ArrayList<Token>();
        this.tokenIndex = 0;
        openInputFile();  
        parseToTokens();
        closeInputFile();
        checkTokens();
    }
    
    /** 
     * Main method
     * @param args[0] location of input file to be converted
     */
    public static void main(String[] args) {
        Tokenizer tokenizer;
        if(args.length != 0) {  // Argument passed
            // Begin main tokenizing loop
            tokenizer = new Tokenizer(args[0]);
            tokenizer.printAllTokens();
        }else { // No argument passed
            System.out.println("[ERROR] No input file specefied in command line!");
            System.exit(0);
        }   
    }
    
    /**
     * Private method to open input file and catch IO exceptions
     */
    private void openInputFile() {
        try {   
            this.input = new BufferedReader(new FileReader(this.inFile));  
        }catch (IOException e) {
            System.out.println("[ERROR] Invalid input file \"" + this.inFile + "\"!\n" + e);
            System.exit(0);
        } 
    }
    
    /**
     * Public method to close input file and catch IO exceptions
     */
    private void closeInputFile() {  
        try {   
            this.input.close();  
        }catch (IOException e) {
            System.out.println("[ERROR] Unable to close input file \"" + this.inFile + "\"!\n" + e);
            System.exit(0);
        }
    } 
    
    /**
     * Private method to get next line of text from input file
     * @returns the next line of the input file
     */
    private String getLine() {  
        String tempLine = null;
        try {   
            tempLine = this.input.readLine();
        }catch (IOException e) {
            System.out.println("[ERROR] Unable to read line from input file!\n" + e);
            System.exit(0);
        }
        return tempLine;
    } 

    /**
     * Method to tokenize strings based on specified strings, chars, and delimiters
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
     * Returns token object at tokenIndex from tokensParsed
     */
    public Token currentToken() {
        return this.tokensParsed.get(tokenIndex);
    }
    
    /**
     * increments tokenIndex
     */
    public void nextToken() {
        this.tokenIndex ++;
    }
    
    /**
     * Method to tokenize individual string into token objects
     * @param currentLine the line to be parsed into tokens
     */
    private void tokenizeLine(String currentLine) {
        for (int i = 0 ; i < currentLine.length() ; i++) {
            String symbol = null, type = null;
            checkChar(currentLine.charAt(i));
            if(checkWhite(currentLine.charAt(i))) { // Skip symbol: whitespace
                continue;
            }else if (!SPEC_CHARS.contains(currentLine.charAt(i))){ // Symbol is identifier
                int nextBreak = getNextBreak(i, currentLine);
                symbol = currentLine.substring(i, i + nextBreak);
                type = "Identifier [unchecked]";
                i += (nextBreak - 1);
            }else if(isOperator(i, currentLine)) { // Symbol is operator
                symbol = currentLine.substring(i, i + 2);
                type = "Special Operator";
                i++;
            }else { // Symbol is special char
                symbol = "" + currentLine.charAt(i);
                type = "Special Character";
            }
            Token newToken = new Token(symbol, this.lineCount, type, getParseValue(symbol));
            this.tokensParsed.add(newToken);
        }
    }
    
    private int getParseValue(String inputSymbol) {
        return 0;
    }
    
    /**
     * Method to check if char is in ASCII 8-Bit set. 
     * reports error and exits on detection of non-ASCII char.
     * @param inChar the char to be checked
     */
    private void checkChar(char inChar) { 
        if(((int)(inChar) > 127)) { // Non-ASCII char found
            System.out.print("[ERROR] Non-ASCII Character Found! '"); 
            System.out.println(inChar + "' Line: " + this.lineCount);
            System.exit(0);
        }   
    }
    
    /**
     * Method to check if character is non-visible/white space char
     * @param inChar the char to be checked
     * @return true if char is invisible/whitespace
     */
    private boolean checkWhite(char inChar) {
        int charInt = inChar;
        return (charInt < 33 || charInt == 127);
    }
    
    /**
     * Method which finds the next non-identifier char of a given string.
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
     */
    private boolean isIdentChar(int currentIndex, String currentLine){
        boolean a = currentIndex < currentLine.length();
        if(a) checkChar(currentLine.charAt(currentIndex));
        boolean b = a && !SPEC_CHARS.contains(currentLine.charAt(currentIndex));
        boolean c = a && !checkWhite(currentLine.charAt(currentIndex));
        return b && c;
    }
    
    /**
     * Conditional check to determine if pair of chars is operator
     * @param currentIndex index location of char in currentLine
     * @param currentLine the line currently being parsed
     */
    private boolean isOperator(int currentIndex, String currentLine){
        boolean a = (currentIndex + 1) < currentLine.length(); 
        return a && SPEC_OPS.contains(currentLine.substring(currentIndex, currentIndex + 2));
    }
    
    /**
     * Check the syntax of all tokens
     */
    private void checkTokens() {
        while(this.tokenIndex < this.tokensParsed.size()) {
            if(currentToken().type.equals("Identifier [unchecked]")){
                currentToken().type = "CHECKED";
            }
            nextToken();
        }
        this.tokenIndex = 0; // Reset index
    }
      
    /**
     * Method to print all lines as currently parsed to the console.
     * For dubugging purposes.
     */
    public void printAllTokens(){
        for(int i = 0 ; i < this.tokensParsed.size() ; i++){
            Token tempToken = this.tokensParsed.get(i);
            System.out.print("Line: " + (tempToken.line + 1) + " Token: " 
            + tempToken.symbol + "\nType: " + tempToken.type);
            System.out.println("\n");
        }
    }
    
    /**
     * Public class to hold information relevant to each token
     */
    public class Token{
        
        /**
         * Stores the token as parsed from input
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
         * Integer value of parsed token type
         */
        public int parse;
        
        /**
         * Constructor
         * 
         * @param symboIn symbol to be stored in this token
         * @param lineIn line number of this token
         * @param infoIn info about this token
         */
        public Token(String symbolIn, int lineIn, String infoIn, int parseValue) {
            this.symbol = symbolIn;
            this.line = lineIn;
            this.type = infoIn;
            this.parse = parseValue;
        }
        
    }
    
}
