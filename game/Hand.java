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
    int bet;
    private boolean finished;
    private int cardValue;
    
    public Hand(int bet) {
    	this.cards = new ArrayList<>();
    	this.bet = bet;
    	this.finished = false;
    }
    
    public Hand(List<Card> cards, int bet, boolean finished) {
    	this.cards = cards; 
    	this.bet = bet;
    	this.finished = finished;
    }

    public int getBet() {
        return bet;
    }

    /**
     * set the handstate to finished
     */
    public void finish() {
    	this.cardValue = this.calculateValue();
    	this.finished = true;
    }
    
    /**
     * calculates the value of the cards contained by the hand <br>
     * if hand is finished, the last result is taken (nothing new is calculated then)
     * @return the value
     */
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
