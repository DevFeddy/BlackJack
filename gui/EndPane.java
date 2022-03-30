package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game.PlayerEndInfo;
import game.Player;

public class EndPane extends JPanel{
	
	
	JButton startNewGame;
	MainGui mainGui;
	
	
	public EndPane(MainGui mainGui, Map<Player, PlayerEndInfo> endInfos, int x, int y, int width, int height) {
		this.mainGui = mainGui;
		this.setBounds(x, y, width, height);
		int x1 = 20;
		int x2 = width - 150;
		this.setBackground(Color.red);
		
		int yVar = 20;
		this.add(this.getLabel("names", x1, yVar));
		this.add(this.getLabel("Total bet result", x2, yVar));
		yVar += 30;
		for (Player p: endInfos.keySet()) {
			int amount = endInfos.get(p).getBetResult();
			this.add(this.getLabel(p.getName(), x1, yVar));
			this.add(this.getLabel(Integer.toString(amount), x2, yVar));
			yVar += 30;
		}
		
		this.startNewGame = new JButton("Start new Game");
		int size = getSize(startNewGame.getText(), startNewGame) + 30;
		this.startNewGame.setBounds((width - size) / 2, this.getHeight() - 50, size, 25);
		this.startNewGame.addActionListener(a -> {
			this.mainGui.newGame();
		});
		
		this.add(this.startNewGame);
	}
	
	private JLabel getLabel(String text, int x, int y) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, 100, 20);
		return label;
	}
	
	private int getSize(String text, Component component) {
		FontMetrics metrics = component.getFontMetrics(component.getFont());
		return metrics.stringWidth(text);
	}
}
