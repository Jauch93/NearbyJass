package game;

import cards_and_Deck.*;
import communication.*;

public class Client extends Game
{
	Client(GoogleApiClient mGoogleApiClient) {
		super(mGoogleApiClient);
		// TODO Auto-generated constructor stub
		startDiscovery();
		receiveData(); //andere Spielernamen entgegennehmen
	}
	
	private Player host;
	
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

