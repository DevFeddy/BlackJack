package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Write a description of class Game here.
 *
 * @author Frederik
 * @version (a version number or a date)
 */
public class Game
{
    List<Card> cards;
    Player[] players;
    int currentPlayerIndex;

    boolean betRound;
    Coupier coupier;
    
    GameSettings gameSettings;
    
    Map<Player, PlayerEndInfo> result;

    public Game(GameSettings gameSettings, Player... players) {
    	this.gameSettings = gameSettings;
        this.players = players;
        this.currentPlayerIndex = 0;
        this.coupier = new Coupier();
    }
    
    public Coupier getCoupier() {
		return coupier;
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
        
        for (Player p : players) {
        	Hand hand = new Hand(p.getInitialBet());
        	p.getHands().add(hand);
        	for (int i = 0; i < 2; i++) {
	        	hand.addCard(takeCardFromCards(this.cards));
        	}
        }
        for (int i = 0; i < 2; i++) {
        	this.coupier.getHand().addCard(takeCardFromCards(this.cards));
        }
        
        while(this.getCurrentPlayer().getCurrentHand().calculateValue() >= 21) {
        	this.stop();
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
        
        //check for win
        if (this.getCurrentPlayer().getCurrentHand().calculateValue() >= 21) {
        	this.stop();
        } else if (this.gameSettings.isFiveCardCharlie() 
        		&& this.getCurrentPlayer().getCurrentHand().getCards().size() == 5) {
        	this.stop();
        }
        
        //TODO three of seven
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
    	this.getCurrentPlayer().setFinished(true);
        if (!(++this.currentPlayerIndex < players.length)) {
            this.onEnd();
        }
    }

    /**
     * called on the end of the game 
     * calls the coupier
     * evaluates the results and bets of the game
     */
    private Map<Player, PlayerEndInfo> onEnd() {
        coupier.action(this.cards);

        Map<Player, PlayerEndInfo> infoMap = new HashMap<>();

        for (Player p : players) {
            PlayerEndInfo pInfo = new PlayerEndInfo();
            for (Hand hand : p.getHands()) {
                HandEndInfo hInfo;
                if (this.coupier.getHand().calculateValue() > 21 ||
                		(hand.calculateValue() > this.coupier.getHand().calculateValue()
                				&& hand.calculateValue() <= 21)) {
                    hInfo = new HandEndInfo(hand, hand.getBet());
                } else if (hand.calculateValue() == this.coupier.getHand().calculateValue()) {
                    hInfo = new HandEndInfo(hand, 0);
                } else {
                    hInfo = new HandEndInfo(hand, - hand.getBet());
                } //TODO check for fivecard-charlie
                pInfo.addHandInfo(hInfo);
                pInfo.addBetResult(hInfo.getBet());
            }
            infoMap.put(p, pInfo);
        }

        //TODO wallet handling
        return (this.result = infoMap);
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
    
    public Map<Player, PlayerEndInfo> getResult() {
		return result;
	}
    
    
    
    

    public static class PlayerEndInfo {
        public int betResult;

        public List<HandEndInfo> handsInfo = new ArrayList<>();

        public void addHandInfo(HandEndInfo hInfo) {
            this.handsInfo.add(hInfo);
        }

        public void addBetResult(int value) {
            this.betResult += value;
        }

        public int getBetResult() {
            return this.betResult;
        }

        public List<HandEndInfo> getHandInfo() {
            return this.handsInfo;
        }
    }

    public static class HandEndInfo {
        public int bet;
        private Hand hand;

        public HandEndInfo(Hand hand, int bet) {
            this.bet = bet;
        }

        public int getBet() {
            return bet;
        }
        
        public Hand getHand() {
			return hand;
		}
    }
}
