import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class wordle {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    static String getRandomWord() {
        Path filePath = Paths.get("words.txt");
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            Random rand = new Random();
            int randomNum = rand.nextInt(lines.size());
            return lines.get(randomNum);
        } catch (IOException e) {
            System.out.println("Error reading file");
            return null;
        } catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }
    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else if (os.contains("Linux"))
            {
                System.out.print("\033\143");
            }
            else
            {
                System.out.print("\033\143");
            }
        }
        catch (final Exception e) {
            System.out.println("Failed to clear console. Probably your OS is not supported.");
        }
    }
    static Boolean isWordinDictionary(String word) {
        Path filePath = Paths.get("words.txt");
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(filePath, charset);
            for (String line : lines) {
                if (line.equals(word)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            System.out.println("Error reading file while checking dictionary for word");
            return false;
        } catch (Exception e) {
            System.out.println("Error while checking dictionary for word");
            return false;
        }
    }

    
    public static void main(String[] args) {
        clearConsole();
        System.out.println("\nWelcome to "+ANSI_BLUE+"W"+ANSI_GREEN+"O"+ANSI_CYAN+"R"+ANSI_RED+"D"+ANSI_YELLOW+"L"+ANSI_BLUE+"E"+ANSI_RESET+"!");
        System.out.println("-------------------");
        System.out.println("Coded by Gautam Mehta (21dcs053)\n\n");
        System.out.println(ANSI_PURPLE+"Rules:"+ANSI_RESET);
        System.out.println(ANSI_BLUE+"1. "+ANSI_RESET+"Guess the WORDLE in 6 tries.\n");
        System.out.println(ANSI_BLUE+"2. "+ANSI_RESET+"Each guess must be a valid 5-letter word. Hit the enter button to submit.\n");
        System.out.println(ANSI_BLUE+"3. "+ANSI_RESET+"After each guess, the color of the letters will change to show how close your guess was to the word, in the following way:");
        System.out.println(ANSI_GREEN+"   - Green:"+ANSI_RESET+" The letter is in the word and in the correct spot.");
        System.out.println(ANSI_YELLOW+"   - Yellow:"+ANSI_RESET+" The letter is in the word but in the wrong spot.");
        System.out.println(ANSI_RED+"   - Red:"+ANSI_RESET+" The letter is not in the word in any spot.\n\n");
        System.out.println(ANSI_GREEN+"Good luck!"+ANSI_RESET+"\n\n");
        System.out.println("Press enter to start.");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println("Error");
        }
        clearConsole();
        while (true) {
            System.out.println("");
            String word = getRandomWord();
            String guessState = ANSI_WHITE + "? ? ? ? ?" + ANSI_RESET;
            int tries = 0;
            while (tries < 6) {
                System.out.println(ANSI_BLUE+"Wordle: "+guessState);
                System.out.println(ANSI_CYAN+"Guesses left: "+(6-tries)+ANSI_RESET);
                System.out.print("Enter your Guess: "+ANSI_PURPLE);
                String guess = System.console().readLine();
                System.out.println(ANSI_RESET);
                if (guess.length() != 5) {
                    System.out.println("Invalid guess. Try again.");
                    continue;
                }
                if (!isWordinDictionary(guess)) {
                    System.out.println("Word not in dictionary. Try again.");
                    continue;
                }
                String newGuessState = "";
                for (int i = 0; i < 5; i++) {
                    char guessChar = guess.charAt(i);
                    char wordChar = word.charAt(i);
                    if (guessChar == wordChar) {
                        newGuessState += ANSI_GREEN + guessChar + ANSI_RESET;
                    } else if (word.indexOf(guessChar) != -1) {
                        newGuessState += ANSI_YELLOW + guessChar + ANSI_RESET;
                    } else {
                        newGuessState += ANSI_RED + guessChar + ANSI_RESET;
                    }
                    newGuessState += " ";
                }
                guessState = newGuessState;
                System.out.println("-------------------");
                if (guess.equals(word)) {
                    System.out.println(guessState);
                    System.out.println(ANSI_GREEN+"You win!"+ANSI_RESET);
                    break;
                }
                tries++;
            }
            if (tries == 6) {
                System.out.println(ANSI_RED+"You lose! The word was "+ ANSI_RESET + word);
            }
            System.out.print("Play again? (y/n): ");
            String playAgain = System.console().readLine();
            if (playAgain.equals("n")) {
                System.out.println("Thanks for playing!");
                break;
            }
            else if (playAgain.equals("y")) {
                clearConsole();
                continue;
            }
            else {
                System.out.println("Invalid input. Exiting.");
                break;
            }
        }
    }
}