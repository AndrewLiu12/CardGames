/**
 * 
 */
package graphicsGames;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import sideClasses.*;
import javax.swing.JOptionPane;

/**
 * @author AndrewLiu12
 *
 */

public class BlackJackGUI extends JFrame implements ActionListener {
	
	private static Vector<Player> players = new Vector <Player> ();
	private static Dealer dealer = new Dealer();
	private static String name;
	private static JPanel GenPan = new JPanel();
	private JButton StartButton = new JButton();
	private static JButton passButton = new JButton();
	private static JButton hitButton = new JButton();
	private static JButton restartButton = new JButton();
	private static JButton getTotal = new JButton();
	private static JPanel buttonPanel = new JPanel();
	private static JPanel bottomPanel = new JPanel();
	private StartGamePanel StartGame = new StartGamePanel();
	private GiveCardsPanel GiveCards = new GiveCardsPanel();
	private static JPanel textField = new JPanel();
	private static JTextField messageBox = new JTextField(25);
	static JPanel dealerPan = new JPanel();
	static JPanel playerPan = new JPanel();
	static JLabel input = new JLabel();
	static JLabel dealertext = new JLabel();
	static JLabel playertext = new JLabel();
	static JPanel dealerCardPanel = new JPanel();
	static JPanel playerCardPanel = new JPanel();
	static JLabel wintext = new JLabel();
	static ImageIcon back = new ImageIcon(System.getProperty("user.dir") + "/cards/back1.gif");
	static JLabel asking = new JLabel();
	static int returning = 0;
	
	public static void main(String[] args) {
	    System.out.println(System.getProperty("user.dir"));
	  
		BlackJackGUI frame = new BlackJackGUI(); // create instance of frame with panel
		frame.setTitle("BlackJack"); // set window title
		frame.setSize(750, 750); // set size of window
		frame.setLocationRelativeTo(null); // center the frame New JDK 1.5
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set corner X to close window
		frame.setVisible(true); // make window show up
	}
	
	public BlackJackGUI()
	{
		GenPan.setLayout(new BorderLayout());
		buttonPanel.setLayout(new BorderLayout());
		StartButton.setText("Start BlackJack");
		StartButton.setFont(new Font("SansSerif", Font.BOLD, 24));
		getTotal.setFont(new Font("SansSerif", Font.BOLD, 24));
		getTotal.setText("Get Total");
		GenPan.add(StartButton, BorderLayout.NORTH);
		//Changes the whole background to the color
		GenPan.setBackground(Color.BLUE);
		add(GenPan);
		//Start Button
		StartButton.addActionListener(this);
		passButton.addActionListener(this);
		hitButton.addActionListener(this);
		messageBox.addActionListener(this);
		restartButton.addActionListener(this);
		getTotal.addActionListener(this);
	}
	
	class StartGamePanel extends JPanel {
		// general drawing
		@Override
		public void paintComponent (Graphics g)
		{
			super.paintComponent(g);
		}
	}
	
	class GiveCardsPanel extends JPanel {
		// general drawing
		@Override
		public void paintComponent (Graphics g)
		{
			super.paintComponent(g);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		remove(GenPan);
		if (e.getSource() == StartButton) {
			GenPan = StartGame;
			GenPan.setBackground(Color.GREEN);
			startScreen();
		}
		else if (e.getSource() == messageBox) {
			//Put this here before setting the layout
			GenPan = GiveCards;
			GenPan.setBackground(Color.GREEN);
			//Need to set layout every instance
			GenPan.setLayout(new BorderLayout());
			dealerCardPanel.setBackground(Color.GREEN);
			playerCardPanel.setBackground(Color.GREEN);
			name = messageBox.getText();
			//player 1 is 0
			players.add(new Player("House", 2, dealer.deck));
			players.add(new Player(name, 2, dealer.deck));
			String path = null;
			ImageIcon card = new ImageIcon();
			dealertext.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			playertext.setFont(new Font("Times New Roman", Font.PLAIN, 30));
			dealertext.setForeground(Color.RED);
			playertext.setForeground(Color.RED);
			dealerPan.setLayout(new BorderLayout());
			playerPan.setLayout(new BorderLayout());
			dealerCardPanel.setLayout(new GridLayout(1, 5, 5, 5));
			playerCardPanel.setLayout(new GridLayout(1, 5, 5, 5));
			for (int i = 0; i < players.size(); i++) {
				for (int j = 0; j < players.get(i).handsize; j++) {
					JLabel picture = new JLabel();
					path = System.getProperty("user.dir") + "/cards/";
					path += players.get(i).PlayerHand.get(j).Value.substring(0, players.get(i).PlayerHand.get(j).Value.indexOf(" ")) + 
							players.get(i).PlayerHand.get(j).Suit.substring(1) + ".gif";
					card = new ImageIcon(path);
					picture.setIcon(card);
					if (i == 0) {
						if (j == 0) {
							picture.setIcon(back);
							dealerCardPanel.add(picture);
						}
						else
							dealerCardPanel.add(picture);
					}
					else 
						playerCardPanel.add(picture);
				}
			}
			dealerPan.add(dealerCardPanel, BorderLayout.CENTER);
			playerPan.add(playerCardPanel, BorderLayout.CENTER);
			GenPan.add(dealerPan, BorderLayout.EAST);
			GenPan.add(playerPan, BorderLayout.WEST);
			dealertext.setOpaque(true);
			playertext.setOpaque(true);
			dealertext.setBackground(Color.GREEN);
			playertext.setBackground(Color.GREEN);
			buttonPanel.setVisible(false);
			passButton.setText("Stay");
			hitButton.setText("Hit");
			passButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
			hitButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.add(passButton);
			buttonPanel.add(hitButton);
			GenPan.add(buttonPanel, BorderLayout.NORTH);
			GenPan.add(getTotal, BorderLayout.SOUTH);
		}
		else if (e.getSource() == passButton) {
			passButtonPressed();
		}
		else if (e.getSource() == hitButton) {
			hitButtonPressed();
		}
		else if (e.getSource() == getTotal) {
			GenPan.remove(getTotal);
			buttonPanel.setVisible(true);
			displayTotals();
		}
		else if (e.getSource() == restartButton) {
			GenPan = StartGame;
			passButton.setEnabled(true);
			hitButton.setEnabled(true);
			wintext.setText("");
			players.clear();
			playertext.setText("");
			dealertext.setText("");
			playerCardPanel.removeAll();
			dealerCardPanel.removeAll();
			bottomPanel.removeAll();
			startScreen();
		}
		add(GenPan, BorderLayout.CENTER);
		// refreshes screen
		repaint();
		validate();
	}
	
	public static void displayTotals() {
		getTotal(0);
		dealertext.setText("House's hand: ???");
		dealerPan.add(dealertext, BorderLayout.NORTH);
		getTotal(1);
		playertext.setText(name + "'s hand: " + players.get(1).total);
		playerPan.add(playertext, BorderLayout.NORTH);
		if (players.get(1).total == 21)
			passButtonPressed();
	}
	
	public static void getTotal(int player) {
		players.get(player).total = 0;
		for (int i = 0; i < players.get(player).PlayerHand.size(); i++) {
			if ((players.get(player).PlayerHand.get(i).value == 1) && (player == 0)) {
				if (players.get(player).total < 11) 
					players.get(player).PlayerHand.get(i).value = 11;
				else 
					players.get(player).PlayerHand.get(i).value = 1;
			}
			else if ((players.get(player).PlayerHand.get(i).value == 1) && (player == 1)) {
				players.get(player).PlayerHand.get(i).value = aceAppears();
			}
			else if (players.get(player).PlayerHand.get(i).value > 10) {
				players.get(player).PlayerHand.get(i).value = 10;
			}
			players.get(player).total += players.get(player).PlayerHand.get(i).value;
		}
	}
	
	public static int aceAppears() {
		int num = 0;
		String input = JOptionPane.showInputDialog(null, "Do you want your ace to be one or eleven(Type 1 or 11): ",
				"One or Eleven", JOptionPane.QUESTION_MESSAGE);
		if (input.equals("1"))
			num = 1;
		else
			num = 11;
		return num;
	}
	
	public static void startScreen() {
		GenPan.setLayout(new BorderLayout());
		JLabel picture = new JLabel();
		picture.setIcon(back);
		picture.setHorizontalAlignment(SwingConstants.CENTER);
		input.setForeground(Color.RED);
		buttonPanel.setBackground(Color.GREEN);
		GenPan.add(picture, BorderLayout.CENTER);
		//Add components outside of paint component
		//Doesn't work as intended otherwise
		input.setText("Enter a name: ");
		input.setFont(new Font("Courier", Font.BOLD, 20));
		messageBox.setFont(new Font("Courier", Font.BOLD, 20));
		textField.add(input, BorderLayout.WEST);
		textField.add(messageBox, BorderLayout.CENTER);
		textField.setBackground(Color.GREEN);
		GenPan.add(textField, BorderLayout.SOUTH);
		messageBox.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	public static void passButtonPressed() {
		hitButton.setEnabled(false);
		passButton.setEnabled(false);
		while (true) {
			String path = "";
			//If less than 16, must hit
        	if (players.get(0).total <= 16) {
        		ImageIcon card = new ImageIcon();
        		players.get(0).addCard(dealer.deck);
        		JLabel picture = new JLabel();
        		path = System.getProperty("user.dir") + "/cards/";
				path += players.get(0).newestCard.Value.substring(0, players.get(0).newestCard.Value.indexOf(" ")) + 
						players.get(0).newestCard.Suit.substring(1) + ".gif";
				card = new ImageIcon(path);
				picture.setIcon(card);
        		dealerCardPanel.add(picture);
        	}
        	//Checks the same conditions as for a player
        	else if (players.get(0).total > 21)
        		break;
        	else if (players.get(0).total == 21) 
        		break;
        	else if (players.get(0).handsize == 5) 
        		break;
        	else 
        		break;
        	//Checks for aces and face card
        	getTotal(0);
		 }
		 dealertext.setText("House's hand: " + players.get(0).total);
		 checkWin(players.get(0));
	}
	
	public static void hitButtonPressed() {
		String path = "";
		ImageIcon card = new ImageIcon();
		players.get(1).addCard(dealer.deck);
		JLabel picture = new JLabel();
		path = System.getProperty("user.dir") + "/cards/";
		path += players.get(1).newestCard.Value.substring(0, players.get(1).newestCard.Value.indexOf(" ")) + 
				players.get(1).newestCard.Suit.substring(1) + ".gif";
		card = new ImageIcon(path);
		picture.setIcon(card);
		playerCardPanel.add(picture);
		if (players.get(1).newestCard.value > 10)
			players.get(1).newestCard.value = 10;
		else if (players.get(1).newestCard.value == 1) 
			players.get(1).newestCard.value = aceAppears();
		players.get(1).total += players.get(1).newestCard.value;
		playertext.setText(players.get(1).Name + "'s hand: " + Integer.toString(players.get(1).total));
		if (players.get(1).handsize == 5)
			passButtonPressed();
		else if (players.get(1).total >= 21)
			passButtonPressed();
	}
	
	public static void checkWin(Player dealer) {
		dealerCardPanel.removeAll();
		for (int i = 0; i < dealer.PlayerHand.size(); i++) {
			JLabel picture = new JLabel();
			ImageIcon card = new ImageIcon();
			String path = "";
			path = System.getProperty("user.dir") + "/cards/";
			path += players.get(0).PlayerHand.get(i).Value.substring(0, players.get(0).PlayerHand.get(i).Value.indexOf(" ")) + 
					players.get(0).PlayerHand.get(i).Suit.substring(1) + ".gif";
			card = new ImageIcon(path);
			picture.setIcon(card);
			dealerCardPanel.add(picture);
		}
		//Vector in case of ties
		Vector <String> winners = new Vector <String> ();
		int winnersize = 0;
		String winningText = "";
		int winningnum = 0;
		if (dealer.total <= 21) {
			//Sets dealer as winner if not over 21
			winners.add(dealer.Name);
			winningnum = dealer.total;
			winnersize++;
		}
		for (int i = 1; i < players.size(); i++) {
			//Goes through each player and if tie add to vector, otherwise clear and have one
			if ((players.get(i).total > winningnum) && (players.get(i).total <= 21)) {
				winnersize = 1;
				winners.removeAllElements();
				winners.add(players.get(i).Name);
				winningnum = players.get(i).total;
			}
			else if (players.get(i).total == winningnum)
				winnersize = 0;
		}
		winningText = "There are no winners";
		wintext.setText(winningText);
		if (winnersize > 0)
			winningText = winners.get(0);
		if (winnersize > 1) {
			for (int i = 1; i < winnersize - 1; i++) {
				winningText += ", " + winners.get(i);
			}
		}
		if (winnersize > 0) {
			winningText += " won with a hand of " + winningnum;
			wintext.setText(winningText);
		}
		wintext.setOpaque(true);
		wintext.setBackground(Color.GREEN);
		wintext.setForeground(Color.RED);
		bottomPanel.setLayout(new BorderLayout());
		wintext.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		wintext.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(wintext, BorderLayout.SOUTH);
		restartButton.setText("Restart");
		restartButton.setFont(new Font("Courier", Font.BOLD, 20));
		bottomPanel.add(restartButton, BorderLayout.CENTER);
		GenPan.add(bottomPanel, BorderLayout.SOUTH);
	}
}