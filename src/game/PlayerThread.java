import java.sql.SQLOutput;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerThread extends Thread {

    protected final Player player;
    private Boolean won = false;

    public PlayerThread(Player player) {
        this.player = player;
    }

    public void run() {
    
        try {
            while(!this.won) {
                if(checkHandState()) {
                    won = true;
                    CardGame.closeThreads();
                    CardGame.finalLogs(player);
                }
                Thread.sleep(300);
                drawCard();
                discardCard();
                readCurrentHand();
            }
            
        } catch(InterruptedException e) {

        }
        
    }

    protected void drawCard() {
        Card drawnCard = player.drawDeck.drawFromDeck();
        player.addToHand(drawnCard);
        //updatePreference(drawnCard);
        int[] args = {drawnCard.getValue(), player.drawDeck.name};
        Logger.logDraw(player, args);
    }

    protected void discardCard() {
        Card discardedCard = player.hand.get(getRandomIndex());
        player.removeFromHand(discardedCard);
        player.discardDeck.addToDeck(discardedCard);
        int[] args = {discardedCard.getValue(), player.discardDeck.name};
        Logger.logDiscard(player, args);
    }

    protected void readCurrentHand() {
        int[] args = player.readHand();
        Logger.logCurrent(player, args);
    }

    protected int getRandomIndex() {
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        int index;
        while(true) {
            index = rand.nextInt(player.hand.size());
            if(player.hand.get(index).getValue() != player.denomination) {
                break;
            }
        }
        return index;
    }

    protected boolean checkHandState() {
        int firstCardValue = player.hand.getFirst().getValue();
        return player.hand.stream().allMatch(card -> card.getValue() == firstCardValue);
    }
}