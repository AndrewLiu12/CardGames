/**
 * 
 */
package casino;

import java.util.*;

/**
 * @author AndrewLiu12
 *
 */
public class Dealer extends Player{

	/**
	 * @param args
	 */
	
	//Dealer name
	public String Name = "House";
	public int handsize = 5;
	public int total, highestcard;
	public Deck deck = new Deck();
	//Dealer hand
	//public Vector <Card> DealerHand = new Vector <Card> ();
	Random rannum1 = new Random();
	
	public static void main(String[] args) {
	}
	
	public Dealer() // default constructor
	{
		Name = "House";
		total = 0;
	}
	
	public Dealer(int Newhandsize) // initializing constructor with values sent over
	{
		handsize = Newhandsize; 
	}
	
	//Gets a hand for the dealer
	/*public void getHand(Deck deck) {
		int num = 0;
		for (int i = DealerHand.size(); i < handsize; i++) {
			num = rannum1.nextInt(deck.getSize());
			DealerHand.add(deck.getCard(num));
			deck.removeCard(num);
		}
		sortHand(DealerHand);
	}*/
	
	public void sortHand(Vector<Card> DealerHand) {	
		int counter = 0, compare, small, size = handsize - 1;
		while (counter < size) {
			//set smallest item to main counter
			small = counter;
			//set compare to next item
			compare = counter + 1;
			while (compare <= size) {	
				if ((DealerHand.get(compare).value) <= (DealerHand.get(small).value)) {
					//swaps 2 values to place smallest on top
					Collections.swap(DealerHand, compare, small);
				}
				//move to next num
				compare = compare + 1;
			}
			//go to next item
			counter = counter + 1;
		}
	}
	
	

	
	public int getHandsize() {
		return handsize;
	}
	
	public void setHandsize(int Newhandsize) {
		handsize = Newhandsize;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int newtotal) {
		total = newtotal;
	}
	
	public String getName() {
		return Name;
	}
	
	public int getDeckSize() {
		return deck.getSize();
	}
	
	/*public void addCard(Deck deck) {
		int num = rannum1.nextInt(deck.getSize());
		DealerHand.add(deck.getCard(num));
		handsize++;
	}
	
	public void removeCard(int num, Deck deck) {
		DealerHand.remove(num);
		handsize--;
	}*/
}















