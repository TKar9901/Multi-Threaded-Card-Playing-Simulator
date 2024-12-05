import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards = new ArrayList<>();
    int name;

    public Deck(int id) {
        name = id;
    }

    public synchronized Card drawFromDeck() {
        return this.cards.get(this.cards.size() - 1);
    }

    public synchronized void addToDeck(Card c) {
        this.cards.add(c);
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    public int[] readDeck() {
        int[] cardasArray = new int[this.cards.size()];
        for(int i=0; i<this.cards.size(); i++) {
            cardasArray[i] = this.cards.get(i).getValue();
        }
        return cardasArray;
    }
    
}
