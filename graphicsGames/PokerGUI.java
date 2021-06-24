/**
 * 
 */
package graphicsGames;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import sideClasses.*;

/**
 * @author AndrewLiu12
 *
 */
public class PokerGUI extends JFrame implements ActionListener {

	/**
	 * @param args
	 */
	private static Vector<Player> players = new Vector<Player>();
	private static Vector<Integer> removeCards = new Vector<Integer>();
	private static Vector<JPanel> cardPanels = new Vector<JPanel>();
	private static String name;
	private static int player = 0;
	private static int limit = 0;
	private static Dealer dealer = new Dealer();
	private static JPanel GenPan = new JPanel();
	private static JPanel DisplayCards = new JPanel();
	private static JPanel bottomPanel = new JPanel();
	private static JLabel input = new JLabel();
	private static JLabel picture = new JLabel();
	private static JButton swap1 = new JButton();
	private static JButton swap2 = new JButton();
	private static JButton swap3 = new JButton();
	private static JButton swapNone = new JButton();
	private static JButton restart = new JButton();
	private static JTextField messageBox = new JTextField(25);
	private static JTextField cardBox = new JTextField(1);
	private static ImageIcon back = new ImageIcon(System.getProperty("user.dir") + "/cards/back1.gif");
	
	//Done
	public static void main(String[] args) {
		PokerGUI frame = new PokerGUI(); // create instance of frame with panel
		frame.setTitle("Poker"); // set window title
		frame.setSize(850, 750); // set size of window
		frame.setLocationRelativeTo(null); // center the frame New JDK 1.5
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set corner X to close window
		frame.setVisible(true); // make window show up
	}
	
	public PokerGUI() {
		GenPan.setLayout(new BorderLayout());
		picture.setIcon(back);
		picture.setHorizontalAlignment(SwingConstants.CENTER);
		GenPan.add(picture, BorderLayout.CENTER);
		//Add components outside of paint component
		//Doesn't work as intended otherwise
		input.setForeground(Color.RED);
		input.setText("Enter a name (enter 0 to stop): ");
		input.setFont(new Font("Courier", Font.BOLD, 20));
		messageBox.setFont(new Font("Courier", Font.BOLD, 20));
		cardBox.setFont(new Font("Courier", Font.BOLD, 20));
		bottomPanel.add(input, BorderLayout.WEST);
		bottomPanel.add(messageBox, BorderLayout.CENTER);
		GenPan.add(bottomPanel, BorderLayout.SOUTH);
		messageBox.setHorizontalAlignment(SwingConstants.RIGHT);
		messageBox.addActionListener(this);
		cardBox.addActionListener(this);
		swap1.addActionListener(this);
		swap2.addActionListener(this);
		swap3.addActionListener(this);
		swapNone.addActionListener(this);
		restart.addActionListener(this);
		players.add(new Player("House", 5, dealer.deck));
		GenPan.setBackground(Color.GREEN);
		bottomPanel.setBackground(Color.GREEN);
		add(GenPan);
	}

	public void displayCards() {
		player++;
		bottomPanel.removeAll();
		DisplayCards.removeAll();
		bottomPanel.setLayout(new FlowLayout());
		GenPan.remove(bottomPanel);
		String path = "";
		ImageIcon card = new ImageIcon();
		GenPan.setLayout(new BorderLayout());
		DisplayCards.setLayout(new GridLayout(players.size(), 2, 5, 5));
		DisplayCards.setBackground(Color.GREEN);
		for (int i = 0; i < players.size(); i++) {
			JLabel playername = new JLabel();
			playername.setFont(new Font("Times New Roman", Font.BOLD, 20));
			playername.setForeground(Color.RED);
			playername.setText(players.get(i).Name + "'s Hand: ");
			DisplayCards.add(playername);
			for (int k = 0; k < players.get(0).handsize; k++) {
				JLabel picture = new JLabel();
				path = System.getProperty("user.dir") + "/cards/";
				path += players.get(i).PlayerHand.get(k).Value.substring(0, players.get(i).PlayerHand.get(k).Value.indexOf(" ")) + 
						players.get(i).PlayerHand.get(k).Suit.substring(1) + ".gif";
				card = new ImageIcon(path);
				picture.setIcon(card);
				DisplayCards.add(picture);
			}
		}
		if (player == players.size()) {
			dealerTurn();
			declareWinner();
		}
		else {
			GenPan.add(DisplayCards, BorderLayout.NORTH);
			input.setForeground(Color.RED);
			input.setText(players.get(player).Name + " Hand's: ");
			bottomPanel.add(input);
			swapNone.setFont(new Font("Times New Roman", Font.BOLD, 15));
			swapNone.setText("Don't Swap");
			swap1.setFont(new Font("Times New Roman", Font.BOLD, 15));
			swap1.setText("Swap 1 Card");
			swap2.setFont(new Font("Times New Roman", Font.BOLD, 15));
			swap2.setText("Swap 2 Cards");
			swap3.setFont(new Font("Times New Roman", Font.BOLD, 15));
			swap3.setText("Swap 3 Cards");
			bottomPanel.add(swapNone);
			bottomPanel.add(swap1);
			bottomPanel.add(swap2);
			bottomPanel.add(swap3);
			GenPan.add(bottomPanel, BorderLayout.SOUTH);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		remove(GenPan);
		if (e.getSource() == messageBox) {
			name = messageBox.getText();
			messageBox.setText("");
			if (name.equals("0")) {
				GenPan.removeAll();
				cardPanels.setSize(players.size());
				displayCards();
			}
			else {
				players.add(new Player(name, 5, dealer.deck));
				if (players.size() == 6) {
					GenPan.removeAll();
					cardPanels.setSize(players.size()); 
					displayCards();
				}
			}
		}
		else if (e.getSource() == swapNone) {
			displayCards();
		}
		else if (e.getSource() == swap1) {
			limit = 1;
			enterPos();
		}
		else if (e.getSource() == swap2) {
			limit = 2;
			enterPos();
		}
		else if (e.getSource() == swap3) {
			limit = 3;
			enterPos();
		}
		else if (e.getSource() == cardBox) {
			name = cardBox.getText();
			cardBox.setText("");
			if (name.equals("1"))
				removeCards.add(1);
			else if (name.equals("2"))
				removeCards.add(2);
			else if (name.equals("3"))
				removeCards.add(3);
			else if (name.equals("4"))
				removeCards.add(4);
			else if (name.equals("5"))
				removeCards.add(5);
			if (removeCards.size() == limit) {
				swapCards();
				displayCards();
			}
		}
		else if (e.getSource() == restart) {
			dealer = new Dealer();
			player = 0;
			players.removeAllElements();
			bottomPanel.remove(restart);
			GenPan.removeAll();
			GenPan.setLayout(new BorderLayout());
			DisplayCards.removeAll();
			picture.setIcon(back);
			picture.setHorizontalAlignment(SwingConstants.CENTER);
			GenPan.add(picture, BorderLayout.CENTER);
			bottomPanel.removeAll();
			//Add components outside of paint component
			//Doesn't work as intended otherwise
			input.setText("Enter a name (enter 0 to stop): ");
			input.setFont(new Font("Courier", Font.BOLD, 20));
			messageBox.setFont(new Font("Courier", Font.BOLD, 20));
			cardBox.setFont(new Font("Courier", Font.BOLD, 20));
			bottomPanel.add(input, BorderLayout.WEST);
			bottomPanel.add(messageBox, BorderLayout.CENTER);
			messageBox.setHorizontalAlignment(SwingConstants.RIGHT);
			GenPan.add(bottomPanel, BorderLayout.SOUTH);
			players.add(new Player("House", 5, dealer.deck));
		}
		add(GenPan, BorderLayout.CENTER);
		repaint();
		validate();
	}
	
	public void enterPos() {
		GenPan.remove(bottomPanel);
		bottomPanel.removeAll();
		input.setText("Enter the position of the card (1 - 5)");
		bottomPanel.add(input);
		bottomPanel.add(cardBox);
		GenPan.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public void swapCards() {
		// One by one move boundary of unsorted subarray
        Collections.sort(removeCards);
		for (int i = removeCards.size() - 1; i >= 0; i--) {
			players.get(player).removeCard(removeCards.get(i) - 1);
		}
		while (players.get(player).handsize < 5) {
			players.get(player).addCard(dealer.deck);
		}
		removeCards.removeAllElements();
	}
	
	public void dealerTurn() {
		evalHand(0);
		//The dealer AI is if the dealer has a hand that is worst than a three of a kind, swap cards out
		if (players.get(0).total < 3) {
			int pos = 4, dealercounter = 0;
			while (dealercounter < 3) {
				if (players.get(0).PlayerHand.get(pos).value != players.get(0).PlayerHand.get(pos - 1).value) {
					players.get(0).PlayerHand.remove(pos);
				}
				pos--;
				dealercounter++;
			}
			players.get(0).setHandsize(5);
			players.get(0).getHand(dealer.deck);
		}
		evalHand(0);
	}
	
	public static void evalHand(int player) {
		//If the suits are the same
		boolean samesuit = true;
		//How the cards are in the order
		int sequence = 0;
		//How many of the same cards appear
		double same = 0;
		//If all the suits are the same, then it becomes true
		for (int i = 0; i < 4; i++) {
			if (players.get(player).PlayerHand.get(i).suit != players.get(player).PlayerHand.get(i + 1).suit)
				samesuit = false;
		}
		//Checks how many times there are similar cards, counting the pair and others 
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (players.get(player).PlayerHand.get(i).value == players.get(player).PlayerHand.get(j).value) 
					same++;
			}
		}
		//Divides the number by 2
		same /= 2.0;
		//Gets how many times the numbers are in sequence
		for (int i = 0; i < 4; i++) {
			if ((players.get(player).PlayerHand.get(i).value + 1) == players.get(player).PlayerHand.get(i + 1).value)
				sequence++;
		}
		//Points distributed by type of hand with royal flush being 10 and highest card being 1
		//If the number are in the sequence of 3, and the first number is 1 and second is 10 and samesuit, then royal flush
		//Has a separate path to follow depending on the player
		if ((sequence == 3) && (players.get(player).PlayerHand.get(0).value == 1) && (players.get(player).PlayerHand.get(1).value == 10) && (samesuit)) {
			players.get(player).total = 10;
			players.get(player).highestcard = players.get(player).PlayerHand.get(4).value;
		}
		//If all in numerical order and same suit
		else if ((sequence == 4) && (samesuit)) {
			players.get(player).total = 9;
			players.get(player).highestcard = players.get(player).PlayerHand.get(4).value;
		}
		//This is the value of same if is 4 of a kind
		else if (same == 8.5) {
			players.get(player).total = 8;
			for (int i = 0; i < 5; i++) {
				if (players.get(player).PlayerHand.get(i).value == players.get(player).PlayerHand.get(i + 1).value) {
					players.get(player).highestcard = players.get(player).PlayerHand.get(i).value;
					break;
				}
			}
		}
		//This is the value of same if it is full house
		else if (same == 6.5) {
			players.get(player).total = 7;
			players.get(player).highestcard = players.get(player).PlayerHand.get(4).value;
		}
		//If all the same suit
		else if (samesuit) {
			players.get(player).total = 6;
			players.get(player).highestcard = players.get(player).PlayerHand.get(4).value;
		}
		//If in numerical order or if it is royal flush without the samesuit
		else if ((sequence == 4) || ((sequence == 3) && (players.get(player).PlayerHand.get(0).value == 1) 
				&& (players.get(player).PlayerHand.get(1).value == 10))) {
			players.get(player).total = 5;
			players.get(player).highestcard = players.get(player).PlayerHand.get(4).value;
		}
		//This is the value of same if it is 3 of a kind
		else if (same == 5.5) {
			players.get(player).total = 4;
			for (int i = 0; i < 5; i++) {
				if (players.get(player).PlayerHand.get(i).value == players.get(player).PlayerHand.get(i + 1).value) {
					players.get(player).highestcard = players.get(player).PlayerHand.get(i).value;
					break;
				}
			}
		}
		//This is the value of same if it is 2 pairs of cards
		else if (same == 4.5) {
			players.get(player).total = 3;
			for (int i = 0; i < 5; i++) {
				if (players.get(player).PlayerHand.get(i).value == players.get(player).PlayerHand.get(i + 1).value) {
					players.get(player).highestcard = players.get(player).PlayerHand.get(i).value;
					break;
				}
			}
		}
		//This is the value of same if it is 1 pair of cards
		else if (same == 3.5) {
			players.get(player).total = 2;
			for (int i = 0; i < 5; i++) {
				if (players.get(player).PlayerHand.get(i).value == players.get(player).PlayerHand.get(i + 1).value) {
					players.get(player).highestcard = players.get(player).PlayerHand.get(i).value;
					break;
				}
			}
		}
		//This is high card
		else {
			players.get(player).total = 1;
			players.get(player).highestcard = players.get(player).PlayerHand.get(4).value;
		}
	}
	
	public void declareWinner() {
		player = 0;
		displayCards();
		bottomPanel.removeAll();
		GenPan.remove(bottomPanel);
		bottomPanel.setLayout(new BorderLayout());
		String winText = "";
		//Vector in case of ties
		Vector <String> winners = new Vector <String> ();
		winners.add(players.get(0).Name);
		int winningnum = players.get(0).total;
		int highestcardvalue = players.get(0).highestcard;
		//Checks the total of each player which was dependent on the type of hand
		//If it comes down to a tie, then it goes to the on with the highest card value
		for (int i = 1; i < players.size(); i++) {
			evalHand(i);
			//Goes through each player and if tie add to vector, otherwise clear and have one
			if (players.get(i).total > winningnum) {
				winners.removeAllElements();
				winners.add(players.get(i).Name);
				winningnum = players.get(i).total;
			}
			else if (players.get(i).total == winningnum) {
				if (players.get(i).highestcard > highestcardvalue) {
					winners.removeAllElements();
					winners.add(players.get(i).Name);
					winningnum = players.get(i).total;
				}
				else if (players.get(i).highestcard == highestcardvalue) {
					winners.add(players.get(i).Name);
				}
			}
		}
		
		//Prints out the winners
		winText += winners.get(0);
		winText += " won with ";
		//Tells what kind hand won
		switch (winningnum) {
			case 1: winText += "got a High Card of " + highestcardvalue; break;
			case 2: winText += "1 pair of cards in this hand"; break;
			case 3: winText += "2 pair of cards in this hand"; break;
			case 4: winText += "3 of a kind"; break;
			case 5: winText += "a flush"; break;
			case 6: winText += "a straight"; break;
			case 7: winText += "a full house"; break;
			case 8: winText += "4 of a kind"; break;
			case 9: winText += "a straight flush"; break;
			case 10: winText += "a royal flush"; break;
		}
		input.setForeground(Color.RED);
		input.setText(winText);
		restart.setFont(new Font("Times New Roman", Font.BOLD, 25));
		restart.setText("Restart");
		bottomPanel.add(input, BorderLayout.SOUTH);
		bottomPanel.add(restart, BorderLayout.NORTH);
		input.setHorizontalAlignment(SwingConstants.CENTER);
		GenPan.add(bottomPanel, BorderLayout.SOUTH);
	}
}