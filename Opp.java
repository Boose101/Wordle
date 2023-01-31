import java.util.Scanner;

import Wordle.main;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Opp {
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
                wordle[gCount][count] = new BoxCharacter(c, colorFind(c, wordBreakdown, count));
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
        try (Scanner reader = new Scanner(new File("Wordle/words.txt"))) {
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
        character = "x";
        status = Opp.Correct.Red;
        term_Color = "\u001B[31m";
    }
    
    public BoxCharacter(char uChar, Opp.Correct colorBase){
        character = toLowerCase(uChar);
        status = colorBase;
        termColorSet();
    }

    private void termColorSet(){
        if(colorBase == Opp.Correct.Red){
            term_Color = colors[0];
        }else if(colorBase == Opp.Correct.Orange){
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