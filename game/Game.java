package game;
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

    public Game(Player... players) {
        this.players = players;
        this.currentPlayerIndex = 0;
        this.coupier = new Coupier();
    }

    /**
     * sets Bets of the different players
     */
    public void setBet(Player player, int bet) {
        player.setBet(bet);
    }

    /**
     * ends the betting phase
     * starts the distribution of the first two cards two all players and the coupier
     */
    public void setup() {
        this.cards = Card.generateCardDeck();
        for (int i = 0; i < 6; i++) { //TODO 7 Decks?
            this.cards.addAll(Card.generateCardDeck());
        }
    }

    /**
     * adds a card to the current hand from all cards (var cards in this class)
     * checks if the card deck is a win by the sum or other variants defined in the GameSettings
     * @return the taken card
     */
    public Card takeCard() {
        Card card = Game.takeCardFromCards(this.cards);
        this.getCurrentPlayer().getCurrentHand().addCard(card);
        return card;
    }

    /**
     * terminates the round of one hand -> increase hand Index in class Player
     * if it's last hand of player -> next Player
     */
    public void stop() {
        if (!this.getCurrentPlayer().newHand()) {
            this.nextPlayer();
        }
    }

    /**
     * splits the hand if possible
     * passes the split into the current hand class
     */
    public boolean split() {
        //TODO
        return false;
    }

    /**
     * lets the whole player surrender
     * skip round to the next player
     * half the bet of his hand
     * only possible before any card is drawn (hand unsplit and two cards in it)
     */
    public boolean surrender() {
        //TODO
        return false;
    }

    /**
     * doubles the bet of one hand
     */
    public boolean doubleBet() {
        //TODO
        return false;
    }

    /**
     * called when a player is finished and it's the next players round
     * increases player index 
     * if last player -> calls onEnd
     */
    private void nextPlayer() {
        if (!(++this.currentPlayerIndex < players.length)) {
            this.onEnd();
        }
    }

    /**
     * called on the end of the game 
     * calls the coupier
     * evaluates the results and bets of the game
     */
    private void onEnd() {
        
    }


    public Player getCurrentPlayer() {
        return this.players[this.currentPlayerIndex];
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
    
    public Player[] getPlayers() {
    	return this.players;
    }
    
    public int getPlayerIndex() {
    	return this.currentPlayerIndex;
    }
}
