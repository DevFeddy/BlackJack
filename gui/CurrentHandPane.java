package gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Card;
import game.Game;
import game.Hand;
import game.Player;

public class CurrentHandPane extends JPanel{
	
	private Game game;
	private Player player;
	private Hand hand;
	
	private JButton takeCard;
	private JButton stop;
	
	private Card coupierCard;
	private GuiCard guiCoupCard;
	private GuiCard unknownCard;
	
	List<GuiCard> handCards = new ArrayList<>();
	
	MainGui guiClass;
	
	
	
	public CurrentHandPane(MainGui gui, Game game, Rectangle bounds) {
		this.game = game;
		this.player = this.game.getCurrentPlayer();
		this.guiClass = gui;
		this.hand = game.getCurrentPlayer().getCurrentHand();
		this.coupierCard = this.game.getCoupier().getOpenCard();
		
		this.setBackground(Color.yellow);
		
		this.setBounds(bounds);
		
		this.setLayout(null);
		
		setup();
	}
	
	public void setup() {
		this.guiCoupCard = new GuiCard(this.coupierCard, this.getWidth()/2 - 55, 20);
		this.unknownCard = new GuiCard("?", "?", this.getWidth()/2 + 5, 20);
		
		this.add(this.guiCoupCard);
		this.add(this.unknownCard);
		
		//TODO add split & doubleBet
		
		this.takeCard = new JButton("take Card");
		this.takeCard.setBounds(20, this.getHeight() - 50, this.buttonWidth(this.takeCard), 20);
		this.takeCard.addActionListener(a -> {
			this.game.takeCard();
			drawHand(false);
			checkRound();
		});
		
		this.stop = new JButton("stop");
		this.stop.setBounds(this.getWidth() - 20 - this.buttonWidth(this.stop), this.getHeight() - 50, this.buttonWidth(this.stop), 20);
		this.stop.addActionListener(a -> {
			this.game.stop();
			checkRound();
		});
		
		this.add(takeCard);
		this.add(stop);
		
		this.drawHand(false);
		
	}
		
	private void checkRound() {
		if (this.game.getResult() != null) {
			JOptionPane.showOptionDialog(this, "Spiel zuende", "Spiel", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			this.drawHand(true);
			this.remove(takeCard);
			this.remove(stop);
			this.add(new EndPane(this.guiClass, this.game.getResult(), (int)(this.getWidth() * 0.2), GuiCard.HEIGHT + 50, (int) (this.getWidth() * 0.6), this.getHeight() - GuiCard.HEIGHT - 100));
			this.repaint();
			return;
		}
		
		boolean change = !this.hand.equals(this.game.getCurrentPlayer().getCurrentHand());
		if (change) {
			JOptionPane.showOptionDialog(this, "Hand vorbei", "hand", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

		}
		if (!this.player.equals(this.game.getCurrentPlayer())) {
			JOptionPane.showOptionDialog(this, "Spieler vorbei", "Spieler", JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			this.guiClass.afterPlayerRound();
			this.player = this.game.getCurrentPlayer();
		}
		if (change) {
			this.setupNewHand(this.game.getCurrentPlayer().getCurrentHand());
		}
	}
	
	private void drawHand(boolean coupier) {
		if (coupier) {
			this.remove(this.guiCoupCard);
			this.remove(this.unknownCard);
		}
		this.handCards.forEach(this::remove);
		this.handCards = new ArrayList<>();
		
		
		List<Card> cards = this.hand.getCards();
		
		int cardDistance = 5;
		int y = this.getHeight() - 150;
		
		
		if (coupier) {
			y = 20;
			cards = this.game.getCoupier().getHand().getCards();
		}
		
		int cardAmount = cards.size();
		
		for (int i = 0; i < cardAmount; i++) {
			int x = this.getWidth()/2 + (GuiCard.WIDTH + cardDistance) * (i - cardAmount/2);
			

			Card card = cards.get(i);
			
			GuiCard c = new GuiCard(card, x, y);
			this.handCards.add(c);
			this.add(c);
		}
		this.repaint();
	}
	
	public void setupNewHand(Hand hand) {
		this.hand = hand;
		this.drawHand(false);
	}
	
	private int buttonWidth(JButton button) {
		return button.getFontMetrics(button.getFont()).stringWidth(button.getText()) + 50;
	}
	
	
}
