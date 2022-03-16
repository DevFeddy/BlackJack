/**
 * @author (Jannis)
 */
public class GameSettings{

    boolean fiveCardCharlie;
    boolean allowResplit; 
    boolean threeOfSeven; 
    int maxSplits;
    int maxPlayer;

    /**
     * define with which rules and how many players you wanna play 
     */
    public GameSettings(boolean fiveCardCharlie, boolean allowResplit, boolean threeOfSeven, int maxSplits, int maxPlayer){
        this.fiveCardCharlie = fiveCardCharlie;
        this.allowResplit = allowResplit;
        this.threeOfSeven = threeOfSeven;
        this.maxSplits = maxSplits;
        this.maxPlayer = maxPlayer;
    }
}

