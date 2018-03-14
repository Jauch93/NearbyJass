package game;

import cards_and_Deck.*;

public class Player 
{
	private String name;
	private String endPointId = null;
	private boolean isHost = false;
	
	//---------------------------------------------------------------------
	//C-Tors
	
	public Player (String name, String endPointId, boolean isHost)
	{
		this.name = name;
		this.endPointId = endPointId;
		this.isHost = isHost;
	}
	
	public Player(String name)
	{
		this.name = name;
	}
	
	public Player()
	{
		this.name = null;
	}
	
	//---------------------------------------------------------------------
	//
	
	

	
	
	//---------------------------------------------------------------------
	//Setters und Getters
	
	public void setHost(boolean h)
	{
		this.isHost = h;
	}
	
	public boolean isHost()
	{
		return isHost;
	}
	
	public void setEndPointId(String id)
	{
		this.endPointId = id;
	}
	
	public String getEndPointId()
	{
		return endPointId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

}
