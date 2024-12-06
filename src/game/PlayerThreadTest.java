import org.junit.Before;
import org.junit.Test;

import java.io.EOFException;
import java.io.File;
import java.util.Scanner;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class PlayerThreadTest {
    private MockPlayerThread playerThread;
    private MockPlayer player;
    private Deck drawDeck;
    private Deck discardDeck;
    private Scanner scanner;

    @Before
    public void setUp() throws Exception {
        File currentDirectory = new File(System.getProperty("user.dir") + File.separator + "logs");
        File[] logFiles = currentDirectory.listFiles((_, filename) -> filename.endsWith(".txt"));
        for (File logFile : logFiles) {
            logFile.delete();
        }

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
