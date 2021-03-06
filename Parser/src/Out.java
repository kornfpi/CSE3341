import java.util.ArrayList;
import java.util.Scanner;

/**
 * Out Class
 * Contains sub-nodes and methods associated with <out> node of the CORE grammar.
 * 
 * @author John E. Wolford
 * @date 3-6-2019 
 * 
 */

public class Out{
    
    /**
     * Private member for sub-node.
     */
    private IDList idl;
    
    /**
     * No argument constructor. Creates private member objects.
     */
    public Out() {
        this.idl = new IDList(false);
    }
    
    /**
     * Method to parse relevant tokens and symbols 
     */
    public void parseOut() {
        Interpreter.matchConsume("write", "Out");
        this.idl.parseIDList();
        Interpreter.matchConsume(";", "Out");
    }
    
    /**
     * Method to print relevant tokens and symbols 
     */
    public void printOut() {
        System.out.print(Interpreter.indent() + "write ");
        this.idl.printIDList();
        System.out.print(";\n");
    }
    
    /**
     * Method to execute node based on parsed values
     */
    public void execOut() {
        ArrayList<String> variables = this.idl.execIDList();
        for(String var : variables) {
            System.out.println(var + " = " + Interpreter.getSymbolValue(var));
        }
    }
    
}
