import java.util.concurrent.ThreadLocalRandom;

public class PlayerThread extends Thread {

    protected final Player player;
    private Boolean won = false;

    public PlayerThread(Player player) {
        this.player = player;
    }

    //Player's will continute to draw and discard until they have four of the same type at which point they have won
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

    //Draw a card from a player's draw deck and add it to their hand
    protected void drawCard() {
        Card drawnCard = player.drawDeck.drawFromDeck();
        player.addToHand(drawnCard);
        int[] args = {drawnCard.getValue(), player.drawDeck.name};
        Logger.logDraw(player, args);
    }

    //Discard a card from the player's hand and add it to their discard deck
    protected void discardCard() {
        Card discardedCard = player.hand.get(getRandomIndex());
        player.removeFromHand(discardedCard);
        player.discardDeck.addToDeck(discardedCard);
        int[] args = {discardedCard.getValue(), player.discardDeck.name};
        Logger.logDiscard(player, args);
    }

    //Log the player's current hand after each draw and discard
    protected void readCurrentHand() {
        int[] args = player.readHand();
        Logger.logCurrent(player, args);
    }

    //Returns a random index to remove a card that isn't the player's preferred denomination
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

    //Checks to see if the player has won the game (has four of the same cards)
    protected boolean checkHandState() {
        int firstCardValue = player.hand.getFirst().getValue();
        return player.hand.stream().allMatch(card -> card.getValue() == firstCardValue);
    }
}