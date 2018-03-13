package cards_and_Deck;

public class Card 
{
	private CardColor color;
	private CardName name;
	private int points = 0;
	private int value = 0;
	private String pictureURL = null; //Link auf das anzuzeigende bild im GUI
	private boolean playable = true;		//wichtig, sobald die Karte auf der Hand ist, und soll angeben, ob die Karte, je nach Spielsituation noch spielbar ist.
	
	Card(byte[] bytes)		//Einfacherer Konstruktor, um die Karte aus der Payload zu erzeugen.
	{
		
	}
	
	Card(CardColor color, CardName name, int points, int value)
	{
		this.color = color;
		this.name = name;
		this.points = points;
		this.value = value;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void setPoints(int points)
	{
		this.points = points;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public String getPictureURL()
	{
		return pictureURL;
	}
	
	public void setCardPlayable(boolean b)
	{
		playable = b;
	}
	
	public boolean isCardPlayable()
	{
		return playable;
	}
	
	
	public void setPictureURL()
	{
		//TODO: Hier kommt die Logik rein, die den einzelnen Karten, anhand ihrer Namen und Farben das richtige Bild zuordnet.
		//Hier wird auch geregelt, ob Deutschschweizer Karten, oder französische angezeigt werden sollen.
	}

}
