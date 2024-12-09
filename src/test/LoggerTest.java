import org.junit.Test;

import java.io.File;
import java.util.Scanner;

public class LoggerTest {
    //Tests the logging of a player's final hand
    @Test
    public void logFinalTest() throws Exception {
        Deck drawDeck = new Deck(1);
        Deck discardDeck = new Deck(2);
        MockPlayer player = new MockPlayer(drawDeck, discardDeck, 100);
        player.setHand();
        Logger.logFinal(player, player.readHand());
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player100_output.txt");
        Scanner scanner = new Scanner(log);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.equals("player 100 final hand: 1 2 3 4")) {
                return;
            }
        }
    }

    //Tests the logging of a player exiting the game
    @Test
    public void logExitTest() throws Exception {
        Deck drawDeck = new Deck(1);
        Deck discardDeck = new Deck(2);
        MockPlayer player = new MockPlayer(drawDeck, discardDeck, 200);
        Logger.logExit(player);
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player200_output.txt");
        Scanner scanner = new Scanner(log);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.equals("player 100 final hand: 1 2 3 4")) {
                return;
            }
        }
    }

    //Tests the logging of a player informing other players they have won
    @Test
    public void logInformedTest() throws Exception {
        Deck drawDeck1 = new Deck(1);
        Deck discardDeck1 = new Deck(2);
        Deck drawDeck2 = new Deck(3);
        Deck discardDeck2 = new Deck(4);
        MockPlayer player1 = new MockPlayer(drawDeck1, discardDeck1, 300);
        MockPlayer player2 = new MockPlayer(drawDeck2, discardDeck2, 400);
        int[] args = {player1.name};
        Logger.logInformed(player2, args);
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player200_output.txt");
        Scanner scanner = new Scanner(log);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.equals("player 300 has informed player 400 that player 300 has won")) {
                return;
            }
        }
    }

    //Tests the logging of the final state of a deck
    @Test
    public void logDeckTest() throws Exception {
        Deck deck = new Deck(500);
        for(int i = 0; i < 4; i++) {
            deck.addToDeck(new Card(i));
        }
        Logger.logDeck(deck);
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "deck500_output.txt");
        Scanner scanner = new Scanner(log);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.equals("deck500 contents: 0 1 2 3")) {
                return;
            }
        }
    }

}
