import java.io.File;

public class MockPlayer extends Player {
    File testLog;

    public MockPlayer(Deck draw, Deck discard, int id) {
        super(draw, discard, id);
    }

    public void createTestLog(String testName) {
        testLog = MockLogger.createTestPlayerLog(testName, this);
    }


    public void setHand() {
        this.hand.clear();
        this.hand.add(new Card(1));
        this.hand.add(new Card(2));
        this.hand.add(new Card(3));
        this.hand.add(new Card(4));
    }

    public void setCustomHand(int[] array) {
        this.hand.clear();
        this.hand.add(new Card(array[0]));
        this.hand.add(new Card(array[1]));
        this.hand.add(new Card(array[2]));
        this.hand.add(new Card(array[3]));
    }

}
