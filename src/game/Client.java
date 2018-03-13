package game;

import cards_and_Deck.*;
import communication.*;

public class Client extends Game
{
	Client(GoogleApiClient mGoogleApiClient) {
		super(mGoogleApiClient);
		// TODO Auto-generated constructor stub
	}
	
	private Participant host;	//Array in dem die Infos über die mitSpieler stehen
	
	public void sendToHost(Payload payload)		//GrundFunktion um Payload an Host zu schicken
	{
            //Nearby.Connections.sendPayload(getGoogleApiClient(), host.getEndpointId(), payload);
	}
	
	public void sendCard(Card card)	//Verteilt gespielte Karten an alle Spieler, damit sie diese auf Table ablegen können.
	{
        Info info = new Info(GameState.UNDEF, card);
        
        Payload payload = info.asPayload();
        sendToHost(payload);       
    }

}

