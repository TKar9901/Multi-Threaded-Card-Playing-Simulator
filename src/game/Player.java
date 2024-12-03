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
    File deckLog;
    ArrayList<Card> hand = new ArrayList<>();

    public Player(Deck draw, Deck discard, int id) {
        drawDeck = draw;
        discardDeck = discard;
        name = id;
        createLog();
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int[] readHand() {
        int[] handasArray = new int[this.hand.size()];
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

    public void createLog() {
        String fs = String.format("player%d_output.txt", this.name);
        this.playerLog = new File(fs);
        String ds = String.format("deck%d_output.txt", this.name);
        this.deckLog = new File(ds);
    }

    public void log(String type, int[] args) {
        String fs = "";
        try {
            FileWriter writer = new FileWriter(this.playerLog, true);
        switch (type) {
            case "draw":
                fs = String.format("player %d draws a %d from deck %d\n", this.name, args[0], args[1]);
                System.out.println(fs);
                writer.write(fs);
                break;
            case "discard":
                fs = String.format("player %d discards %d to deck %d\n", this.name, args[0], args[1]);
                writer.write(fs);
                System.out.println(fs);
                break;
            case "current":
                fs = String.format("player %d current hand: %d %d %d %d\n", this.name, args[0], args[1], args[2], args[3]);
                writer.write(fs);
                System.out.println(fs);
                break;
            case "initial":
                fs = String.format("player %d intial hand: %d %d %d %d\n", this.name, args[0], args[1], args[2], args[3]);
                writer.write(fs);
                System.out.println(fs);
                break;
            case "final":
                fs = String.format("player %d final hand: %d %d %d %d", this.name, args[0], args[1], args[2], args[3]);
                writer.write(fs);
                System.out.println(fs);
                break;
            case "win":
                fs = String.format("player %d wins", this.name);
                writer.write(fs);
                System.out.println(fs);
                break;
            case "exit":
                fs = String.format("player %d exits", this.name);
                writer.write(fs);
                System.out.println(fs);
                break;
            case "informed":
                fs = String.format("player %d has informed player %d that player %d has won", args[0], this.name, args[0]);
                writer.write(fs);
                System.out.println(fs);
                break;
            }
            writer.close();
        } catch(IOException e) {
            System.out.println("Something went wrong");
        }
        
        
    }
}
