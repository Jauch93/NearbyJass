package game;

import cards_and_Deck.Card;

public class LocalPlayer extends Player
{
	private Card[] handCards;
	private int anzCards = 0;

	LocalPlayer(String name) 
	{
		super(name);
	}
	
	public void takeCard(Card card)			//Karte der Hand des Spieler hinzufügen.
	{
		handCards = new Card[anzCards + 1];
		handCards[anzCards] = card;
		anzCards++;
	}
	
	public void setAllCardsPlayable()
	{
		for(int i = 0; i < anzCards; i++)
			handCards[i].setCardPlayable(true);
	}
	
	public void sortCards()
	{
		
	}
	
	public int getAnzCards()
	{
		return anzCards;
	}

}
