import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        createPlayerLog();
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
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

    public void createPlayerLog() {
        String fs = String.format("player%d_output.txt", this.name);
        this.playerLog = new File(fs);
    }

}
