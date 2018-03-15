package cards_and_Deck;

/**
 * This Class represents a CardStack.
 * @author Jauch
 *
 */

public class Deck 
{
	private Card[] deck;
	private int cardsInDeck = 0;
	
/**
 * 	Creates a Simple Stack of 36 Cards to play Jass with.
 */
	public Deck()			//Erstellt ein Standart Jass Deck mit 36 Karten.
	{
		deck = new Card[36];
		for(int j = 0; j < 4; j++)
		{
			for(int i = 0; i < 9; i++)
			{
				deck[cardsInDeck] = new Card(CardColor.values()[j], CardName.values()[i], 0, 0);
				cardsInDeck++;
			}
		}
	}

	/**
	 * 
	 * @return the actual number of Cards in the Stack.
	 */
	
	public int length()
	{
		return cardsInDeck;
	}
	
	/**
	 * Draws one Card randomly.
	 * @return	a random Card.
	 */
	public Card drawRandom()
	{
		if(cardsInDeck > 0)
		{
			int rand = (int)((Math.random() * (cardsInDeck)));
			Card ret = deck[rand];
			
			for(int i = rand; i < cardsInDeck-1; i++)
				deck[i] = deck[i+1];
			
			cardsInDeck--;
			return ret;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Draws from the top of the Stack.
	 * @return
	 */
	
	public Card drawFromTop()
	{
		if(cardsInDeck > 0)
		{
			cardsInDeck--;
			return deck[cardsInDeck];
		}
		else
			return null;
	}

}
