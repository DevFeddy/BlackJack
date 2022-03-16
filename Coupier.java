import java.util.List;

/**
 * @author ()
*/
public class Coupier
{
  Hand hand;
  
  /*
   * public Card getClossedCard(){
      Hand.getCards().get(0);
  }
   */
  
  public Card getOpenCard(){
      //hand.getCards().get(0);
      return hand.getCards().get(1);
  }
  
  public void action(List<Card> cards){
      if (hand.calculateValue() <= 17){
           Card.takeCardFromCards(List<Card> cards);
        }
  }
 
}


