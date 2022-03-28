package gui;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JScrollPane;

import game.Hand;
import game.Player;

public class OtherPlayerPanel extends JScrollPane {
	
	Player[] players;
	List<Hand> hands;
	
	public OtherPlayerPanel(Player[] players) {
		this.players = players;
		
		setup();
	}
	
	private void setup() {
		this.hands = Arrays.stream(players)
			.flatMap(p -> p.getHands().stream())
			.collect(Collectors.toList());
		
		int roomBetweenCards = 3;
		
		int cardH = this.getHeight() / hands.size() - roomBetweenCards;
		int cardW = this.getWidth();
		
		for (int i = 0; i < hands.size(); i++) {
			Hand hand = hands.get(i);
			
			// Displayed as cards with value in the middle, cardW = width of pane, 
			//    cardH = height that all fit, x = 0, y = i * cardH -> jede karte wird untereinander angeordnet
			// 
			OtherHandPane ohp = new OtherHandPane(hand, cardW, cardH, 0, i * (cardH + roomBetweenCards));
			
			this.add(ohp);
		}
	}

}
