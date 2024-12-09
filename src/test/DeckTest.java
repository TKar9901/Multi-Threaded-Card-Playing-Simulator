import org.junit.Test;
import static org.junit.Assert.*;

public class DeckTest {
    private Deck deck;

    //Tests that the correct card gets drawn
    @Test
    public void drawFromDeckTest() {
        deck = new Deck(1);
        deck.addToDeck(new Card(1));
        deck.addToDeck(new Card(2));
        deck.addToDeck(new Card(3));
        deck.addToDeck(new Card(4));
        assertEquals(1, deck.drawFromDeck().value);
    }

    //Tests the card gets added to the bottom of the deck
    @Test
    public void addToDeckTest() {
        deck = new Deck(2);
        deck.addToDeck(new Card(1));
        deck.addToDeck(new Card(2));
        deck.addToDeck(new Card(3));
        deck.addToDeck(new Card(4));
        assertEquals(4, deck.cards.getLast().value);
    }

    //Tests the deck is read correctly
    @Test
    public void readDeckTest() {
        deck = new Deck(3);
        deck.addToDeck(new Card(1));
        deck.addToDeck(new Card(2));
        deck.addToDeck(new Card(3));
        deck.addToDeck(new Card(4));
        int[] expectedDeck = {1,2,3,4};
        assertArrayEquals(expectedDeck, deck.readDeck());
    }
}
