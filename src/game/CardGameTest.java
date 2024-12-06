import org.junit.Test;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

public class CardGameTest {
    MockCardGame game = new MockCardGame();

    @Before
    public void setUp() throws Exception {
        MockCardGame.resetStaticState();
    }

    @After
    public void tearDown() throws Exception {
        MockCardGame.resetStaticState();
    }

    private Method getPackValidity() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("packValidity");
        method.setAccessible(true);
        return method;
    }

    private Method getCreateCards() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("createCards");
        method.setAccessible(true);
        return method;
    }

    private Method getCreateDeck() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("createDeck");
        method.setAccessible(true);
        return method;
    }

    private Method getCreatePlayers() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("createPlayers");
        method.setAccessible(true);
        return method;
    }

    private Method getDealCards() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("dealCards");
        method.setAccessible(true);
        return method;
    }

    private Method getSetPreferred() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("setPreferred");
        method.setAccessible(true);
        return method;
    }

    private Method getStartThreads() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("startThreads");
        method.setAccessible(true);
        return method;

    }

    private Method getCloseThreads() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("closeThreads");
        method.setAccessible(true);
        return method;
    }

    private Method getGetNumberOfPlayers() throws NoSuchMethodException {
        Method method = CardGame.class.getDeclaredMethod("getNumberOfPlayers");
        method.setAccessible(true);
        return method;
    }

    //Tests all functionality of the packValidity function
    @Test 
    public void packValidityTest() throws Exception {
        Method packValidity = getPackValidity();

        game.setPlayers(2);
        game.setPackList(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)));
        assertFalse((Boolean)(packValidity.invoke(null)));

        game.setPackList(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 43, 33, 35, 3443, 3344, 3, 433, 44)));
        assertFalse((Boolean)(packValidity.invoke(null)));

        game.setPlayers(17);
        game.setPackList(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16)));
        assertFalse((Boolean)(packValidity.invoke(null)));

        game.setPlayers(2);
        game.setPackList(new ArrayList<>(Arrays.asList(1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)));
        assertFalse((Boolean)(packValidity.invoke(null)));

        game.setPlayers(2);
        game.setPackList(new ArrayList<>(Arrays.asList(1, 1, 1, -2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)));
        assertFalse((Boolean)(packValidity.invoke(null)));

        game.setPackList(new ArrayList<>(Arrays.asList(1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)));
        assertTrue((Boolean)(packValidity.invoke(null)));
    }

    //Tests to see if the pack of card objects is the expected size
    @Test
    public void createCardsTest() throws Exception {
        MockCardGame game = new MockCardGame();
        Method createCards = getCreateCards();
        game.setPackList(new ArrayList<>(Arrays.asList(1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)));
        createCards.invoke(null);
        assertEquals(16, game.getPack().size());
    }

    //Tests to see if the correct number of decks are being created
    @Test
    public void createDeckTest() throws Exception {
        Method createDecks = getCreateDeck();
        game.setPlayers(5);
        createDecks.invoke(null);
        assertEquals(5, game.getDeckList().size());
    }

    /* Tests to see if the correct number of players are created and that their
    draw and discard decks are correct
     */
    @Test
    public void createPlayersTest() throws Exception {
        Method createPlayers = getCreatePlayers();
        Method createDecks = getCreateDeck();
        game.setPlayers(5);
        createDecks.invoke(null);
        createPlayers.invoke(null);

        //Test the correct number of players are created
        assertEquals(5, game.getPlayers().size());

        //Test they have the correct draw and discard deck
        for(int i = 0; i < 5; i++) {
            assertEquals(game.getDeckList().get(i), game.getPlayers().get(i).drawDeck);
            if(i != 4) {
                assertEquals(game.getDeckList().get(i+1), game.getPlayers().get(i).discardDeck);
            } else {
                assertEquals(game.getDeckList().get(0), game.getPlayers().get(i).discardDeck);
            }
        }
    }

    @Test
    public void dealCardsTest() throws Exception {
        Method dealCards = getDealCards();
        Method createCards = getCreateCards();
        Method createDecks = getCreateDeck();
        Method createPlayers = getCreatePlayers();
        game.setPlayers(2);
        game.setPackList(new ArrayList<>(Arrays.asList(1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)));
        createCards.invoke(null);
        createDecks.invoke(null);
        createPlayers.invoke(null);
        dealCards.invoke(null);

        //Check that the players receive the expected hands and that the decks have the expected cards
        int[] expectedHand = {1,1,2,2};
        assertArrayEquals(expectedHand, game.getPlayers().get(0).readHand());
        assertArrayEquals(expectedHand, game.getPlayers().get(1).readHand());

        //Check that the two decks are as expected
        int[] expectedDeck = {2,2,2,2};
        assertArrayEquals(expectedDeck, game.getDeckList().get(0).readDeck());
        assertArrayEquals(expectedDeck, game.getDeckList().get(1).readDeck());
    }

    @Test
    public void setPreferredTest() throws Exception {
        Method setPreferred = getSetPreferred();
        Method createCards = getCreateCards();
        Method createDecks = getCreateDeck();
        Method createPlayers = getCreatePlayers();
        game.setPlayers(2);
        game.setPackList(new ArrayList<>(Arrays.asList(4, 4, 4, 4, 5, 5, 5, 5, 7, 7, 7, 7, 2, 2, 2, 2)));
        createCards.invoke(null);
        createDecks.invoke(null);
        createPlayers.invoke(null);
        setPreferred.invoke(null);

        //Test that the preference is being correctly determined and assigned (the lowest value two uniques)
        assertEquals(2, game.getPlayers().get(0).denomination);
        assertEquals(4, game.getPlayers().get(1).denomination);
    }

    //Tests that the threads start correctly
    @Test
    public void startThreadsTest() throws Exception {
        Method startThreads = getStartThreads();
        Method dealCards = getDealCards();
        Method setPreferred = getSetPreferred();
        Method createCards = getCreateCards();
        Method createDecks = getCreateDeck();
        Method createPlayers = getCreatePlayers();
        game.setPlayers(2);
        game.setPackList(new ArrayList<>(Arrays.asList(4, 4, 4, 4, 5, 5, 5, 5, 7, 7, 7, 7, 2, 2, 2, 2)));
        createCards.invoke(null);
        createDecks.invoke(null);
        createPlayers.invoke(null);
        setPreferred.invoke(null);
        dealCards.invoke(null);
        startThreads.invoke(null);
        assertEquals(true, game.getPlayerThreads().get(0).isAlive());
        assertEquals(true, game.getPlayerThreads().get(1).isAlive());
    }

    //Test that the threads close correctly
    @Test
    public void closeThreadsTest() throws Exception {
        Method closeThreads = getCloseThreads();
        Method startThreads = getStartThreads();
        Method dealCards = getDealCards();
        Method setPreferred = getSetPreferred();
        Method createCards = getCreateCards();
        Method createDecks = getCreateDeck();
        Method createPlayers = getCreatePlayers();
        game.setPlayers(2);
        game.setPackList(new ArrayList<>(Arrays.asList(4, 4, 4, 4, 5, 5, 5, 5, 7, 7, 7, 7, 2, 2, 2, 2)));
        createCards.invoke(null);
        createDecks.invoke(null);
        createPlayers.invoke(null);
        setPreferred.invoke(null);
        dealCards.invoke(null);
        startThreads.invoke(null);
        closeThreads.invoke(null);
        assertEquals(true, game.getPlayerThreads().get(0).isInterrupted());
        assertEquals(true, game.getPlayerThreads().get(1).isInterrupted());
    }
   
}
