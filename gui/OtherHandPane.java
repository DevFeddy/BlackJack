package gui;

import java.awt.Canvas;
import java.awt.Graphics;

import game.Hand;

public class OtherHandPane extends Canvas {
	
	Hand hand;
	int width;
	int height;
	
	
	public OtherHandPane(Hand hand, int width, int height, int x, int y) {
		this.setBounds(x, y, width, height);
		this.hand = hand;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void paint(Graphics g) {
		
		g.drawRoundRect(0, 0, width, height, 10, 10);
		g.drawString(Integer.toString(this.hand.calculateValue()), (int) width/2, (int) height/2);
		
		super.paint(g);
	}

}
