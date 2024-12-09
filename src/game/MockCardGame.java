import java.util.ArrayList;

public class MockCardGame extends CardGame{

    public void setPlayers(int n) {
        CardGame.nPlayers = n;
    }
    public void setPackList(ArrayList<Integer> n) {
        CardGame.packList = n;
    }

    public int getNPlayers() {
        return nPlayers;
    }

    public ArrayList<Integer> getPackList() {
        return packList;
    }

    public ArrayList<Card> getPack() {
        return pack;
    }

    public ArrayList<Deck> getDeckList() {
        return deckList;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<PlayerThread> getPlayerThreads() {
        return playerThreads;
    }

    public static void resetStaticState() {
        players.clear();
        deckList.clear();
        nPlayers = 0;
        packList.clear();
        pack.clear();
        playerThreads.clear();
    }

}
