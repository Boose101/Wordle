import java.util.Scanner;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Opp {
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m"; 
    public static final String ANSI_BLACK = "\033[0;30m";
    public static final String ANSI_RESET = "\u001B[0m"; 

    public enum Correct{
        Red,
        Orange,
        Green
    }

    private static final boolean debug = true;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        String word = wordPick(rand);
        char [] wordBreakdown = word.toCharArray();

        BoxCharacter[][] wordle = new BoxCharacter [6][5];

        int guesses = 6;

        System.out.println("***Welcome to WORDLE!***");

        System.out.println("You have " + guesses + " guesses to try to find the word. " +
        "Green will indicate that you guessed the letter in the correct place. " +
        "Yellow means the letter exists in the word yet not the place it's curently in. " +
        "Red will indicate that the letter is not in the word"); // * instructions

        if(debug){System.out.println(word);} // * Debug word print

        String[] place = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth"};
        
        for(int gCount = 0; gCount < guesses; gCount++)   {
             // * Adaptive entry

            if(gCount != guesses-1){
                try{
                    System.out.println("Enter your " + place[gCount] + " guess:");
                }
                catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Enter the " + gCount + " guess:");
                }
            }else{
                System.out.println("Enter your final guess:");
            }

            ////////////////////////////////////////////////////////////////////////

            boolean nonstandardEntry = true;
            String guess = "";
            while(nonstandardEntry){
                guess = scan.nextLine();
                if(guess.length() == 5){
                    nonstandardEntry = false;
                }else{
                    System.out.println("Enter a word with 5 characters");
                }
            }
            guess = guess.toLowerCase();
            char[] char_buff = guess.toCharArray();

            for(int count = 0; count < char_buff.length; count++){
                wordle[gCount][count] = new BoxCharacter(char_buff[count], colorFind(char_buff[count], wordBreakdown, count));
            }

            for(int rep = 0; rep <= gCount; rep++) {
                
                
                if(rep == 0){
                    System.out.println(ANSI_BLACK + "***********" + ANSI_RESET);
                } 
                for(int x = 0; x < 5; x++){
                    System.out.print(ANSI_BLACK + "*" + ANSI_RESET + wordle[rep][x].getTerminalColor() + Character.toString(wordle[rep][x].getCharacter()).toUpperCase() + ANSI_RESET);
                }
                System.out.println(ANSI_BLACK + "*" + ANSI_RESET);
                System.out.println(ANSI_BLACK + "***********" + ANSI_RESET);
            }
            
            boolean bool_buff = true;
            for(int check = 0; check < 5; check++){
                if(wordle[gCount][check].getStatus() != Correct.Green){
                    bool_buff = false;
                }
            }
            if(bool_buff){
                System.out.println("You won the game! In " + (gCount + 1) + " times!");
                break;
            }else if(gCount == guesses-1){
                System.out.println("You lost the game! The word is: " + word);
            }
        
        }

        


    }

    private static Correct colorFind(char c, char[] wordBreakdown, int count){
        if(c == wordBreakdown[count]){
            return Correct.Green;
        }else{
            for(char x : wordBreakdown){
                if(x == c){
                    return Correct.Orange;
                }
            }
            return Correct.Red;
        }
        
    }

    private static String wordPick(Random rand){
        try (Scanner reader = new Scanner(new File("words.txt"))) {
            List<String> words = new ArrayList<String>();

            while (reader.hasNext()){
                words.add(reader.nextLine().toLowerCase());
            } 
            return words.get(rand.nextInt(0, words.size())).toLowerCase();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        }
        
    }
}

class BoxCharacter{
    private char character;
    private Opp.Correct status;
    private String term_Color;
    private String[] colors = {"\u001B[31m", "\u001B[33m", "\u001B[32m"}; // * Red, Orange, Green

    public BoxCharacter(){
        character = 'x';
        status = Opp.Correct.Red;
        term_Color = "\u001B[31m";
    }
    
    public BoxCharacter(char uChar, Opp.Correct colorBase){
        character = Character.toLowerCase(uChar);
        status = colorBase;
        termColorSet();
    }

    private void termColorSet(){
        if(status == Opp.Correct.Red){
            term_Color = colors[0];
        }else if(status == Opp.Correct.Orange){
            term_Color = colors[1];
        }else{
            term_Color = colors[2];
        }
    }
    
    public char getCharacter(){
        return character;
    }

    public Opp.Correct getStatus(){
        return status;
    }

    public String getTerminalColor(){
        return term_Color;
    }

}