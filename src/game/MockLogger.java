import java.io.File;

public class MockLogger extends Logger{

    public static File createTestPlayerLog(String test, Player player) {
        String fs = path + String.format(test, player.name);

        return new File(fs);
    }
}
