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
    public static final Character[] SPEC_CHARS_ARRAY = new Character[]{';',',','=','!','[',']','(',')','+','-','*','<','>'};
    public static final Set<Character> SPEC_CHARS = new HashSet<>(Arrays.asList(SPEC_CHARS_ARRAY));
    public static final String[] SPEC_OPS_ARRAY = new String[]{"!=","==",">=","<=",""};
    public static final Set<String> SPEC_OPS = new HashSet<>(Arrays.asList(SPEC_OPS_ARRAY));
    public static final Character[] DELIMITERS_ARRAY = new Character[]{' ','\n','\r','\t'};
    public static final Set<Character> DELIMITERS = new HashSet<>(Arrays.asList(DELIMITERS_ARRAY));
     
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
     * Constructor
     * @param inputFile the file to be converted passed by command line
     */
    public Tokenizer(String inputFile) {
        this.inFile = inputFile;
        openInputFile();  
    }
    
    /** 
     * Main method
     * @param args[0] location of input file to be converted
     */
    public static void main(String[] args) {
        // Create tokenizer object
        Tokenizer tokenizer;
        if(args.length != 0) {  // Argument passed
            
            // Begin main tokenizing loop
            tokenizer = new Tokenizer(args[0]);
            tokenizer.parseToTokens();
            tokenizer.printAllTokens();
            
            // Close input file
            tokenizer.closeInputFile();
            
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
    public void closeInputFile() {  
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
        this.lineCount = 0;
        this.tokensParsed = new ArrayList<Token>();
        while((currentLine = getLine()) != null) {
            tokenizeLine(currentLine);
            lineCount ++;
        }
    }
    
    /**
     * Method to tokenize individual string into token objects
     * @param currentLine the line to be parsed into tokens
     */
    private void tokenizeLine(String currentLine) {
        for (int i = 0 ; i < currentLine.length() ; i++) {
            if(DELIMITERS.contains(currentLine.charAt(i))) { // Skip symbol: whitespace
                continue;
            }else if (!SPEC_CHARS.contains(currentLine.charAt(i))){ // Symbol is identifier
                int nextBreak = 1, j = i + 1;
                while(isIdentChar(j, currentLine)) {
                    nextBreak ++;
                    j++;
                }
                String identifier = currentLine.substring(i, i + nextBreak);
                Token newToken = new Token(identifier, lineCount, "Identifier [unchecked]");
                this.tokensParsed.add(newToken);
                i += (nextBreak - 1);
            }else if(isOperator(i, currentLine)) { // Symbol is operator
                String operator = currentLine.substring(i, i + 2);
                Token newToken = new Token(operator, lineCount, "Special Operator");
                this.tokensParsed.add(newToken);
                i++;
            }else { // Symbol is special char
                String specChar = "" + currentLine.charAt(i);
                Token newToken = new Token(specChar, lineCount, "Special Character");
                this.tokensParsed.add(newToken);
            }
        }
    }
    
    /**
     * Conditional check to determine if char is end of identifier string
     * @param currentIndex index location of char in currentLine
     * @param currentLine the line currently being parsed
     */
    private boolean isIdentChar(int currentIndex, String currentLine){
        boolean a = currentIndex < currentLine.length();
        boolean b = a && !SPEC_CHARS.contains(currentLine.charAt(currentIndex));
        boolean c = a && !DELIMITERS.contains(currentLine.charAt(currentIndex));
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
     * Method to print all lines as currently parsed to the console.
     * For dubugging purposes.
     */
    public void printAllTokens(){
        for(int i = 0 ; i < this.tokensParsed.size() ; i++){
            Token tempToken = this.tokensParsed.get(i);
            System.out.print("Line: " + (tempToken.line + 1) + " Token: " 
            + tempToken.symbol + " Info: " + tempToken.info);
            System.out.println();
        }
    }
    
    /**
     * Private class to hold information relevant to each token
     */
    private class Token{
        
        /**
         * Stores the token as parsed
         */
        public String symbol;
        
        /**
         * Stores the line of code which token was parsed from
         */
        public int line;
        
        /**
         * Stores any information about the token
         */
        public String info;
        
        /**
         * Constructor
         * 
         * @param symboIn symbol to be stored in this token
         * @param lineIn line number of this token
         * @param infoIn info about this token
         */
        public Token(String symbolIn, int lineIn, String infoIn) {
            this.symbol = symbolIn;
            this.line = lineIn;
            this.info = infoIn;
        }
        
    }
    
}
