package game;
import java.util.List;

/**
 * @author Jannis
*/
public class Coupier
{
  Hand hand;
  
  /*
   * public Card getClossedCard(){
      Hand.getCards().get(0);
  }
   */
  
  public Coupier() {
	  this.hand = new Hand(0);
  }
  
  public Card getOpenCard(){
      //hand.getCards().get(0);
      return hand.getCards().get(1);
  }

  public Hand getHand() {
    return hand;
  }
  
  /**
   * should add cards from leftover cards to hand till the value is >= 17
   * @param cards the left over cards from the game
   */
  public void action(List<Card> cards){
      while (hand.calculateValue() <= 17){
           Card card = Game.takeCardFromCards(cards);
           hand.addCard(card);  
      }
      this.hand.finish();
     
  }
 
}


