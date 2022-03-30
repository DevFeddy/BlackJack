package gui;

import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import game.Game;
import game.GameSettings;
import game.Player;

public class MainGui extends JFrame {
	
	TopBar topBar;
	
	CurrentHandPane handPane;
	
	Game game;
	
	public MainGui(int width, int height, String title) {
		super(title);
		
		this.setSize(width, height);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false); 
		
		this.setLayout(null);
		
		firstSetup();
		
		this.setVisible(true);
	}
	
	private void setupGui() {
		
		this.topBar = new TopBar(this.game.getCurrentPlayer().getName(), this.game.getCurrentPlayer().getWallet().getcurrentAmount(),
				new Rectangle(0, 0, this.getWidth(), 40));
		
		this.handPane = new CurrentHandPane(this, this.game, new Rectangle(0, 40, this.getWidth(), this.getHeight() - 70));
				
		this.add(this.handPane);
		this.add(this.topBar);
		
		this.repaint();
		
	}
	
	private void firstSetup() {
		Player[] players = this.getPlayers();
		
		this.game = new Game (GameSettings.defaultSettings(), players);
		
		betRound();
		
		this.game.setup();
		
		setupGui();
	}
	
	private Player[] getPlayers() {
		String s;
		
		do {
			s = JOptionPane.showInputDialog(this, "Type in the amount of players (as pos Integer)");
			
		} while (!(checkInt(s)));
		
		int playerAmount = Integer.parseInt(s);
		Player[] player = new Player[playerAmount];
		for (int i = 0; i < playerAmount; i++) {
			String name = JOptionPane.showInputDialog(this, "Name of Player " + (i + 1));
			if (name == null || name.isEmpty()) {
				System.exit(0);
			}
			player[i] = new Player(name);
		}
		return player;
	}
	
	private void betRound()  {
		for (Player p : this.game.getPlayers()) {
			String s;
			int bet = 0;
			while (!this.game.setBet(p, bet)) {
				do {
					s = JOptionPane.showInputDialog(this, p.getName() + " please type your bet (Current amount: " + p.getWallet().getcurrentAmount() + ")");
				} while (!(checkInt(s)));
				bet = Integer.parseInt(s);
			}
		}
	}
	
	public void afterPlayerRound() {
		this.topBar.change(this.game.getCurrentPlayer().getName(), this.game.getCurrentPlayer().getWallet().getcurrentAmount());
		this.topBar.repaint();
	}
	
	public void newGame(Player... newPlayers) {
		Player[] players = this.game.getPlayers();
		players = Arrays.stream(players)
				.filter(p -> p.getWallet().getcurrentAmount() > 0)
				.map(Player::prepareForNewGame)
				.toArray(Player[]::new);
		if (players.length == 0) {
			System.exit(0);
		}
		
		this.game = new Game(this.game.getGameSettings(), players);
		
		betRound();
		
		this.game.setup();
		
		this.remove(this.handPane);
		this.remove(this.topBar);
		
		this.setupGui();
	}
	
	private boolean checkInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}	
	

}
