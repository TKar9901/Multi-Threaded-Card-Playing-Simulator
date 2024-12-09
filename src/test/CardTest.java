import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    private Card card = new Card(1);

    //Tests the correct value is returned
    @Test
    public void CardTest() {
        assertEquals(1, card.getValue());
    }
}
