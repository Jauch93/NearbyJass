package game;
import communication.GoogleApiClient;

public abstract class Game 
{
	private Player myself;
	private GoogleApiClient mGoogleApiClient;
	
	class Participant
	{
		private String name;
		private String endpointId = null;
		
		public String getName()
		{
			return name;
		}
		
		public void setName(String name)
		{
			this.name = name;
		}
		
		public void setEndpointId(String id)
		{
			this.endpointId = id;
		}
		
		public String getEndpointId()
		{
			return endpointId;
		}

	}
	
	Game(GoogleApiClient mGoogleApiClient)
	{
		myself = new Player("Host");
		this.mGoogleApiClient = mGoogleApiClient;
	}
	
	public GoogleApiClient getGoogleApiClient()
	{
		return mGoogleApiClient;
	}
	
	public Player getMyself()
	{
		return myself;
	}

	//TODO: Hier wird der Spielablauf grob beschrieben. In abgeleiteten Klassen soll das Spielgeschehen separat für den Client
	//      und den Host beschrieben werden.
}
