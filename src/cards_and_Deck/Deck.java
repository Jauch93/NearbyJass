package cards_and_Deck;

public class Deck 
{
	private Card[] deck;
	private int cardsInDeck = 0;
	
	
	public Deck()			//Erstellt ein Standart Jass Deck mit 36 Karten.
	{
		deck = new Card[36];
		//TODO: Deck instanzieren
	}
	
	public int length()
	{
		return cardsInDeck;
	}
	
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
}
