package gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopBar extends JPanel {
	
	String name;
	int walletAmount;
	
	JLabel nameLabel;
	JLabel amountLabel;
	
	public TopBar(String name, int walletAmount, Rectangle rect) {
		this.setBounds(rect);

		this.nameLabel = new JLabel();
		this.amountLabel = new JLabel();
		
		this.amountLabel.setForeground(Color.white);
		this.nameLabel.setForeground(Color.white);
		
		this.setBackground(Color.decode("#333333"));
		
		
		this.change(name, walletAmount);
		this.add(nameLabel);
		this.add(amountLabel);
		this.repaint();
	}
	
	public void change(String name, int walletAmount) {
		this.name = name; 
		this.walletAmount = walletAmount;
		
		this.nameLabel.setText(name);
		this.amountLabel.setText(Integer.toString(walletAmount));
		
		
		
		setMiddleBounds();
		this.amountLabel.setBounds(10, 10, 100, 20);
		this.repaint();
	}
	
	private void setMiddleBounds() {
		FontMetrics metrics = this.nameLabel.getFontMetrics(this.nameLabel.getFont());
		int x = this.getWidth() / 2; 
		
		int width = metrics.stringWidth(this.nameLabel.getText());
		if (width > 100) width = 100;
		int offsetX = width / 2;
		
		this.nameLabel.setBounds(x - offsetX, 10, width, 20);
	}
}
