import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    public static void logDraw(Player player, int[] args) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d draws a %d from deck %d\n",
                    player.name, args[0], args[1]);
            System.out.println(fs);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void logDiscard(Player player, int[] args) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d discards %d to deck %d\n",
                    player.name, args[0], args[1]);
            System.out.println(fs);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logCurrent(Player player, int[] args){
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d current hand: %d %d %d %d\n",
                    player.name, args[0], args[1], args[2], args[3]);
            System.out.println(fs);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logInitial(Player player, int[] args){
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d initial hand: %d %d %d %d\n",
                    player.name, args[0], args[1], args[2], args[3]);
            System.out.println(fs);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logFinal(Player player, int[] args){
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d final hand: %d %d %d %d\n",
                    player.name, args[0], args[1], args[2], args[3]);
            System.out.println(fs);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logWin(Player player) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d wins\n", player.name);
            System.out.println(fs);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logExit(Player player) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d exits\n", player.name);
            System.out.println(fs);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logInformed(Player player, int[] args) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d has informed player %d that player %d has won\n",
                    args[0], player.name, args[0]);
            System.out.println(fs);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void logDeck(Deck deck) {
        try {
            int[] args = deck.readDeck();
            FileWriter writer = new FileWriter(deck.deckLog, true);
            String fs = String.format("deck%d contents: %d %d %d %d",
                    deck.name, args[0], args[1], args[2], args[3]);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
