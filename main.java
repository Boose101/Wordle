package Wordle;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class main {

    
    public static final String ANSI_RED = "\u001B[31m"; //1
    public static final String ANSI_YELLOW = "\u001B[33m"; //2
    public static final String ANSI_GREEN = "\u001B[32m"; //3
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m"; 
    public static final String ANSI_BLACK = "\033[0;30m";
    public static final String ANSI_RESET = "\u001B[0m"; 

    public static final String ANSI_COLORS[] = {"\u001B[31m", "\u001B[33m", "\u001B[32m"};

    public enum Correct{
        Red,
        Orange,
        Green
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        String word = wordPick(rand);

        char[][] wordleList = new char[6][5];
        Correct[][] wordleColor = new Correct[6][5];
        char[] wordBreakdown = word.toCharArray();

        

        int guesses = 6;

        System.out.println("***Welcome to WORDLE!***");

        System.out.println("You have " + guesses + " guesses to try to find the word. " +
        "Green will indicate that you guessed the letter in the correct place. " +
        "Yellow means the letter exists in the word yet not the place it's curently in. " +
        "Red will indicate that the letter is not in the word");

        System.out.println(word);
        String[] place = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth", "tenth"};
        for(int gCount = 0; gCount < guesses; gCount++)   {
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

            int count = 0;
            for(char char_Iteration : char_buff){
                wordleList[gCount][count] = char_Iteration;
                wordleColor[gCount][count] = cor_Check(char_Iteration, count, wordBreakdown);
                count++;
            }

            for(int rep = 0; rep <= gCount; rep++) {
                
                
                if(rep == 0){
                    System.out.println(ANSI_BLACK + "***********" + ANSI_RESET);
                } 
                for(int x = 0; x < 5; x++){
                    System.out.print(ANSI_BLACK + "*" + ANSI_RESET + ANSI_COLORS[col_find(rep, x, wordleColor)] + Character.toString(wordleList[rep][x]).toUpperCase() + ANSI_RESET);
                }
                System.out.println(ANSI_BLACK + "*" + ANSI_RESET);
                System.out.println(ANSI_BLACK + "***********" + ANSI_RESET);
            }
            boolean bool_buff = true;
            for(Correct x : wordleColor[gCount]){
                if(x != Correct.Green){
                    bool_buff = false;
                }
            }
            if(bool_buff){
                System.out.println("You won the game! In " + gCount + 1 + " times!");
                break;
            }else if(gCount == guesses-1){
                System.out.println("You lost the game! The word is: " + word);
            }
        }
        scan.close();
        




    }

    private static int col_find(int gCount, int count, Correct[][] wordleColor){
        if(wordleColor[gCount][count] == Correct.Red){
            return 0;
        }else if(wordleColor[gCount][count] == Correct.Orange){
            return 1;
        }else{
            return 2;
        }
    }

    private static Correct cor_Check(char character, int count, char[] wordBreakdown){
        if(character == wordBreakdown[count]){
            return Correct.Green;
        }else{
            for(char char_Iteration : wordBreakdown){
                if(character == char_Iteration){
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


