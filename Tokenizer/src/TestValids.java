import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class TestValids {

    @Test
    void test() {
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
