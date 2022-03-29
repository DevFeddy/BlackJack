package gui;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import game.Game;
import game.Player;

public class MainGui extends JFrame {
	
	OtherPlayerPanel nextPlayers;
	OtherPlayerPanel playedPlayers;
	JTextField currentPlayer;
	JPanel topBar;
	
	Game game;
	
	public MainGui(int width, int height, String title) {
		super(title);
		
		this.setSize(width, height);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false); 
		
		this.setLayout(null);
		
		setup();
		
		this.setVisible(true);
	}
	
	private void setup() {
		
	}
	
	private void afterPlayerRound() {
		Player[] playedPlayers = Arrays.copyOfRange(this.game.getPlayers(), 0, this.game.getPlayerIndex());
		Player[] nextPlayers = Arrays.copyOfRange(this.game.getPlayers(), this.game.getPlayerIndex() + 1, this.game.getPlayers().length);
	}
	
	private void onEnd() {
		int chosenOption = JOptionPane.showOptionDialog(this, "What do you want to do now", "And now?", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"continue with all players", "continue with new players"}, null);	
		System.out.println(chosenOption);
		if (chosenOption == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
	}
	
	

}
