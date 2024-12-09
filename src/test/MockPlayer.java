public class MockPlayer extends Player {

    public MockPlayer(Deck draw, Deck discard, int id) {
        super(draw, discard, id);
    }

    //A function to quickly set a predefined hand for testing
    public void setHand() {
        this.hand.clear();
        this.hand.add(new Card(1));
        this.hand.add(new Card(2));
        this.hand.add(new Card(3));
        this.hand.add(new Card(4));
    }

    //A function to quickly set a custom hand for testing
    public void setCustomHand(int[] array) {
        this.hand.clear();
        this.hand.add(new Card(array[0]));
        this.hand.add(new Card(array[1]));
        this.hand.add(new Card(array[2]));
        this.hand.add(new Card(array[3]));
    }

}
