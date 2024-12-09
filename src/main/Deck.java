import java.util.ArrayList;
import java.io.File;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();
    File deckLog;
    int name;

    public Deck(int id) {
        name = id;
        deckLog = Logger.createDeckLog(this);
    }

    //Allows a player to retrieve the card at the top of the deck and removes it from the deck
    public synchronized Card drawFromDeck() {
        Card c = this.cards.getFirst();
        this.cards.remove(c);
        return c;
    }

    //Allows a player to discard a card to this deck adding it to the bottom
    public synchronized void addToDeck(Card c) {
        this.cards.add(c);
    }

    //Returns the values of each card in the deck in an array
    public int[] readDeck() {
        int[] cardAsArray = new int[this.cards.size()];
        for(int i=0; i<this.cards.size(); i++) {
            cardAsArray[i] = this.cards.get(i).getValue();
        }
        return cardAsArray;
    }
    
}
