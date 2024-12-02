import java.util.Collections;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class PlayerThread extends Thread {

    private Player player;
    private Boolean won = false;

    public PlayerThread(Player player) {
        this.player = player;
    }

    public void run() {
        while(!this.won) {

            Card drawnCard = player.drawDeck.drawFromDeck();
            player.addToHand(drawnCard);
            int[] args = {drawnCard.getValue(), player.drawDeck.name};
            player.log("draw", args);

            Random rand = new Random();
            int index;
            while(true) {
                index = rand.nextInt(5);
                if(player.hand.get(index).getValue() != player.denomination) {
                    break;
                }
            }
            
            Card discardedCard = player.hand.get(index);
            player.removeFromHand(discardedCard);
            int[] args2 = {discardedCard.getValue(), player.discardDeck.name};
            player.log("discard", args2);
            player.discardDeck.addToDeck(discardedCard);

            int[] args3 = player.readHand();
            player.log("current", args3);

            Set<Card> set = new HashSet<Card>(this.player.hand);
            if(set.size() == 1) {
                won = true;
            }
        }

    }
}