
public class Test {

    public static void main(String[] args) {
//        
//        String mix = "abc1d23e556c1d";
//        String[] splitMix = mix.split("[0123456789]+");
//        for(String temp : splitMix) {
//            System.out.println(temp);
//        }
                
        Tokenizer tokenizer = new Tokenizer("src/validWhitespaceBetweenTokens.core");
        tokenizer.printAllTokens();
        
    }

}
