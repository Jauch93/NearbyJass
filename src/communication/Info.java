package communication;

import cards_and_Deck.Card;
import game.GameState;

//
//
//In dieser Klasse soll die übermittlung der Daten vereinfacht werden.
//Ziel: Mit einer einkommenden Payload soll direkt in der CallBackFunktion ein InfoObjekt erstellt werden, die die verschiedenen 
//		Daten aufschlüsselt und verarbeitungsbereit macht.
//
//


public class Info
{
	private byte[] message;			//Das Der effektive String/Int, whatever, der übermittelt werden soll
	private GameState nextState;	//Nächste State, der das GameObject einnehmen soll
	private DataType dataType;		//ist message als int/String/Card zu entschlüsseln?
	
	private byte[] infoObject;
	
	public Info(Payload payload)		//Die erhaltene Payload soll hier aufgeschlüsselt werden und in die verschiedenen Informationen unterteilt werden.
	{
		message = payload.asBytes();
		//TODO: Trennung von stateInfo und Message--> dazu erst info objekt erstellen!
	}
	
	public Info(GameState nextState, byte[] message, DataType dataType)
	{
		this.message = message;
		this.nextState = nextState;
		this.dataType = dataType;
		this.infoObject = new byte[message.length + 2];
		
		infoObject[0] = (byte)nextState.ordinal();
		infoObject[1] = (byte)dataType.ordinal();
		for(int i = 0; i < message.length; i ++)
			infoObject[i+2] = message[i];
	}
	
	public Info(GameState gameState, Card card)
	{
	//TODO: Konstruktor, der eine Karte direkt verarbeiten kann
	}
	
	public Payload asPayload()
	{
		return Payload.fromBytes(message);
	}
	
}
