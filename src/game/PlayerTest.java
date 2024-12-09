import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

public class PlayerTest {
    private MockPlayer player;
    private Deck drawDeck;
    private Deck discardDeck;

    private static Method getMakeLogDir() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("makeLogDir");
        method.setAccessible(true);
        return method;
    }

    @BeforeClass
    public static void setUp() throws Exception {
        Method makeLogDir = getMakeLogDir();
        makeLogDir.invoke(null);
    }
    //Tests that a hand is read correctly
    @Test
    public void readHandTest() {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        player.setHand();
        int[] expectedHand = {1,2,3,4};
        assertArrayEquals(expectedHand, player.readHand());
    }

    //Testing card gets added correctly to hand
    @Test
    public void addToHandTest() {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        player.setHand();
        player.addToHand(new Card(5));
        int[] expectedHand = {1,2,3,4,5};
        assertArrayEquals(expectedHand, player.readHand());
    }

    //Testing card gets removed correctly from hand
    @Test
    public void removeFromHandTest() {
        drawDeck = new Deck(1);
        discardDeck = new Deck(2);
        player = new MockPlayer(drawDeck, discardDeck, 1);
        player.setHand();
        player.removeFromHand(player.hand.getFirst());
        int[] expectedHand = {2,3,4};
        assertArrayEquals(expectedHand, player.readHand());
    }
}
