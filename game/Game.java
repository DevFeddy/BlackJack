package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The class where all the methods to play the game are
 *
 * @author Frederik
 */
public class Game
{
    List<Card> cards;
    Player[] players;
    int currentPlayerIndex;

    boolean betRound;
    Coupier coupier;
    
    GameSettings gameSettings;
    
    Map<Player, PlayerEndInfo> result = null;

    public Game(GameSettings gameSettings, Player... players) {
    	this.gameSettings = gameSettings;
        this.players = players;
        this.currentPlayerIndex = 0;
        this.coupier = new Coupier();
    }
    
    public GameSettings getGameSettings() {
		return gameSettings;
	}
    
    public Coupier getCoupier() {
		return coupier;
	}

    /**
     * sets Bets of players
     */
    public boolean setBet(Player player, int bet) {
        return player.setBet(bet);
    }


    /**
     * ends the betting phase &
     * starts the distribution of the first two cards to all players and the coupier
     */
    public void setup() {
        this.cards = Card.generateCardDeck();
        for (int i = 0; i < 5; i++) {
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
        
        while(this.getResult() == null && this.getCurrentPlayer().getCurrentHand().calculateValue() >= 21) {
        	this.stop();
        }
    }

    /**
     * <ul>
     * <li>adds a card to the current hand from all cards</li>
     * <li>checks if the card deck is a win by the sum or other variants defined in the {@link GameSettings}</li>
     * </ul>
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
     * <ul>
     * <li>terminates the round of one hand -> increase hand Index in class Player</li>
     * <li>if it's last hand of player -> next Player</li>
     * </ul>
     */
    public void stop() {
    	this.getCurrentPlayer().getCurrentHand().finish();
        if (!this.getCurrentPlayer().newHand()) {
            this.nextPlayer();
        }
    }

    /**
     * 
     * <ul>
     * <li> increases player index </li>
     * <li> if last player -> calls onEnd </li>
     * </ul>
     * called when a player is finished and it's the next players round
     */
    private void nextPlayer() {
    	this.getCurrentPlayer().setFinished(true);
        if (!(++this.currentPlayerIndex < players.length)) {
            this.onEnd();
        }
    }

    /**
     * 
     * <ul>
     * <li>calls the coupier action </li>
     * <li>evaluates the results and bets of the game</li>
     * </ul>
     * called on the end of the game
     */
    private Map<Player, PlayerEndInfo> onEnd() {
        coupier.action(this.cards);

        Map<Player, PlayerEndInfo> infoMap = new HashMap<>();

        for (Player p : players) {
            PlayerEndInfo pInfo = new PlayerEndInfo();
            for (Hand hand : p.getHands()) {
                HandEndInfo hInfo;
                System.out.println("Hand:" + hand.calculateValue());
                System.out.println("Coupier:" + this.coupier.getHand().calculateValue());
                if (hand.calculateValue() <= 21) {
	                if (this.coupier.getHand().calculateValue() > 21 
	                		|| hand.calculateValue() > this.coupier.getHand().calculateValue()) {
	                	hInfo = new HandEndInfo(hand, hand.getBet());
	                } else if (hand.calculateValue() == this.coupier.getHand().calculateValue()) {
	                    hInfo = new HandEndInfo(hand, 0);
	                } else {
	                    hInfo = new HandEndInfo(hand, - hand.getBet());
	                } 
	                
	                if (this.gameSettings.isFiveCardCharlie()) {
	                	if (hand.getCards().size() == 5) {
	                		hInfo = new HandEndInfo(hand, hand.getBet() * 2);
	                	}
	                }
                } else {
                	hInfo = new HandEndInfo(hand, - hand.getBet());
                }
                
                pInfo.addHandInfo(hInfo);
                pInfo.addBetResult(hInfo.getBet());
            }
            infoMap.put(p, pInfo);
            p.getWallet().addCoins(pInfo.getBetResult());
        }
        
        return (this.result = infoMap);
    }


    public Player getCurrentPlayer() {
        return this.players[this.currentPlayerIndex];
    }

    /**
     * chooses random card from list and removes the card from the list
     * @param cards the list of card to draw a card from
     * @return the drawn card
     */
    public static Card takeCardFromCards(List<Card> cards) {
        Random r = new Random();
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
    
    
    
    
    /**
     * contains a list of {@link HandEndInfo} for the player & the total Bet-Result of player
     *
     */
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

    /**
     * contains betResult and the {@link Hand}
     */
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
