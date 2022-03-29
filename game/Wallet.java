package game;
/**
 * @Benjamin
 */

public class Wallet
{
    int coins;
    
    public Wallet() {
    	this.coins = 100;
    }
    
    public void addCoins(int coinsToAdd){
        this.coins = this.coins + coinsToAdd;
    }

    public void removeCoins(int coinsToRemove){
       this.coins = this.coins - coinsToRemove;
    }
    public int getcurrentAmount(){
    return this.coins;
    }
}

