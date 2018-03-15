package cards_and_Deck;

import java.util.Arrays;

/**
 * This class represents a PlayCard.
 */

import communication.Info;

public class Card 
{
	private CardColor color;
	private CardName name;
	private int points = 0;
	private int value = 0;
	private String pictureURL = " "; //Link auf das anzuzeigende bild im GUI
	private boolean playable = true;		//wichtig, sobald die Karte auf der Hand ist, und soll angeben, ob die Karte, je nach Spielsituation noch spielbar ist.
	
	/**
	 * Allows to construc a Card from a byteArray.
	 * @param bytes a ByteArray representing a CardObject.
	 */
	public Card(byte[] bytes)		//Einfacherer Konstruktor, um die Karte aus der Payload zu erzeugen.
	{
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
	
	/**
	 * 
	 * @return a representation of a Card in form of a ByteArray.
	 */
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
	
	/**
	 * Simple Constructor.
	 * @param color 
	 * @param name
	 * @param points
	 * @param value
	 */
	
	public Card(CardColor color, CardName name, int points, int value)
	{
		this.color = color;
		this.name = name;
		this.points = points;
		this.value = value;
	}
	
	/**
	 * 
	 * @return The number of Points, that this Card is worth.
	 */
	
	public int getPoints()
	{
		return points;
	}
	
	/**
	 * allows to set the number of Points this card is worth.
	 * @param points
	 */
	
	public void setPoints(int points)
	{
		this.points = points;
	}
	
	/**
	 * Represents the Strength of the Card.
	 * @return
	 */
	
	public int getValue()
	{
		return value;
	}
	/**
	 * Allows to set the Strength of the Card.
	 * @param value
	 */
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	/**
	 * 
	 * @return A pointer to the Bitmap used to show this specific Card in a GUI
	 */
	
	public String getPictureURL()
	{
		return pictureURL;
	}
	
	/**
	 * Allows to set, wheter this Card is momentarily playable.
	 * @param b
	 */
	
	public void setCardPlayable(boolean b)
	{
		playable = b;
	}
	
	/**
	 * 
	 * @return returns true, if this card is playable.
	 */
	public boolean isCardPlayable()
	{
		return playable;
	}
	
	/**
	 * Sets the Pointer to a bitmap, which graphically represents this CardObject.
	 */
	
	public void setPictureURL()
	{
		//TODO: Hier kommt die Logik rein, die den einzelnen Karten, anhand ihrer Namen und Farben das richtige Bild zuordnet.
		//Hier wird auch geregelt, ob Deutschschweizer Karten, oder französische angezeigt werden sollen.
	}
	
	/**
	 * 
	 * @return the CardColor in form of a enum.
	 */
	
	public CardColor getCardColor()
	{
		return color;
	}
	
	/**
	 * the CardName in form of a enum.
	 * @return
	 */
	public CardName getCardName()
	{
		return name;
	}

}
