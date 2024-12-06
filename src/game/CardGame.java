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

    protected static void makeLogDir() {
        File logDir = new File(System.getProperty("user.dir") + File.separator + "logs");
        if (!logDir.exists()) {
            logDir.mkdir();
        }
    }

    protected static void loadPack(Scanner userInput) throws InvalidFormatException {
        makeLogDir();
        while (true) {
            System.out.println("Please enter pack file name");
            try {
                String packName = userInput.nextLine();
                File packFile = new File(System.getProperty("user.dir") + File.separator + "src"
                        + File.separator + "game" + File.separator + packName + ".txt");
                Scanner packScanner = new Scanner(packFile);
                while (packScanner.hasNextLine()) {
                    String data = packScanner.nextLine();
                    try {
                        packList.add(Integer.parseInt(data));
                    } catch (NumberFormatException e) {
                        throw new InvalidFormatException("Pack format invalid, please fix and run again");
                    }
                }
                packScanner.close();
                if (!packValidity()) {
                    throw new InvalidFormatException("Pack format invalid, please fix and run again");
                } else {
                    break;
                }
            } catch (FileNotFoundException e) {
                System.out.println("File could not be found, ensure it is in the current directory");
            }
        }
    }

    protected static boolean packValidity() {

        //Checks the correct number of cards in the pack
        if(packList.size() != 8 * nPlayers) {
            System.out.println("Invalid pack size, ensure it is 8 * number of players");
            return false;
        }
        return true;
    }

    protected static void createCards() {
        for(int i : packList) {
            Card card = new Card(i);
            pack.add(card);
        }
    }

    protected static void createDeck() {
        for(int i=0; i<nPlayers; i++) {
            Deck deck = new Deck(i + 1);
            deckList.add(deck);
        }
    }

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

    protected static void dealCards() {
        for(int i=0; i<players.size(); i++) {
            for(int j=i; j<(4*nPlayers); j+=nPlayers) {
                players.get(i).addToHand(pack.get(j));
            }
            Logger.logInitial(players.get(i), players.get(i).readHand());
        }
        for(int i=0; i<deckList.size(); i++){
            for(int j=i+4*nPlayers; j<8*nPlayers; j+=nPlayers) {
                deckList.get(i).addToDeck(pack.get(j));
            }
        }
    }

    protected static void startThreads() {
        for(Player player : players) {
            PlayerThread thread = new PlayerThread(player);
            playerThreads.add(thread);
            thread.start();
        }
    }

    protected static void closeThreads() {
        for(PlayerThread thread : playerThreads) {
            thread.interrupt();
        }
    }

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

    protected static void removeLogs() {
        File currentDirectory = new File(System.getProperty("user.dir") + File.separator + "logs");
        File[] logFiles = getLogFiles(currentDirectory);
        for (File logFile : logFiles) {
            logFile.delete();
        }
    }

    protected static File[] getLogFiles(File directory) {
        return directory.listFiles((_, filename) -> filename.endsWith(".txt"));
    }

    public static void main(String[] args) {

        getNumberOfPlayers(new Scanner(System.in));
        try {
            loadPack(new Scanner(System.in));
        } catch(InvalidFormatException e) {
            System.out.println(e.getMessage());
        }

        removeLogs();
        packValidity();
        createCards();
        createDeck();
        createPlayers();
        dealCards();
        startThreads();

    }
}
