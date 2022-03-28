/**
 * @revox
 */

public class Wallet
{
    int coins;
    Player betvalue;
    public void addCoins(int coinsToAdd){
        this.coins = this.coins + coinsToAdd;
    }

    public void removeCoins(coinsToRemove){
       this.coins = thiscoins - coinsToRemove;
    }
    public int getcurrentWallet(){
    return this.coins;
    }
}

