import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Collection;

public class CardGame {
    static int nPlayers;
    String packLocation;
    static ArrayList<Integer> packList = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Thread> playerThreads = new ArrayList<>();
    ArrayList<Card> pack = new ArrayList<>();
    ArrayList<Deck> deckList = new ArrayList<>();
    
    public gameSetup() {
        for(int i : packList) {
            Card card = new Card(i);
            pack.add(Card);
        }
        for(int i=1; i <= nPlayers; i++) {
            Deck deck = new Deck();
            deckList.add(deck);
        }
        for(int i=1; i <= nPlayers; i++) {
            if(i == nPlayers) {
                Player player = new Player(deckList[i-1], deckList[0]); 
            }
            Player player = new Player(deckList[i-1], deckList[i]);
        }
        for(int i; i<players.size(); i++) {
            for(int j; j<=4*nPlayers; j+=nPlayers) {
                players[i].addToHand(pack[j]);
            }
        }
        for(int i; i<deckList.size(); i++) {
            for(int j=4*nPlayers+1; j<=4*nPlayers; j+=nPlayers) {
                deckList[i].addCard(pack[j]);
            }
        }
    }

    public boolean packValidity(ArrayList packList, int nPlayers) {
        if(packList.size() != 8 * nPlayers) {
            return false;
        }
        Set<Int> set = new HashSet<>(packList);
        if(set.size() != nPlayers) {
            return false;
        }
        for(int i : set) {
            if(Collections.frequency(packList, i) != 4) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        while(True) {
            System.out.println("Player number");
            int nPlayers = s.nextLine();
            if(nPlayers > 2) {
                break;
            }
            System.out.println("Invalid player number, ensure more than two players."); 
        }

        while(True) {
            System.out.println("Pack Location");
            String packLocation = s.nextLine();
            try {
                File packFile = new File(packLocation);
                Scanner packScanner = new Scanner(packFile);
                while (packScanner.hasNextLine()) {
                    String data = packScanner.nextLine();
                    packList.add(Integer.parseInt(data));
                }
                if(packValidity(packList, nPlayers)) {
                    throw Exception; 
                } 
            } catch (FileNotFoundException e) {
                System.out.println("File could not be found, ensure it is in the current directory");
            } catch (Exception e) {
                System.out.println("Pack format invalid, please fix.");
            }
           
        }
        
        gameSetup();
    
    }
}
