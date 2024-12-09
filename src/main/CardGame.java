import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

public class CardGame {
    protected static int nPlayers;
    protected static ArrayList<Integer> packList = new ArrayList<>();
    protected static ArrayList<Player> players = new ArrayList<>();
    protected static ArrayList<PlayerThread> playerThreads = new ArrayList<>();
    protected static ArrayList<Card> pack = new ArrayList<>();
    protected static ArrayList<Deck> deckList = new ArrayList<>();

    //Returns the number of players for the game based on the user's input
    protected static void getNumberOfPlayers(Scanner userInput) {
        while (true) {
            System.out.println("Number of players");
            try {
                nPlayers = Integer.parseInt(userInput.nextLine());
                if (nPlayers > 1) {
                    break;
                }
                System.out.println("Invalid player number, ensure at least two players.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number");
            }
        }
    }

    //If one does not already exist, makes the logging directory
    protected static void makeLogDir() {
        File logDir = new File(System.getProperty("user.dir") + File.separator + "logs");
        if (!logDir.exists()) {
            logDir.mkdir();
        }
    }

    //Attempts to load a pack based on its name (without the .txt), won't accept packs with an incorrect format or characters
    protected static void loadPack(Scanner userInput) {
        makeLogDir();
        while (true) {
            System.out.println("Please enter pack file name");
            try {
                String packName = userInput.nextLine();
                File packFile = new File(System.getProperty("user.dir") + File.separator + packName + ".txt");
                Scanner packScanner = new Scanner(packFile);
                while (packScanner.hasNextLine()) {
                    String data = packScanner.nextLine();
                    try {
                        packList.add(Integer.parseInt(data));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid card value, please fix and run again");
                        System.exit(0);
                    }
                }
                packScanner.close();
                if (!packValidity()) {
                    System.exit(0);
                } else {
                    System.out.println("Pack loaded successfully");
                    break;
                }
            } catch (FileNotFoundException e) {
                System.out.println("File could not be found, ensure it is in the current directory");
            }
        }
    }

    //Checks that the pack is the correct size
    protected static boolean packValidity() {
        if(packList.size() != 8 * nPlayers) {
            System.out.println("Invalid pack size, ensure it is 8 * number of players");
            return false;
        }
        return true;
    }

    //Converts the values from the pack into Card objects
    protected static void createCards() {
        for(int i : packList) {
            Card card = new Card(i);
            pack.add(card);
        }
    }

    //Creates the deck objects based on the number of players
    protected static void createDeck() {
        for(int i=0; i<nPlayers; i++) {
            Deck deck = new Deck(i + 1);
            deckList.add(deck);
        }
    }

    //Creates the player objects based on the number of players, assigns them a draw and discard deck
    protected static void createPlayers() {
        for (int i = 0; i < nPlayers; i++) {
            if (i + 1 == nPlayers) {
                Player player = new Player(deckList.get(i), deckList.getFirst(), i + 1);
                players.add(player);
                break;
            }
            Player player = new Player(deckList.get(i), deckList.get(i + 1), i + 1);
            players.add(player);
        }
    }

    /*Deals cards in a round-robin fashion to all players, logs their inital hand
    and then deals the remaining cards in a round-robin fashion to the decks */
    protected static void dealCards() {
        // Dealing the first 4 * nPlayers cards to players in a round-robin fashion
        for (int i = 0; i < 4 * nPlayers; i++) {
            int playerIndex = i % nPlayers; // Determine which player receives this card
            players.get(playerIndex).addToHand(pack.get(i));
        }

        // Logging the hands of all players after dealing
        for (int i = 0; i < nPlayers; i++) {
            Logger.logInitial(players.get(i), players.get(i).readHand());
        }

        // Dealing the remaining 4 * nPlayers cards to decks in a round-robin fashion
        for (int i = 4 * nPlayers; i < 8 * nPlayers; i++) {
            int deckIndex = (i - 4 * nPlayers) % deckList.size(); // Determine which deck receives this card
            deckList.get(deckIndex).addToDeck(pack.get(i));
        }
    }

    //Creates and starts each playerThread
    protected static void startThreads() {
        for(Player player : players) {
            PlayerThread thread = new PlayerThread(player);
            playerThreads.add(thread);
            thread.start();
        }
    }

    //Stops all threads from running once the game has a winner
    protected static void closeThreads() {
        for(PlayerThread thread : playerThreads) {
            thread.interrupt();
        }
    }

    //Executes the final 3 log lines for the winner and other players
    protected static void finalLogs(Player winner) {
        for(Player player : players) {
            if(player==winner) {
                Logger.logWin(winner);
            } else {
                int[] args = {winner.name};
                Logger.logInformed(player, args);
            }
            Logger.logExit(player);
            Logger.logFinal(player, player.readHand());
            Logger.logDeck(player.drawDeck);
        }
    }

    //Removes logs from previous games if they exist
    protected static void removeLogs() {
        File currentDirectory = new File(System.getProperty("user.dir") + File.separator + "logs");
        File[] logFiles = getLogFiles(currentDirectory);
        for (File logFile : logFiles) {
            logFile.delete();
        }
    }

    //Retrieves logs from previous games if they exist
    protected static File[] getLogFiles(File directory) {
        return directory.listFiles((_, filename) -> filename.endsWith(".txt"));
    }

    //Initalizes all game information
    public static void main(String[] args) {
        getNumberOfPlayers(new Scanner(System.in));
        loadPack(new Scanner(System.in));
        removeLogs();
        packValidity();
        createCards();
        createDeck();
        createPlayers();
        dealCards();
        startThreads();
    }
}
