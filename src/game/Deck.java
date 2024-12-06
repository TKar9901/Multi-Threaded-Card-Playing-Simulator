import java.util.ArrayList;
import java.io.File;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();
    File deckLog;
    int name;

    public Deck(int id) {
        name = id;
        createDeckLog();
    }

    private void createDeckLog() {
        String fs = String.format("deck%d_output.txt", this.name);
        this.deckLog = new File(fs);
    }

    public synchronized Card drawFromDeck() {
        return this.cards.getFirst();
    }

    public synchronized void addToDeck(Card c) {
        this.cards.add(c);
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public int[] readDeck() {
        int[] cardAsArray = new int[this.cards.size()];
        for(int i=0; i<this.cards.size(); i++) {
            cardAsArray[i] = this.cards.get(i).getValue();
        }
        return cardAsArray;
    }
    
}
