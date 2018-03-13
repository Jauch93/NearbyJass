package game;

import cards_and_Deck.*;
import communication.*;

public class Host extends Game
{
	private Deck deck;
	private Participant participants[] = new Participant[3];	//Array in dem die Infos über die mitSpieler stehen
	
	Host(GoogleApiClient mGoogleApiClient) {
		super(mGoogleApiClient);
		generateNewDeck();
	}
	
	private void generateNewDeck() 
	{
		deck = new Deck();		
	}

	public void sendPayloadTo(Participant p, Payload payload)	//GrundFunktion um Payload an einen mitSpieler zu schicken
	{
		//Nearby.Connections.sendPayload(getGoogleApiClient(), p.getEndpointId(), payload);
	}
	
	public void sendToAllParticipants(Payload payload)			//Schickt die gleiche Payload an alle Spieler
	{
		for(int i = 0; i < participants.length; i++)
        {
            sendPayloadTo(participants[i], payload);
        }
	}
	
	public void propagateCard(Card card)
	{
        Info info = new Info(GameState.waitForHandCard, card);
        
        Payload payload = info.asPayload();
        sendToAllParticipants(payload);       
    }

}
