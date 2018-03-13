package game;

import cards_and_Deck.*;

public class Player 
{
	private String name;
	private String endPointId;
	private Card[] handCards;
	private int anzCards = 0;
	
	Player(String name)
	{
		this.name = name;
	}
	
	public void takeCard(Card card)			//Karte der Hand des Spieler hinzufügen.
	{
		handCards = new Card[anzCards + 1];
		handCards[anzCards] = card;
	}
	
	public int getAnzCards()
	{
		return anzCards;
	}
	
	public String getName()
	{
		return name;
	}

}
