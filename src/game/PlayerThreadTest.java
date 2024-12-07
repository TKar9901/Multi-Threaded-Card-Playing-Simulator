import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;

/*IMPORTANT PLEASE READ -- If these tests fail, it seems to be due to an issue when running them all together
as they class. They should pass the majority of the time, but they don't properly reset the log file each time
when ran as such. Try running them individually if they do fail.
 */

public class PlayerThreadTest {
    private MockPlayerThread playerThread;
    private MockPlayer player;
    private Deck drawDeck;
    private Deck discardDeck;
    private Scanner scanner;

    @Before
    public void deleteLog() throws Exception {
        final File logFile = new File(System.getProperty("user.dir") + File.separator
                + "logs" + File.separator + "player1_output.txt");
        if (logFile.exists()) {
            logFile.delete();
        }
        }

    //Test the log file gets a line when a player draws a card
    @Test
    public void drawCardTest() throws Exception  {
        drawDeck = new Deck(1);
        drawDeck.addToDeck(new Card(1));
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        playerThread = new MockPlayerThread(player);
        playerThread.setTestType("drawCard");
        playerThread.start();
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player1_output.txt");
        scanner = new Scanner(log);
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        assertEquals("player 1 draws a 1 from deck 1", line);
    }

    //Test the log file gets a line when a player discards a card
    @Test
    public void discardCardTest() throws Exception  {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        discardDeck.addToDeck(new Card(1));
        player = new MockPlayer(drawDeck, discardDeck, 1);
        int[] hand = {2,2,2,2};
        player.setCustomHand(hand);
        playerThread = new MockPlayerThread(player);
        playerThread.setTestType("discardCard");
        playerThread.start();
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player1_output.txt");
        scanner = new Scanner(log);
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        assertEquals("player 1 discards a 2 to deck 2", line);
    }

    //Test the log file gets a line when a hand is read
    @Test
    public void readCurrentHandTest() throws Exception  {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        int[] hand = {2,2,2,2};
        player.setCustomHand(hand);
        playerThread = new MockPlayerThread(player);
        playerThread.setTestType("currentHand");
        playerThread.start();
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player1_output.txt");
        scanner = new Scanner(log);
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        assertEquals("player 1 current hand: 2 2 2 2", line);

    }

    //Tests that a winning hand is correctly identified
    @Test
    public void checkHandStateFailTest() throws Exception  {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        player.setHand();
        playerThread = new MockPlayerThread(player);
        playerThread.setTestType("checkHand");
        playerThread.start();
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player1_output.txt");
        scanner = new Scanner(log);
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        assertNotEquals("player 1 wins", line);
    }

    @Test
    public void checkHandStateWinTest() throws Exception {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        int[] hand = {2, 2, 2, 2};
        player.setCustomHand(hand);
        playerThread = new MockPlayerThread(player);
        playerThread.setTestType("checkHand");
        playerThread.start();
        String line = "";
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player1_output.txt");
        scanner = new Scanner(log);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
        }
        assertEquals("player 1 wins", line);
    }
}
