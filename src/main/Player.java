import java.util.ArrayList;
import java.io.File;

public class Player {
    Deck drawDeck;
    Deck discardDeck;
    int denomination;
    int name;
    File playerLog;
    ArrayList<Card> hand = new ArrayList<>();

    public Player(Deck draw, Deck discard, int id) {
        drawDeck = draw;
        discardDeck = discard;
        name = id;
        denomination = id;
        playerLog = Logger.createPlayerLog(this);
    }

    public int[] readHand() {
        int[] handAsArray = new int[this.hand.size()];
        for(int i=0; i<this.hand.size(); i++) {
            handAsArray[i] = this.hand.get(i).getValue();
        }
        return handAsArray;
    }

    public void addToHand(Card c) {
        this.hand.add(c);
    }

    public void removeFromHand(Card c) {
        this.hand.remove(c);
    }

}
