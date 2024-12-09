import java.util.ArrayList;
import java.io.File;
import java.util.Collections;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();
    File deckLog;
    int name;

    public Deck(int id) {
        name = id;
        deckLog = Logger.createDeckLog(this);
    }

    public synchronized Card drawFromDeck() {
        Card c = this.cards.getFirst();
        this.cards.remove(c);
        return c;
    }

    public synchronized void addToDeck(Card c) {
        this.cards.add(c);
    }

    public int[] readDeck() {
        int[] cardAsArray = new int[this.cards.size()];
        for(int i=0; i<this.cards.size(); i++) {
            cardAsArray[i] = this.cards.get(i).getValue();
        }
        return cardAsArray;
    }
    
}
