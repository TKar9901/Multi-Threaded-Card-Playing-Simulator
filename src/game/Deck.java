import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
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
    
    public String getContent() {
        String str = "";
        for(int i=0; i<this.cards.size(); i++) {
            str = str + " " + this.cards.get(i).toString();
        }
        return str;
    }
    
}
