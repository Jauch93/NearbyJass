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
		
		distributePlayerNames(); //Die Namen aller Mitspieler + weitere relevante informationen, wie ihre jeweilige Position am Tisch verteilen.
		
		do
			Tournament tournament = new Tournament();
		while(playAgain());
		
		disconnectConnections();
	}
	
	
    //-----------------------------------------------------------------------------------------------------------------------        
    //Sending Data
            

	public void sendInfoTo(Player p, Info info)
	{
		sendInfoTo(p.getEndPointId(), info);
	}
	
	public void sendInfoTo(String endPointId, Info info)
	{
		Nearby.Connections.sendPayload(getGoogleApiClient(), endPointId, info.asPayload());
	}
	
	public void sendToAllPlayers(Info info)			//Schickt die gleiche Payload an alle Spieler
	{
		for(int i = 1; i < getAnzPlayer(); i++)		//player[0] ist Host, nur clients wichtig!
        {
            sendInfoTo(getPlayer(i), info);
        }
	}
	
	public void distributePlayerNames()
	{
		//Inform Clients about incoming playerNames!
		sendToAllPlayers(new Info(GameState.ADDPLAYERNAMES, DataType.SETCLIENTSTATE, null));
		
		//Send the separate PlayerNames:
		for(int j = 0; j < getAnzPlayer(); j++) //mit der doppelten Schleife werden allen Spielern nach und nach die anderen Spieler übermittelt,
		{										//sich selbst immer zuerst, dann der Reihe nach um den Tisch herum.
			for(int i = 0; i < getAnzPlayer(); i++)
			{
				String id = getPlayer(i).getEndPointId(); //receiver
				if(id != null)
					sendInfoTo(id, new Info(GameState.PLAYERNAME, getPlayer((i+j)%4).getName()));	//playerName
			}
		}
		
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


            


}
