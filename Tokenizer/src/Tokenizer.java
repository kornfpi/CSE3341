/**
 * 
 * Tokenizer class which takes CORE  language file as input and converts it to proper object file.
 * @author John E Wolford
 * @date 1/20/2019
 */

import java.io.*;

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
        if(!args[0].equals(null)) {  // Argument passed
            // Begin main tokenizing loop
            tokenizer = new Tokenizer(args[0]);
            
            
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
        // Try to open input file, display error and exit on exception
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
        // Try to open input file, display error and exit on exception
        try {   
            this.input.close();  
        }catch (IOException e) {
            System.out.println("[ERROR] Unable to close input file \"" + this.inFile + "\"!\n" + e);
            System.exit(0);
        }
    } 
    
}
