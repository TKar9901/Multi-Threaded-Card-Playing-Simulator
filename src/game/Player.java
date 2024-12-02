import java.util.ArrayList;

public class Player {
    Deck drawDeck;
    Deck discardDeck;
    int denomination;
    int name;
    ArrayList<Card> hand = new ArrayList<>();

    public Player(Deck draw, Deck discard, int id) {
        drawDeck = draw;
        discardDeck = discard;
        name = id;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int[] readHand() {
        int[] handasArray = {};
        for(int i=0; i<this.hand.size(); i++) {
            handasArray[i] = this.hand.get(i).getValue();
        }
        return handasArray;
    }

    public void addToHand(Card c) {
        this.hand.add(c);
    }

    public void removeFromHand(Card c) {
        this.hand.remove(c);
    }

    public String log(String type, int[] args) {
        String fs = "";
        switch (type) {
            case "draw":
                fs = String.format("player %d draws a %d from deck %d", this.name, args[0], args[1]);
                break;
            case "discard":
                fs = String.format("player %d discards %d to deck %d", this.name, args[0], args[1]);
                break;
            case "current":
                fs = String.format("player %d current hand: %d %d %d %d", this.name, args[0], args[1], args[2], args[3]);
                break;
            case "initial":
                fs = String.format("player %d intial hand: %d %d %d %d", this.name, args[0], args[1], args[2], args[3]);
                break;
            case "final":
                fs = String.format("player %d final hand: %d %d %d %d", this.name, args[0], args[1], args[2], args[3]);
                break;
            case "win":
                fs = String.format("player %d wins", this.name);
                break;
            case "exit":
                fs = String.format("player %d exits", this.name);
                break;
            case "informed":
                fs = String.format("player %d has informed player %d that player %d has won", args[0], this.name, args[0]);
                break;
        }
        return fs;
        
    }
}
