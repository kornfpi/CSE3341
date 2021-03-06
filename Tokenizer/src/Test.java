import java.io.*;
import org.junit.Test.*;
public class Test {

    public static void main(String[] args) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/testOutput.txt"));
            Tokenizer tokenizer = new Tokenizer("src/validWhitespaceBetweenTokens.core");
            for(int i = 0 ; i < tokenizer.tokenCount(); i++) {
                Tokenizer.Token temp = tokenizer.currentToken();
                try {
                    writer.write(temp.parsedValue);
                }catch(IOException e) {
                    System.exit(0);
                }
                tokenizer.nextToken();
            }
        }catch(IOException e){
            System.exit(0);
        }
        
    }

}
