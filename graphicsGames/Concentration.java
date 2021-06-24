/**
 * 
 */
package graphicsGames;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import sideClasses.*;
//include the following 
import javax.swing.Timer;


/**
 * @author AndrewLiu12
 *
 */
public class Concentration extends JFrame implements ActionListener {

	/**
	 * @param args
	 */
	//Done
	static JMenuBar menuBar = new JMenuBar();
	JPanel CardPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JLabel Score = new JLabel();
	JLabel Scored = new JLabel();
	JLabel title = new JLabel();
	JButton restart = new JButton();
	Dealer dealer = new Dealer();
	/** The card displays. */
	private JLabel[] displayCards = new JLabel[52];
	private int score = 0;
	int cntr = 0;
	private Vector<Card> selectedCards = new Vector <Card>();
	private boolean[] selections = new boolean[52];
	private boolean[] showing = new boolean[52];
	//declare a timer at top
	
	

	
	
	
	public static void main(String[] args) {
		Concentration frame = new Concentration(); // create instance of frame with panel
		frame.setTitle("Concentration"); // set window title
		frame.setSize(1000, 550); // set size of window
		frame.setLocationRelativeTo(null); // center the frame New JDK 1.5
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set corner X to close window
		frame.setVisible(true); // make window show up
		//Adding a menu bar
		frame.setJMenuBar(menuBar);
	}
	
	public Concentration() {
		//Adding in what it says in the menu bar
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		help.add(new JMenuItem("Click up to two cards to match up pairs(same color and number)"));
		help.add(new JMenuItem("and try and get rid of all the cards"));
		title.setFont(new Font("Lucida Sans", Font.BOLD, 16));
		title.setText("Concentration/Memory");
		title.setForeground(Color.RED);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		add(title, BorderLayout.NORTH);
		bottomPanel.setLayout(new BorderLayout());
		CardPanel.setLayout(new GridLayout(4, 13));
		bottomPanel.setBackground(Color.GREEN);
		CardPanel.setBackground(Color.GREEN);
		// if true the component paints every pixel within its bounds
		title.setOpaque(true);
		// sets the background color of this component
		// the background color is used only if the component is opaque
		title.setBackground(Color.GREEN);
		//Deals all the cards out, with the back 
		for (int i = 0; i < 52; i++) {
			displayCards[i] = new JLabel();
			ImageIcon card = new ImageIcon(System.getProperty("user.dir") + "/cards/back1.gif");
			displayCards[i].setIcon(card);
			CardPanel.add(displayCards[i]);
			displayCards[i].addMouseListener(new MyMouseListener());
			selections[i] = false;
			showing[i] = true;
		}
		//Bottom panel has score
		//Card Panel shows cards
		Score.setFont(new Font("Times New Roman", Font.BOLD, 20));
		Score.setForeground(Color.RED);
		Scored.setFont(new Font("Times New Roman", Font.BOLD, 20));
		Scored.setForeground(Color.RED);
		Score.setText("Score: " + score);
		Scored.setHorizontalAlignment(SwingConstants.CENTER);
		bottomPanel.add(Score, BorderLayout.WEST);
		Scored.setHorizontalAlignment(SwingConstants.RIGHT);
		bottomPanel.add(Scored, BorderLayout.EAST);
		bottomPanel.add(restart, BorderLayout.SOUTH);
		add(CardPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		restart.setVisible(false);
		restart.addActionListener(this);
	}
	
	public void newBoard() {
		Scored.setText("");
		CardPanel.removeAll();
		int selectedCardsSize = 0;
		for (int i = 0; i < 52; i++) {
			ImageIcon card = new ImageIcon();
			//If card is selected make face up
			if (selections[i]) {
				card = new ImageIcon(System.getProperty("user.dir") + "/cards/" + dealer.deck.getCard(i).Value.substring(0, dealer.deck.getCard(i).Value.indexOf(" ")) + dealer.deck.getCard(i).Suit.substring(1) + ".gif");
				selectedCardsSize++;
			}
			else {
				//Otherwise make it face down
				if (showing[i])
					card = new ImageIcon(System.getProperty("user.dir") + "/cards/back1.gif");
				//If the card was already matched, make a blank panel
				else
					card = new ImageIcon();
			}
			//Have the card displayed
			displayCards[i].setIcon(card);
			CardPanel.add(displayCards[i]);
		}
		if (selectedCardsSize == 2) {
			checkPair();
		}
		//Win screen
		if (score == 26) {
			score = 0;
			CardPanel.removeAll();
			title.setFont(new Font("Times New Roman", Font.BOLD, 30));
			title.setText("Congratulations!!! You Won!");
			restart.setText("Restart");
			restart.setFont(new Font("Times New Roman", Font.BOLD, 15));
			restart.setVisible(true);
		}	
	}
	
	public void checkPair() {
		cntr = 0;
		if (((selectedCards.get(0).suit == 1) && (selectedCards.get(1).suit == 4)) || 
				((selectedCards.get(0).suit == 4) && (selectedCards.get(1).suit == 1)) ||
				((selectedCards.get(0).suit == 3) && (selectedCards.get(1).suit == 2)) ||
				((selectedCards.get(0).suit == 2) && (selectedCards.get(1).suit == 3))) {
			if (selectedCards.get(0).value == selectedCards.get(1).value) {
				Scored.setText("You got a pair");
				score++;
				Score.setText("Score: " + score);
				removeCards();
			}
			else {
				Scored.setText("You didn't get a pair");
				for (int i = 0; i < 52; i++) {
					for (int j = 0; j < 2; j++) {
						if ((selectedCards.get(j)).equals(dealer.deck.getCard(i))) {
							selections[i] = false;
						}
					}
				}
			}
		}
		else {
			Scored.setText("You didn't get a pair");
			for (int i = 0; i < 52; i++) {
				for (int j = 0; j < 2; j++) {
					if ((selectedCards.get(j)).equals(dealer.deck.getCard(i))) {
						selections[i] = false;
					}
				}
			}
		}
		selectedCards.removeAllElements();
		//Needs a pause then the cards are flipped back
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeCards() {
		//Goes from last to first to make order nice
		for (int i = 51; i >= 0; i--) {
			for (int j = 0; j < 2; j++) {
				if ((selectedCards.get(j)).equals(dealer.deck.getCard(i))) {
					selections[i] = false;
					showing[i] = false;
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		CardPanel.removeAll();
		if (e.getSource() == restart) {
			Score.setText("Score: " + score);
			dealer = new Dealer();
			for (int i = 0; i < 52; i++) {
				selections[i] = false;
				showing[i] = true;
			}
			selectedCards.removeAllElements();
			title.setFont(new Font("Times New Roman", Font.BOLD, 15));
			restart.setVisible(false);
			title.setText("Concentration/Memory");
			newBoard();
		}
		repaint();
		validate();
		
	}
	
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent Evt)
		{
			//Pauses program for 1000 milliseconds, or until user input
			// counts number of rolls
			cntr++;
			// when hits 10 stops rolling
			if (cntr == 10) {
				newBoard();
				repaint();
				validate();
			}
		}
	}

	
	/**
	 * Receives and handles mouse clicks.  Other mouse events are ignored.
	 */
	private class MyMouseListener implements MouseListener {

		/**
		 * Handle a mouse click on a card by toggling its "selected" property.
		 * Each card is represented as a label.
		 * @param e the mouse event.
		 */
		public void mouseClicked(MouseEvent e) {
			for (int k = 0; k < 52; k++) {
				//Checks to see if the area clicked, has a card attached to it
				if (e.getSource().equals(displayCards[k])
						&& showing[k] == true) {
					selections[k] = !selections[k];
					if (selections[k] == true)
						selectedCards.add(dealer.deck.getCard(k));
					else
						selectedCards.remove(selectedCards.indexOf(dealer.deck.getCard(k)));
					newBoard();
					repaint();
					validate();
					return;
				}
			}
		}

		/**
		 * Ignore a mouse exited event.
		 * @param e the mouse event.
		 */
		public void mouseExited(MouseEvent e) {
		}

		/**
		 * Ignore a mouse released event.
		 * @param e the mouse event.
		 */
		public void mouseReleased(MouseEvent e) {
		}

		/**
		 * Ignore a mouse entered event.
		 * @param e the mouse event.
		 */
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * Ignore a mouse pressed event.
		 * @param e the mouse event.
		 */
		public void mousePressed(MouseEvent e) {
		}
	}
	

	
}



