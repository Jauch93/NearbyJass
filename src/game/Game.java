package game;
import communication.GoogleApiClient;
import communication.Info;

public abstract class Game 
{
	private Player[] player = new Player[4];
	private int anzPlayer = 0;
	private GoogleApiClient mGoogleApiClient;
	private GameState gameState;
	private String serviceId = "69"; //Immer noch unklar, was das genau macht.
	private Info data = null;
	
	public Game(GoogleApiClient mGoogleApiClient)
	{
		this.mGoogleApiClient = mGoogleApiClient;
		String myName = "MeinName"; //TODO: Name des Localspielers irgendwie einfügen.
		addPlayer(new LocalPlayer(myName));
	}
	
	public String getServiceId()
	{
		return serviceId;
	}
	
	public GoogleApiClient getGoogleApiClient()
	{
		return mGoogleApiClient;
	}
	
	public GameState getGameState()
	{
		return gameState;
	}
	
	public void setGameState(GameState state)
	{
		this.gameState = state;
	}
	
	public Info getData()
	{
		return data;
	}
	
	
	//-----------------------------------------------------------
	//Player-Operationen:
	
	public Player getPlayer(int i)
	{
		return player[i];
	}
	
	public LocalPlayer getMyself()
	{
		return (LocalPlayer) player[0];
	}
	
	public void addPlayer(Player p)
	{
		if(anzPlayer < 4)
		{
			player[anzPlayer] = p;
			anzPlayer++;
		}
	}

	public Player searchByEndpointId(String id)
	{
		for(int i = 0; i < player.length; i++)
		{
			if(id.equals(player[i].getEndPointId()))
				return player[i];
		}
		return null;
	}
	
	public Player searchByName(String name)
	{
		for(int i = 0; i < player.length; i++)
		{
			if(name.equals(player[i].getName()))
				return player[i];
		}
		return null;
	}
	
	public int getAnzPlayer()
	{
		return anzPlayer;
	}
	//----------------------------------------------------------------------------
	//Basic connect functions
	
	
	public PayloadCallback mPayloadCallback = new PayloadCallback() 
	{
        @Override
        public void onPayloadReceived(String s, Payload payload)
        {
            data = new Info(payload);				//Erhaltene Payload wird als InfoObjekt im Attribut data angelegt
            gameState = GameState.DATATOFETCH;		//gameState aktualisieren, sodass Game weiss, dass es frische Daten erhalten hat.	
        }
        
	};

	
}
