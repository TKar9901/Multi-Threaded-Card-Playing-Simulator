import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    protected static final String path = System.getProperty("user.dir") + File.separator + "logs"
            + File.separator;

    //Creates a log file for a player
    public static File createPlayerLog(Player player) {
        String fs = path + String.format("player%d_output.txt", player.name);
        File playerLog = new File(fs);
        try {
            playerLog.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return playerLog;
    }

    //Creates a log file for a deck
    public static File createDeckLog(Deck deck) {
        String fs = path + String.format("deck%d_output.txt", deck.name);
        return new File(fs);
    }

    //Logs which card a player draws from their draw deck
    public static synchronized void logDraw(Player player, int[] args) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d draws a %d from deck %d\n",
                    player.name, args[0], args[1]);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Logs which card player discards to their discard deck
    public static synchronized void logDiscard(Player player, int[] args) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d discards a %d to deck %d\n",
                    player.name, args[0], args[1]);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Logs the players current hand after performing a draw and discard
    public static synchronized void logCurrent(Player player, int[] args){
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d current hand: %d %d %d %d\n",
                    player.name, args[0], args[1], args[2], args[3]);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Logs the players starting hand
    public static void logInitial(Player player, int[] args){
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d initial hand: %d %d %d %d\n",
                    player.name, args[0], args[1], args[2], args[3]);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Logs the players final hand after they or another player has won
    public static void logFinal(Player player, int[] args){
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d final hand: %d %d %d %d\n",
                    player.name, args[0], args[1], args[2], args[3]);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Logs that the player has won the game (also sends this message to the terminal)
    public static synchronized void logWin(Player player) {
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

    //Logs that a player has exited the game
    public static void logExit(Player player) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d exits\n", player.name);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Logs that a player has been informed by the victor that the victor has won the game
    public static void logInformed(Player player, int[] args) {
        try {
            FileWriter writer = new FileWriter(player.playerLog, true);
            String fs = String.format("player %d has informed player %d that player %d has won\n",
                    args[0], player.name, args[0]);
            writer.write(fs);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /*Logs the final state of a deck, a deck will have a maximum of 7 cards and a minimum of 1 at the end
    based on the speed of each playerThread*/
    public static void logDeck(Deck deck) {
        try {
            int[] args = deck.readDeck();
            FileWriter writer = new FileWriter(deck.deckLog, true);
            if (args.length == 70) {
                String fs = String.format("deck%d contents: %d %d %d %d %d %d %d",
                        deck.name, args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
                writer.write(fs);
            } else if (args.length == 6) {
                String fs = String.format("deck%d contents: %d %d %d %d %d %d",
                        deck.name, args[0], args[1], args[2], args[3], args[4], args[5]);
                writer.write(fs);
            } else if (args.length == 5) {
                String fs = String.format("deck%d contents: %d %d %d %d %d",
                        deck.name, args[0], args[1], args[2], args[3], args[4]);
                writer.write(fs);
            } else if (args.length == 4) {
                String fs = String.format("deck%d contents: %d %d %d %d",
                        deck.name, args[0], args[1], args[2], args[3]);
                writer.write(fs);
            } else if (args.length == 3) {
                String fs = String.format("deck%d contents: %d %d, %d",
                        deck.name, args[0], args[1], args[2]);
                writer.write(fs);
            } else if (args.length == 2) {
                String fs = String.format("deck%d contents: %d, %d",
                        deck.name, args[0], args[1]);
                writer.write(fs);
            } else {
                String fs = String.format("deck%d contents: %d",
                        deck.name, args[0]);
                writer.write(fs);
            }

            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
