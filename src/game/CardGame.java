import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.FilenameFilter;

public class CardGame {
    static int nPlayers;
    static String packLocation;
    static ArrayList<Integer> packList = new ArrayList<>();
    static ArrayList<Player> players = new ArrayList<>();
    static ArrayList<PlayerThread> playerThreads = new ArrayList<>();
    static ArrayList<Card> pack = new ArrayList<>();
    static ArrayList<Deck> deckList = new ArrayList<>();
    
    public static void gameSetup() {
        for(int i : packList) {
            Card card = new Card(i);
            pack.add(card);
        }
        for(int i=0; i < nPlayers; i++) {
            Deck deck = new Deck(i);
            deckList.add(deck);
        }
        for(int i=0; i < nPlayers; i++) {
            if(i+1 == nPlayers) {
                Player player = new Player(deckList.get(i), deckList.get(0), i);
                players.add(player);
                break;
            }
            Player player = new Player(deckList.get(i), deckList.get(i+1), i);
            players.add(player);
        }
        for(int i=0; i<players.size(); i++) {
            for(int j=i; j<(4*nPlayers)+i; j+=nPlayers) {
                players.get(i).addToHand(pack.get(j));
            }
            players.get(i).log("initial", players.get(i).readHand());
        }
        for(int i=0; i<deckList.size(); i++){
            for(int j=i+4*nPlayers+1; j<=8*nPlayers-i; j+=nPlayers) {
                deckList.get(i).addToDeck(pack.get(j));
            }
        }
        Set<Integer> uniquesCreator = new HashSet<Integer>(packList);
        ArrayList<Integer> uniquesOrdered = new ArrayList<Integer>(uniquesCreator);
        for(int i=0; i<nPlayers; i++) {
            players.get(i).setDenomination(uniquesOrdered.get(i));
        }
    }

    public static boolean packValidity(ArrayList<Integer> packList, int nPlayers) {
        if(packList.size() != 8 * nPlayers) {
            return false;
        }
        
        Set<Integer> set = new HashSet<Integer>(packList);
        if(set.size() < nPlayers) {
            System.out.println("Second");
            return false;
        }
        for(int i : set) {
            if(Collections.frequency(packList, i) < 4) {
                System.out.println("Third");
                return false;
            }
            if(i < 0) {
                return false;
            }
        }

        return true;
    }

    public static void gameEnd(Player winner) throws InterruptedException {
        for(PlayerThread thread : playerThreads) {
            thread.interrupt();
        }
        for(Player player : players) {
            int[] args = {};
            if(player==winner) {
                player.log("win", args);
                String fs = String.format("Player %d wins", winner.name);
                System.out.println(fs);
            } else {
                int[] args2 = {winner.name};
                player.log("informed", args2);
            }
            player.log("exit", args);
            player.log("final",player.readHand());
            player.deckLog(player.drawDeck.readDeck());
        }
    }
    public static void main(String[] args) {
        File dir = new File(System.getProperty("user.dir"));
        System.out.println(dir);
        File[] previousLogs = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".txt");
            }
        });
        for(File file : previousLogs) {
            file.delete();
        }

        Scanner s = new Scanner(System.in);

        while(true) {
            System.out.println("Player number");
            nPlayers = Integer.parseInt(s.nextLine());
            if(nPlayers > 1) {
                break;
            }
            System.out.println("Invalid player number, ensure atleast two players."); 
        }

        while(true) {
            System.out.println("Pack Location");
            String packName = s.nextLine();
            try {
                File packFile = new File("src\\game\\"+packName+".txt");
                Scanner packScanner = new Scanner(packFile);
                while (packScanner.hasNextLine()) {
                    String data = packScanner.nextLine();
                    packList.add(Integer.parseInt(data));
                }
                packScanner.close();
                if(packValidity(packList, nPlayers)) {
                    break;
                } else {
                    throw new InvalidFormatException("Pack format invalid, please fix and run again"); 
                } 
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("File could not be found, ensure it is in the current directory");
            } catch (InvalidFormatException e) {
                System.out.println(e.getMessage());
            }
           
        }

        s.close();
        gameSetup();
        for(Deck deck : deckList) {
            int[] items = deck.readDeck();
            for(int i : items) {
                System.out.print(i);
            }
        }
        
        for(int i=0; i<nPlayers; i++) {
            PlayerThread thread = new PlayerThread(players.get(i));
            playerThreads.add(thread);
            thread.start();
        }

    }
}
