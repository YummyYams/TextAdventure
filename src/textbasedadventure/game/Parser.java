package textbasedadventure.game;

import java.util.Scanner;
public class Parser{
    private Scanner kb;
    public Parser(){
        kb= new Scanner (System.in);
    }
    public Command getCommand(){
        //prompt for user to input text
        System.out.println ("> ");
        
        String inputLine; 
        String word1=null;
        String word2=null; 
        String line = null; 
        
        inputLine=kb.nextLine(); 
        
        Scanner tokenizer = new Scanner (inputLine); 
        if (tokenizer.hasNext()){
            word1=tokenizer.next().toLowerCase();
            if (tokenizer.hasNext()){
                word2=tokenizer.next().toLowerCase();
            }
            if (tokenizer.hasNext()){
                line = tokenizer.nextLine();
            }
        }
        return new Command (word1, word2, line);
    }
}
