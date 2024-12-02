import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.File;

public class CardGame {
    static int nPlayers;
    static String packLocation;
    static ArrayList<Integer> packList = new ArrayList<>();
    static ArrayList<Player> players = new ArrayList<>();
    static ArrayList<Thread> playerThreads = new ArrayList<>();
    static ArrayList<Card> pack = new ArrayList<>();
    static ArrayList<Deck> deckList = new ArrayList<>();
    
    public static void gameSetup() {
        for(int i : packList) {
            Card card = new Card(i);
            pack.add(card);
        }
        for(int i=1; i <= nPlayers; i++) {
            Deck deck = new Deck(i);
            deckList.add(deck);
        }
        for(int i=1; i <= nPlayers; i++) {
            if(i == nPlayers) {
                Player player = new Player(deckList.get(i-1), deckList.get(0), i); 
            }
            Player player = new Player(deckList.get(i-1), deckList.get(i), i);
        }
        for(int i=0; i<players.size(); i++) {
            for(int j=0; j<=4*nPlayers; j+=nPlayers) {
                players.get(i).addToHand(pack.get(j));
            }
        }
        for(int i=0; i<deckList.size(); i++) {
            for(int j=4*nPlayers+1; j<=4*nPlayers; j+=nPlayers) {
                deckList.get(i).addCard(pack.get(j));
            }
        }
        Set<Integer> uniquesCreator = new HashSet<Integer>(packList);
        ArrayList<Integer> uniquesOrdered = new ArrayList<Integer>(uniquesCreator);
        for(int i=0; i<nPlayers; i++) {
            players.get(i).setDenomination(uniquesOrdered.get(i));
        }
    }

    public static boolean packValidity(ArrayList packList, int nPlayers) {
        if(packList.size() != 8 * nPlayers) {
            return false;
        }
        
        Set<Integer> set = new HashSet<Integer>(packList);
        if(set.size() < nPlayers) {
            return false;
        }
        for(int i : set) {
            if(Collections.frequency(packList, i) < 4) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        while(true) {
            System.out.println("Player number");
            int nPlayers = Integer.parseInt(s.nextLine());
            if(nPlayers > 2) {
                break;
            }
            System.out.println("Invalid player number, ensure more than two players."); 
        }

        while(true) {
            System.out.println("Pack Location");
            String packLocation = s.nextLine();
            try {
                File packFile = new File(packLocation);
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
                System.out.println("File could not be found, ensure it is in the current directory");
            } catch (InvalidFormatException e) {
                System.out.println(e.getMessage());
            }
           
        }

        s.close();
        gameSetup();
        
        for(int i=0; i<nPlayers; i++) {
            PlayerThread thread = new PlayerThread(players.get(i));
            playerThreads.add(thread);
            thread.start();
        }

    }
}
