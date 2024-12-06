import java.util.concurrent.ThreadLocalRandom;

public class PlayerThread extends Thread {

    private Player player;
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

    private void drawCard() {
        Card drawnCard = player.drawDeck.drawFromDeck();
        player.addToHand(drawnCard);
        int[] args = {drawnCard.getValue(), player.drawDeck.name};
        Logger.logDraw(player, args);
    }

    private void discardCard() {
        Card discardedCard = player.hand.get(getRandomIndex());
        player.removeFromHand(discardedCard);
        int[] args = {discardedCard.getValue(), player.discardDeck.name};
        Logger.logDiscard(player, args);
        player.discardDeck.addToDeck(discardedCard);
    }

    private void readCurrentHand() {
        int[] args = player.readHand();
        Logger.logCurrent(player, args);
    }

    private int getRandomIndex() {
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

    private boolean checkHandState() {
        int firstCardValue = player.hand.getFirst().getValue();
        return player.hand.stream().allMatch(card -> card.getValue() == firstCardValue);
    }
}