import java.util.ArrayList;

public class MockCardGame extends CardGame{

    //Provides an easy way to set the players in a game for testing
    public void setPlayers(int n) {
        CardGame.nPlayers = n;
    }

    //Provides an easy way to set the pack in a game for testing
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

    //Resets all elements of a game each time a test is ran to ensure they work correctly
    public static void resetStaticState() {
        players.clear();
        deckList.clear();
        nPlayers = 0;
        packList.clear();
        pack.clear();
        playerThreads.clear();
    }

}
