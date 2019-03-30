/**
 * In Class
 * Contains sub-nodes and methods associated with <in> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

import java.util.*;

public class In{
    
    /**
     * Private member for sub-node.
     */
    private IDList idl;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public In() {
        this.idl = new IDList(false);
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseIn() {
        Parser.matchConsume("read", "IDList");
        this.idl.parseIDList();
        Parser.matchConsume(";", "IDList");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printIn() {
        System.out.print(Parser.indent() + "read ");
        this.idl.printIDList();
        System.out.print(";\n");
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execIn() {
        ArrayList<String> variables = this.idl.execIDList();
        Scanner reader = new Scanner(System.in);
        for(String var : variables) {
            System.out.print(var + "=? ");
            String input = reader.nextLine();
            int i = 0;
            boolean correct = false;
            while(!correct) {
                if(isInteger(input)) {
                    i = Integer.parseInt(input);
                    correct = true;
                }else {
                    System.out.print(input + " Is not a valid integer! Try again!\n");
                    System.out.print(var + "=? ");
                    input = reader.nextLine();
                }
            }
            Parser.setValue(var, i);
            System.out.print("\n");
        }
    }
    
    /**
     * Checks if input string can be converted into a proper integer
     * @param str the string gathered from input to be checked
     * @return true if input string is proper java integer, false otherwise
     */
    public static boolean isInteger(String str) {

        boolean isInt = true;
        if (!(str == null)) {
            int length = str.length();
            if (str.isEmpty()) {
                isInt = false;
            }
            int i = 0;
            if (str.charAt(0) == '-') {
                if (length == 1) {
                    isInt = false;
                }
                i = 1;
            }
            for (; i < length; i++) {
                char c = str.charAt(i);
                if (c < '0' || c > '9') {
                    isInt = false;
                }
            }
            if(isInt) {
                isInt = !Parser.isOverflowString(str);
            }
        }
        return isInt;
    }
    
}
