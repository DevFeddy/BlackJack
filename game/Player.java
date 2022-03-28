package game;
import java.util.List;

public class Player
{
    String name;
    List<Hand> hands;
    Wallet wallet;
    boolean finished;
    int currentHandIndex;
    int splits;
    
    public void setBet(int bet) {
        
    }

    public Hand getCurrentHand() {
        return this.hands.get(this.currentHandIndex);
    }
    
    public List<Hand> getHands() {
    	return hands;
    }

    public boolean newHand() {
        return ++this.currentHandIndex < hands.size();
        //return false if currenHandIndex exceeds List of Hand
    }
}
