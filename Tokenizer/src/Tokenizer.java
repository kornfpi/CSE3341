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
     * ArrayList of ArrayList of String array containing parsed tokens indexed by lines
     */
    private ArrayList<ArrayList<String>> lines;
    
    /**
     * Constructor
     * @param inputFile the file to be converted passed by command line
     */
    public Tokenizer(String inputFile) {
        // Set private members and ensure input is valid
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
            tokenizer.parseToLines();
            tokenizer.printAllLines();
            
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
     * Method to break a single string into individual tokens based on specified strings, chars, and delimiters
     */
    public void parseToLines() {
        String currentLine = null;
        int lineCount = 0;
        this.lines = new ArrayList<ArrayList<String>>();
        while((currentLine = getLine()) != null) {
            ArrayList<String> lineTokens = new ArrayList<String>();
            for (int i = 0 ; i < currentLine.length() ; i++) {
                if(DELIMITERS.contains(currentLine.charAt(i))) { // Skip symbol: whitespace
                    continue;
                }else if (!SPEC_CHARS.contains(currentLine.charAt(i))){ // Symbol is identifier
                    int nextBreak = 1, j = i + 1;
                    while(j < currentLine.length() && !SPEC_CHARS.contains(currentLine.charAt(j)) 
                            && !DELIMITERS.contains(currentLine.charAt(j))) {
                        nextBreak ++;
                        j++;
                    }
                    lineTokens.add(currentLine.substring(i, i + nextBreak));
                    i += (nextBreak - 1);
                }else if((i + 1) < currentLine.length() 
                        && SPEC_OPS.contains(currentLine.substring(i, i + 2))){ // Symbol is operator
                    lineTokens.add(currentLine.substring(i, i + 2));
                    i++;
                }else { // Symbol is special char
                    lineTokens.add("" + currentLine.charAt(i)); 
                }
            }
            this.lines.add(lineCount, lineTokens);
            lineCount ++;
        }
    }
    
    /**
     * Method to print all lines as currently parsed to the console.
     * For dubugging purposes.
     */
    public void printAllLines(){
        for(int i = 0 ; i < this.lines.size() ; i++){
            ArrayList<String> tempTokens = this.lines.get(i);
            System.out.print("Line " + (i + 1) + " Tokens: ");
            for(String s : tempTokens) {
                System.out.print("(" + s + ") ");
            }
            System.out.println();
        }
    }
    
}
