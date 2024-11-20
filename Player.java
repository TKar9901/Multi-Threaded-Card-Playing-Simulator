public class Player {
    Deck drawDeck;
    Deck discardDeck;
    int denomination;
    ArrayList<Card> hand = new ArrayList<>();

    public Player(Deck draw, Deck discard) {
        drawDeck = draw;
        discardDeck = discard;
    }

    private void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    private void addToHand(Card c) {
        this.hand.add(c);
    }

    private void removeFromHand(Card c) {
        this.hand.remove(c);
    }

    //log method
    //turn method
}
