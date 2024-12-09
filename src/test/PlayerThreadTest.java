import org.junit.BeforeClass;
import org.junit.Test;
import java.io.File;
import java.util.Scanner;
import static org.junit.Assert.*;
import java.lang.reflect.*;


public class PlayerThreadTest {
    private MockPlayerThread playerThread;
    private MockPlayer player;
    private Deck drawDeck;
    private Deck discardDeck;
    private Scanner scanner;

    private static Method getMakeLogDir() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("makeLogDir");
        method.setAccessible(true);
        return method;
    }

    @BeforeClass
    public static void setUp() throws Exception {
        Method makeLogDir = getMakeLogDir();
        makeLogDir.invoke(null);
        File log = new File(System.getProperty("user.dir")
                + File.separator + "logs" +
                File.separator + "player1_output.txt");
        if(!log.exists()) {
            log.createNewFile();
        }
    }
    //Test that the drawCard function works correctly
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
            if(line.equals("player 1 draws a 1 from deck 1")) {
                return;
            }
        }
        fail();
    }

    //Test the discardCard function works correctly
    @Test
    public void discardCardTest() throws Exception  {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        int[] hand = {2,2,2,2};
        player.setCustomHand(hand);
        playerThread = new MockPlayerThread(player);
        playerThread.setTestType("discardCard");
        playerThread.start();
        playerThread.join();
        int val = discardDeck.cards.getFirst().getValue();
        assertEquals(2, val);
    }

    //Test the readCurrentHand function works correctly
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
            if(line.equals("player 1 current hand: 2 2 2 2")) {
                return;
            }
        }
        fail();
    }

    //Tests that a winning hand is correctly identified
    @Test
    public void checkHandStateTest() throws Exception {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        int[] hand = {2, 2, 2, 2};
        player.setCustomHand(hand);
        playerThread = new MockPlayerThread(player);
        playerThread.setTestType("checkHand");
        playerThread.start();
        playerThread.join();
        assertTrue(playerThread.result.equals("won"));

    }
}
