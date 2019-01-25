
public class Test {

    public static void main(String[] args) {
        
        Tokenizer tokenizer = new Tokenizer(args[0]);
        tokenizer.printAllTokens();
        tokenizer.nextToken();
        Tokenizer.Token newToken = tokenizer.currentToken();
        System.out.print(newToken.symbol);
        
    }

}
