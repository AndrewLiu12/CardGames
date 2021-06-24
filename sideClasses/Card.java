/**
 * 
 */
package casino;
/**
 * @author AndrewLiu12
 *
 */

public class Card {
	/**
	 * @param args
	 */
	public String Suit, Value;
	public int value, suit;
	
	public static void main(String[] args) {
	}
	
	public String getSuit(int suit) {
		//Gets a suit based on the ascii
		if (suit == 1)
			//Heart
			Suit = " hearts"; 
		else if (suit == 2)
			//Club
			Suit = " clubs"; 
		else if (suit == 3)
			//Spade
			Suit = " spades";
		else if (suit == 4)
			//Diamond
			Suit = " diamonds";
		this.suit = suit;
		return Suit;
	}
	
	public String getValue(int value) {
		//Some cards have a name to them 
		if (value == 1)
			Value = "ace of";
		else if (value == 11)
			Value = "jack of";
		else if (value == 12)
			Value = "queen of";
		else if (value == 13)
			Value = "king of";
		else if (value == 2)
			Value = "2 of";
		else if (value == 3)
			Value = "3 of";
		else if (value == 4)
			Value = "4 of";
		else if (value == 5)
			Value = "5 of";
		else if (value == 6)
			Value = "6 of";
		else if (value == 7)
			Value = "7 of";
		else if (value == 8)
			Value = "8 of";
		else if (value == 9)
			Value = "9 of";
		else if (value == 10)
			Value = "10 of";
		return Value;
	}
	

	public void PrintCard() {
		//Prints the card out
		System.out.println(Value + Suit);
	}
	
	Card() {
	}
	
	//Gets a new card for deck
	Card(int value, int suit) {
		this.value = value;
		this.suit = suit;
		getValue(value);
		getSuit(suit);
	}
	
	//Overides to write an actual string instead of random letters
	@Override
	public String toString() { 
	    return (Value + Suit);
	}
}