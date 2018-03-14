package game;

import cards_and_Deck.*;
import communication.*;

public class Host extends Game
{
	private Deck deck;
	
	Host(GoogleApiClient mGoogleApiClient) 
	{
		super(mGoogleApiClient);
		
		organizeConnections(); //Verbindungen aufbauen, endpointids und namen entgegennehmen, erst fortfahren, wenn alles erledigt ist.
		while(anzPlayer < 4) {}
		
		distributeInformation(); //Die Namen aller Mitspieler + weitere relevante informationen, wie ihre jeweilige Position am Tisch verteilen.
		
		do
			startTournament();
		while(playAgain());
		
		disconnectConnections();
	}
	//---------------------------------------------------------------------------------------------------
	//ConnectionManagement
	
	public void organizeConnections()
	{
		startAdvertising();
	}
	
	public void startAdvertising()
    {
        Nearby.Connections.startAdvertising(getGoogleApiClient(),
                getMyself().getName(),
                getServiceId(),
                mConnectionLifecycleCallback,       //Die Funtion wird aufgerufen, wenn ein discoverer eine Verbindung anfordert.
                new AdvertisingOptions((P2P_CLUSTER)))
                .setResultCallback( new ResultCallback<Connections.StartAdvertisingResult>() {
                    @Override
                    public void onResult( Connections.StartAdvertisingResult result ) {
                        if( result.getStatus().isSuccess() ) {
                            //SUCCESS: Advertising wird ausgeführt
                        }
                        else
                        {
                            //ERROR: Advertising konnte nicht ausgeführt werden.
                        }
                    }
                });
    }

	
	public final ConnectionLifecycleCallback mConnectionLifecycleCallback =
            new ConnectionLifecycleCallback() {


              	//Wird aufgerufen, wenn ein discoverer eine Verbindung anfordert.
                public void onConnectionInitiated(String endpointId , ConnectionInfo connectionInfo) {
                    
                    Nearby.Connections.acceptConnection(getGoogleApiClient(), endpointId, mPayloadCallback);	// Automatically accept the connection on both sides.
                    
                    addPlayer(new Player(connectionInfo.getEndpointName(), endpointId, false));
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.
                    		if(anzPlayer == 4)
                    			Nearby.Connections.stopAdvertising(getGoogleApiClient());	 //Anzahl Spieler vollständig --> stopAdvertising()
                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            anzPlayer--;
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
                            break;
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    // We've been disconnected from this endpoint. No more data can be
                    // sent or received.
                }
            };


            
    //-----------------------------------------------------------------------------------------------------------------------        
    //Sending Data
            
	public void sendPayloadTo(Player p, Payload payload)	//GrundFunktion um Payload an einen mitSpieler zu schicken
	{
		Nearby.Connections.sendPayload(getGoogleApiClient(), p.getEndPointId(), payload);
	}
	
	public void sendToAllParticipants(Payload payload)			//Schickt die gleiche Payload an alle Spieler
	{
		for(int i = 0; i < anzPlayer; i++)
        {
            sendPayloadTo(getPlayer(i), payload);
        }
	}
	
	public void propagateCard(Card card)
	{
        Info info = new Info(GameState.waitForHandCard, card);
        
        Payload payload = info.asPayload();
        sendToAllParticipants(payload);       
    }

}
