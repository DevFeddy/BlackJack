import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.Card;
import game.Game;
import game.Game.PlayerEndInfo;
import gui.MainGui;
import game.GameSettings;
import game.Player;

public class MainClass {
	
	public static void main(String[] args) {
		if (args.length > 0) {
			new MainClass().start();
		} else {
			new MainGui(800, 600, "BlackJack");
		}
	}
	
	Scanner scanner;
	
	Game game;

	public MainClass() {
		this.scanner = new Scanner(System.in);
		System.out.println("how many players are playing?");
		int playerNumber = checkInt();
		
		Player[] players = new Player[playerNumber];

		
		for (int i = 0; i < players.length; i++) {
			System.out.println(String.format("Player %d please provide your name", i+1));
			String name = scanner.nextLine();
			players[i] = new Player(name);
		}
		
		this.bettingPhase(players);
		
		this.game = new Game(GameSettings.defaultSettings(), players);
	}
	
	
	public void bettingPhase(Player[] players) {
		for (Player p : players) {
			System.out.println("Set your Bet " + p.getName());
			int bet = checkInt();
			while (!p.setBet(bet)) {
				System.out.println("You bet must not exceed your current coin amount of " + p.getWallet().getcurrentAmount());
				bet = checkInt();
			}
		}
	}
	
	/**
	 * starts the game after players are defined and betting phase is over
	 */
	public void start() {
		this.game.setup();
		System.out.println("The cards:\n");
		
		for (Player p : this.game.getPlayers()) {
			List<Card> cards = p.getCurrentHand().getCards();
			System.out.println(String.format("%s --> %s (%s)", p.getName(), cards.get(0).getCardValue().getName(), cards.get(0).getColor()));
			System.out.println(String.format("%s --> %s (%s)", p.getName(), cards.get(1).getCardValue().getName(), cards.get(1).getColor()));
			System.out.println();
		}
		
		System.out.println("First card of the coupier:");
		
		Card coupierCard = this.game.getCoupier().getOpenCard();
		System.out.println(String.format("%s (%s)\n\n\n", coupierCard.getCardValue().getName(), coupierCard.getColor()));
	
		for (Player p : this.game.getPlayers()) {
			this.playerRound(p);
		}
		
		printEnd();
	}
	
	private void playerRound(Player player) {
		System.out.println("It's the round of " + player.getName());
		System.out.println("You're cards were: ");
		List<Card> cards = player.getCurrentHand().getCards();
		System.out.println(cards.get(0) + " & " + cards.get(1));
		
		
		while (!player.isFinished()) {
			System.out.println("Do you want to pick another card? y/n");
			switch (this.scanner.nextLine().toLowerCase()) {
			case "y":
				System.out.println(this.game.takeCard());
				break;
			case "n":
				this.game.stop();
				break;
			default:
				System.out.println("You have to type 'y' or 'n' for yes or no");
			}
		}
	}
	
	private void printEnd() {
		System.out.println("The cards of the coupier are:");
		this.game.getCoupier().getHand().getCards().forEach(System.out::println);
		System.out.println("");
		Map<Player, PlayerEndInfo> result = this.game.getResult();
		for (Player p : result.keySet()) {
			System.out.println("Player " + p.getName() + ":");
			p.getHands().get(0).getCards().forEach(System.out::print);
			int betResult = result.get(p).getBetResult();
			System.out.println("\nso your bet-result is: --> " + betResult + " \nAnd therfore you");
			
			if (betResult > 0) {
				System.out.println("WON");
			} else if (betResult < 0) {
				System.out.println("LOST");
			} else {
				System.out.println("'ve reached a DRAW");
			}
			System.out.println();
		}
		
		for (Player p : this.game.getPlayers()) {
			System.out.println(String.format("%s: %d coins", p.getName(), p.getWallet().getcurrentAmount()));
		}
		
		System.out.println("Play another round with all players? y/n");
		switch(scanner.nextLine().toLowerCase()) {
		case "y": 
			Player[] players = Arrays.stream(this.game.getPlayers()).map(Player::prepareForNewGame).toArray(Player[]::new);
			this.bettingPhase(players);
			this.game = new Game(this.game.getGameSettings(), players);
			this.start();
			break;
		case "n":
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int checkInt() {
		int res = 0;
		while (res == 0) {
			int i;
			
		try {
		i =  Integer.parseInt(this.scanner.nextLine());
		} catch (NumberFormatException ex) {
			System.out.println("Please provide an Integer");
			continue;
		}
		if (i <= 0) {
			System.out.println("Please provide a positive number");
			continue;
		}
		res = i;
		}
		return res;
	}


}
