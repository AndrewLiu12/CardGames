/**
 * 
 */
package casino;

import java.util.*;
/**
 * @author AndrewLiu12
 *
 */
public class Deck {
	/**
	 * @param args
	 */
	
	Vector <Card> deck = new Vector <Card> (52);
	
	public static void main(String[] args) {
	}
	
	//Shuffles the deck
	public static void Shuffle(Vector<Card> deck) {
		//Shuffles by taking the first element and swapping with another at the a random spot
		for (int i = 0; i < 52; i++) {
            int r = i + (int) (Math.random() * (52-i));
            //Swaps in a vector
            Collections.swap(deck, r, i);
        }
		//System.out.print(deck);
	}
	
	//Makes the deck
	public Deck() {
		//Adds a deck of cards to the deck class
		for (int i = 1; i <= 13; i++) {
			for (int j = 1; j <= 4; j++) {
				deck.add(new Card(i, j));
			}
		}
		Shuffle(deck);
	}
	
	//Gets a card
	public Card getCard(int card) {
		return deck.get(card);
	}

	//Gets the deck
	public Vector<Card> getDeck() {
		return deck;
	}
	//Remove card from deck, like when dealer gives out cards
	public void removeCard(int card) {
		deck.remove(card);
	}
	
	//Allows a card to be returned to the deck
	void returnCard(Card card) {
		deck.add(card);
	}
	
	//Gets the size of the card
	public int getSize() {
		return deck.size();
	}		
}