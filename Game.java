import java.util.List;
import java.util.Random;

/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Game
{
    List<Card> cards;
    Player[] players;
    int currentPlayerIndex;

    boolean betRound;
    Coupier coupier;

    /**
     * sets Bets of the different players
     */
    public void setBet(Player player, int bet) {

    }

    /**
     * starts with the distribution of the cards and ends the betting phase
     */
    public void setup() {

    }

    public Card takeCard() {

    }

    /**
     * terminates the round of one hand
     */
    public void stop() {

    }

    /**
     * splits the hand if possible
     */
    public boolean split() {

    }

    /**
     * lets the whole player surrender
     */
    public void surrender() {

    }

    /**
     * doubles the bet of one hand
     */
    public void doubleBet() {

    }

    /**
     * called when a player is finished and it's the next players round
     */
    private void nextPlayer() {

    }

    /**
     * called on the end of the game and calls the coupier
     */
    private void onEnd() {

    }

    /**
     * @author Frederik
     */
    public static Card takeCardFromCards(List<Card> cards) {
        Random r = new Random();
        //TODO check if range of r is not maybe a bit to large
        int random = r.nextInt(cards.size());
        Card card = cards.get(random);
        cards.remove(random);
        return card;
    }
}
