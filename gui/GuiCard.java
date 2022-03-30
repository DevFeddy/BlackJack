package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import game.Card;

public class GuiCard extends JPanel{
	
	private String cardName;
	private String color;
	
	public static final int HEIGHT = 70; 
	public static final int WIDTH = 50;
	
	
	
	
	public GuiCard(Card card, int x, int y) {
		this(card.getCardValue().getName(), card.getColor(), x, y);
	}
	
	public GuiCard(String name, String color, int x, int y) {
		this.cardName = name;
		this.color = color;
		this.setBounds(x, y, WIDTH + 7, HEIGHT + 5);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, 10, 10);
		
		g.setColor(Color.black);
		g.drawRoundRect(0, 0, WIDTH, HEIGHT, 10, 10);
		
		FontMetrics fontMetrics = g.getFontMetrics();
		
		int x = (this.getWidth() - fontMetrics.stringWidth(cardName)) / 2 - 3;		
		g.drawString(cardName, x, 25);
		
		x = (this.getWidth() - fontMetrics.stringWidth(color)) / 2 - 3;
		g.drawString(color, x, 40);
				
	}

}
