package game;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Frederik
 */
public class Card {
    
    private CardValue cardValue; 
    private String color;
    
    static String[] colors = new String[] {"Herz", "Pick", "Kreuz", "Karo"};
    
    
    public Card(CardValue cv, String color) {
        this.color = color;
        this.cardValue = cv;
    }
    
    public static List<Card> generateCardDeck() {
        List<Card> cards = new ArrayList<>();
        for (String color: colors) {
            for (CardValue cv: CardValue.values()) {
                cards.add(new Card(cv, color));
            }
        }
        
        return cards;
    }

    public CardValue getCardValue() {
        return cardValue; 
    }
    
    public String getColor() {
        return color;
    }
    
    @Override
    public String toString() {
    	return String.format("%s (%s)", this.cardValue.getName(), this.getColor());
    }
    
    public enum CardValue {
        Ass("Ass", 11), 
        Bube("Bube", 10), 
        Dame("Dame", 10), 
        Koenig("König", 10),
        K10("10", 10),
        K9("9", 9),
        K8("8", 8),
        K7("7", 7),
        K6("6", 6),
        K5("5", 5),
        K4("4", 4),
        K3("3", 3),
        K2("2", 2);
        
        
        private int value;
        private String name;
        CardValue(String name, int value) {
            this.value = value;
            this.name = name;
        }
        
        public int getValue() {
            return value;
        }
        
        public String getName() {
            return name;
        }
    }
}
