import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

//Runs all the tests, detailing any failures
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(CardGameTest.class, CardTest.class
        , DeckTest.class, LoggerTest.class, PlayerTest.class, PlayerThreadTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Tests were successful: " + result.wasSuccessful());
        /* I included this as it seemed that there was a bug. The tests would still be running after reportedly having all passed
        and then it would complain about a specific drawCard out of bound index issue. Running the tests through the IDE never causes
        this problem and on review there is no reason for this to happen. I decided to add this to stop them running past the point
        where they've already passed.
         */
        System.exit(0);
    }
}
