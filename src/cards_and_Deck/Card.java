package cards_and_Deck;

import java.util.Arrays;

import communication.Info;

public class Card 
{
	private CardColor color;
	private CardName name;
	private int points = 0;
	private int value = 0;
	private String pictureURL = " "; //Link auf das anzuzeigende bild im GUI
	private boolean playable = true;		//wichtig, sobald die Karte auf der Hand ist, und soll angeben, ob die Karte, je nach Spielsituation noch spielbar ist.
	
	public Card(byte[] bytes)		//Einfacherer Konstruktor, um die Karte aus der Payload zu erzeugen.
	{
		//TODO: implement!
		color = CardColor.values()[bytes[0]];
		name = CardName.values()[bytes[1]];
		byte[] tmp = Arrays.copyOfRange(bytes, 2, 5);
		points = Info.byteArrToInt(tmp);
		tmp = Arrays.copyOfRange(bytes, 6, 9);
		value = Info.byteArrToInt(tmp);
		playable = Info.byteToBool(bytes[10]);
		tmp = Arrays.copyOfRange(bytes, 11, bytes.length);
		pictureURL = Info.byteArrToString(tmp);
	}
	
	public byte[] toByteArr() 
	{
		byte[] ret = new byte[30];
		ret[0] = (byte)color.ordinal();
		ret[1] = (byte)name.ordinal();
		byte[] p = Info.intToByteArr(points);
		for(int i = 0; i < p.length; i++)
			ret[i+2] = p[i];
		byte[] v = Info.intToByteArr(value);
		for(int i = 0; i < v.length; i++)
			ret[i+6] = v[i];
		ret[10] = Info.boolToByte(playable);
		byte[] pURL = Info.StringToByteArr(pictureURL);
		for(int i = 0; i < pURL.length; i++)
			ret[i+11] = pURL[i];
		
		return ret;
	}
	
	public Card(CardColor color, CardName name, int points, int value)
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
	
	public static void main(String args[])
	{
		Card test = new Card(CardColor.SCHALLE, CardName.OBER, 42, 69);
		byte[] t = test.toByteArr();
		Card solver = new Card(t);
		System.out.println(solver.getValue());
	}
}
