package game;

import cards_and_Deck.*;
import communication.*;

public class Client extends Game
{
	Client(GoogleApiClient mGoogleApiClient) {
		super(mGoogleApiClient);
		
		startDiscovery();
		
		while(host == null) {}		//Warten, bis ein Host gefunden wurde.
		
		startStateMachine();
	}
	
	private Player host;
	
	public void startStateMachine()
	{
		while(true)
		{
			switch(getGameState())
			{
			case WAIT:
				break;
			case DATATOFETCH:
				processData();			//erhaltene Daten verarbeiten, zum beispiel karte auf hand ablegen, usw.
				break;
				
			case CHOOSECARD:
				//TODO: implement function to let player choose a card!
				setGameState(GameState.WAIT);	//function executed, going back into wait-State
				break;
			case CHOOSETRUMPF:
				//TODO: implement function to let player choose a Trumpf!
				setGameState(GameState.WAIT);
				break;
			case ADDPLAYERNAMES:
				addPlayerNames();
				setGameState(GameState.WAIT);
				break;
			}
			
			if(getGameState() == GameState.EXIT)
				break;
		}
	}
	
	private void addPlayerNames() 
	{
		for(int i = 0; i < 4; i++)
		{
			while(getGameState() != GameState.DATATOFETCH) {} //Warte auf Daten
			String name = getData().getStringMessage();
			if(name != getMyself().getName());
			{
				addPlayer(new Player(name));
				if(name == host.getName())
					getPlayer(i).setHost(true);
			}
		}
	}

	public void processData()
	{
		switch(getData().getType())
		{
		case CARD: //Unterscheiden zwischen HandKarten und Karten, die auf Table abgelegt werden.
			switch(getData().getState())
			{
			case HANDCARD:
				getMyself().takeCard(getData().getCardMessage());
				break;
			case PLAYEDCARD:
				//TODO: Lege die erhaltene Karte auf Table ab.
				break;
			}
			setGameState(GameState.WAIT);	//Daten wurden verarbeitet.
			break;
			
		case INT:
			switch(getData().getState())
			{
			case POINTS:
				//TODO: füge die erhaltene Zahl den eigenen Punkten hinzu!
				break;
			case ENEMYPOINTS:
				//TODO: füge die erhaltene Zahl den gegnerischen Punkten hinzu!
				break;
			}
			setGameState(GameState.WAIT);
			break;
			
		case STRING:
			
			break;
			
		case SETCLIENTSTATE:					//setzen des übergebenen States, sodass dieser Fall in der StateMaschine abgearbeitet werden kann.
			setGameState(getData().getState());
			break;
		}
	}
	
	//-------------------------------------------------------------------------------------------
	//ConnectionManagement
	
	 public void startDiscovery()
     {
         Nearby.Connections.startDiscovery(getGoogleApiClient(),
                 getServiceId(),
                 mEndpointDiscoveryCallback,
                 new DiscoveryOptions(P2P_CLUSTER))
                 .setResultCallback(new ResultCallback<Status>() {
                     @Override
                     public void onResult(@NonNull Status status) {
                         if (status.isSuccess()) {
                             //SUCCESS: Discovery startet.
                         } else {
                             //ERROR: Discover unable to start.
                         }
                     }
                 });
     }
	 
	 public final EndpointDiscoveryCallback mEndpointDiscoveryCallback =
             new EndpointDiscoveryCallback() {
                 @Override
                 public void onEndpointFound( //Host gefunden!
                         String endpointId, DiscoveredEndpointInfo discoveredEndpointInfo)
                 {
                	 //Informationen zu gefundenem Host ablegen.
                     host = new Player(discoveredEndpointInfo.getEndpointName());
                     host.setEndPointId(endpointId);
                     host.setHost(true);

                     Nearby.Connections.requestConnection(getGoogleApiClient(),
                             getMyself().getName(),       //Hier wird der Name des Clients an den Host übergeben.
                             endpointId,
                             mConnectionLifecycleCallback)
                             .setResultCallback(new ResultCallback<Status>() {
                                 @Override
                                 public void onResult(@NonNull Status status) {
                                     if (status.isSuccess()) {
                                         //A Connection with the Host has been requestet.
                                     } else {
                                         //Error: unable to request a connection
                                     }
                                 }
                             });

                 }
                 
                 @Override
                 public void onEndpointLost(String endpointId) {
                     //Informationen über alten Host löschen!
                 }
             };

     public final ConnectionLifecycleCallback mConnectionLifecycleCallback =
             new ConnectionLifecycleCallback() {


                 @Override
                 public void onConnectionInitiated(String endpointId , ConnectionInfo connectionInfo) {
                     // Automatically accept the connection on both sides.
                     Nearby.Connections.acceptConnection(getGoogleApiClient(), endpointId, mPayloadCallback);
                 }

                 @Override
                 public void onConnectionResult(String endpointId, ConnectionResolution result) {
                     switch (result.getStatus().getStatusCode()) {
                         case ConnectionsStatusCodes.STATUS_OK:
                             // We're connected! Can now start sending and receiving data.
                             Nearby.Connections.stopDiscovery(getGoogleApiClient());		//stop Discovering
                             break;
                         case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                             //Connection rejected
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



	//-----------------------------------------------------------------------------------------------
	//sending data:
	 
	public void sendToHost(Info data)		//GrundFunktion um Daten an Host zu schicken
	{
            Nearby.Connections.sendPayload(getGoogleApiClient(), host.getEndpointId(), data.asPayload());
	}
	

}

