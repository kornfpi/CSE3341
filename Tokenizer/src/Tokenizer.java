/**
 * Tokenizer class which takes CORE  language file as input and converts it to proper object file.
 * @author John E Wolford
 * @date 1/20/2019
 */

import java.io.*;
import java.util.*;

public class Tokenizer {
     
    /**
     * Buffered reader for reading lines from input file
     */
    private BufferedReader input;
    
    /**
     * String to denote input file location
     */
    private String inFile;
    
    /**
     * Array list of string array containing tokens parsed by delimeters
     */
    private ArrayList<String[]> lines;
    
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
     * Parse all lines of input file to entries in array list
     * Lines are split into string arrays by whitespace delimeters.
     */
    public void parseToLines() {  
        this.lines = new ArrayList<String[]>();
        String tempLine;
        while((tempLine = getLine()) != null) {
            String[] splitTempLine = tempLine.split("\n\t\r");
            for(int i = 0 ; i < splitTempLine.length ; i++){
                splitTempLine[i] = splitTempLine[i].trim();
            }
            this.lines.add(splitTempLine);
        }
    } 
    
    /**
     * Method to print all lines as currently parsed to the console.
     * For dubugging purposes.
     */
    public void printAllLines(){
        for(int i = 0 ; i < this.lines.size() ; i++){
            String[] tempTokens = this.lines.get(i);
            for(String s : tempTokens) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
    
}
