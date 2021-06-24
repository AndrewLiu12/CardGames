/**
 * 
 */
package casino;

import java.util.*;

/**
 * @author AndrewLiu12
 *
 */
public class Player {

	/**
	 * @param args
	 */
	public String Name;
	public int total;
	public int highestcard;
	public int handsize = 5;
	public Vector <Card> PlayerHand = new Vector <Card> ();
	public Card newestCard;
	Random rannum1 = new Random();
	
	public static void main(String[] args) {
	}
	
	public Player() // default constructor
	{
		
		Name = "Joe";
		total = 0;
	}
	
	public Player(String NewName, int Newhandsize, Deck deck) // initializing constructor with values sent over
	{
		
		Name = NewName; 
		handsize = Newhandsize; 
		getHand(deck);
	}
	
	public void getHand(Deck deck) {
		//Gives the user a hand
		
		int num = 0;
		for (int x = PlayerHand.size(); x < handsize; x++) {
			num = rannum1.nextInt(deck.getSize());
			PlayerHand.add(deck.getCard(num));
			deck.removeCard(num);
		}
		sortHand(PlayerHand);
	}
	
	public int getHandsize() {
		return handsize;
	}
	
	public void setHandsize(int Newhandsize) {
		handsize = Newhandsize;
	}
	
	public void setTotal(int newtotal) {
		total = newtotal;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String NewName) {
		Name = NewName;
	}
	
	public Card getNewestCard() {
		return newestCard;
	}
	
	public void setNewestCard(Card NewCard) {
		newestCard = NewCard;
	}
	
	public void sortHand(Vector<Card> PlayerHand) {	
		int counter = 0, compare, small, size = handsize - 1;
		while (counter < size) {
			//set smallest item to main counter
			small = counter;
			//set compare to next item
			compare = counter + 1;
			while (compare <= size) {	
				if ((PlayerHand.get(compare).value) <= (PlayerHand.get(small).value)) {
					//swaps 2 values to place smallest on top
					Collections.swap(PlayerHand, compare, small);
				}
				//move to next num
				compare = compare + 1;
			}
			//go to next item
			counter = counter + 1;
		}
		//System.out.println(PlayerHand);
	}
	
	public void addCard(Deck deck) {
		int num = rannum1.nextInt(deck.getSize());
		PlayerHand.add(deck.getCard(num));
		newestCard = deck.getCard(num);
		setHandsize(PlayerHand.size());
		sortHand(PlayerHand);
		
	}
	
	public void removeCard(int num) {
		PlayerHand.remove(num);
		setHandsize(PlayerHand.size());
		sortHand(PlayerHand);
		
	}
	
	public void transferCards(Card card) {
		PlayerHand.add(card);
		setHandsize(PlayerHand.size());
		sortHand(PlayerHand);
		
	}

}










