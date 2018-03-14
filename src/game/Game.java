package game;
import communication.GoogleApiClient;
import communication.Info;

public abstract class Game 
{
	private Player[] player = new Player[4];
	private int anzPlayer = 0;
	private GoogleApiClient mGoogleApiClient;
	private GameState gamestate;
	private String serviceId = "69"; //Immer noch unklar, was das genau macht.
	
	public Game(GoogleApiClient mGoogleApiClient)
	{
		this.mGoogleApiClient = mGoogleApiClient;
		String myName = "MeinName"; //TODO: Name des Localspielers irgendwie einfügen.
		player[0] = new LocalPlayer(myName);
		anzPlayer++;
	}
	
	public String getServiceId()
	{
		return serviceId;
	}
	
	public GoogleApiClient getGoogleApiClient()
	{
		return mGoogleApiClient;
	}
	
	
	//-----------------------------------------------------------
	//Player-Operationen:
	
	public Player getPlayer(int i)
	{
		return player[i];
	}
	
	public Player getMyself()
	{
		return player[0];
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
	//----------------------------------------------------------------------------
	//Basic connect functions
	
	
	public PayloadCallback mPayloadCallback = new PayloadCallback() 
	{
        @Override
        public void onPayloadReceived(String s, Payload payload)
        {
            Info data = new Info(payload);
            gamestate = GameState.DATATOFETCH;
        }
        
	};

	
}
