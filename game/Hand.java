package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Frederik
 */
public class Hand
{
    List<Card> cards;
    boolean splitted;
    int bet;
    private boolean finished;
    private int cardValue;
    
    public Hand(int bet) {
    	this.cards = new ArrayList<>();
    	this.bet = bet;
    	this.splitted = false;
    	this.finished = false;
    }
    
    public Hand(List<Card> cards, int bet, boolean splitted, boolean finished) {
    	this.cards = cards; 
    	this.bet = bet;
    	this.splitted = splitted;
    	this.finished = finished;
    }

    public Hand split() {
        
    	// checks if the card values of the Cards are the same 
    	if (this.cards.stream().map(card -> card.getCardValue()).collect(Collectors.toSet()).size() == 1
    			&& this.cards.size() == 2) { // max 2 cards allowed in hand in order to split
    				return new Hand(Arrays.asList(cards.get(0)), this.bet, true, false);
		} else {
			return null;
		}
    }

    public int getBet() {
        return bet;
    }

    public void finish() {
    	this.cardValue = this.calculateValue();
    	this.finished = true;
    }
    
    public int calculateValue() {
      if (finished) {
        return this.cardValue;
      }
         List<Card.CardValue> cardValues = cards.stream().map(card -> card.getCardValue()).collect(Collectors.toList());
         
         int simpleValue = cardValues.stream().mapToInt(cv -> cv.getValue()).sum();
         if (simpleValue <= 21) {
        	 return simpleValue;
         }
         
         int aceAmount = (int) cardValues.stream().filter(cv -> cv.equals(Card.CardValue.Ass)).count();
         
         while (aceAmount > 0 && simpleValue > 21) {
           simpleValue -= 10;
           aceAmount--;
         }
       
         
         return simpleValue;
    }

    public void addCard(Card card) {
      this.cards.add(card);
    }
    
    public List<Card> getCards() {
    	return cards;
    }
}
