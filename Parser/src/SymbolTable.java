import java.util.*;
final class SymbolTable {

    private static HashMap<String, Integer> symbolTable =new HashMap<String, Integer>();
    
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
    
}
