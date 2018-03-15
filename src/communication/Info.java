package communication;

import java.nio.charset.Charset;
import java.util.Arrays;

import cards_and_Deck.Card;
import cards_and_Deck.CardColor;
import cards_and_Deck.CardName;
import game.GameState;


/**
 * This Class is supposed to simplify the Communication between a Host and a Client, specifically to create a Jass-Turnier between devices with the NearbyAPI from Google.
 *
 * @author Jauch
 * @version 0.9
 *
 */

public class Info
{
	private byte[] byteArray;						//Das Der effektive String/Int, whatever, der übermittelt werden soll
	private GameState nextState;					//Nächste State, der das GameObject einnehmen soll
	private DataType dataType = DataType.UNDEF;		//ist message als int/String/Card zu entschlüsseln?
	
	private byte[] message;
	
	//-----------------------------------------------------------------
	//Konstruktoren um verschiedene Datentypen direkt in ByteArray zu casten.
	
	/**
	 * 
	 * @param payload Takes a received Payload and Constructs from its Data an
	 * 		Info-Object.
	 * 
	 */
	public Info(Payload payload)		//Die erhaltene Payload soll hier aufgeschlüsselt werden und in die verschiedenen Informationen unterteilt werden.
	{
		message = payload.asBytes();
		this.decodeMessage();
	}
	
	/**
	 * 
	 * @param nextState Additional Information for the StateMachine implemented in
	 * 		Client and Host.
	 * @param byteArray Contains the actual message, encoded in an Array of bytes.
	 * @param dataType Specifies the Datatype, which the byteArray represents.
	 * 
	 */
	
	public Info(GameState nextState, DataType dataType, byte[] byteArray) //Rudimentärer Konstruktor, evt unnötig, also löschen!
	{
		this.byteArray = byteArray;
		this.nextState = nextState;
		this.dataType = dataType;		
		createMessage();
	}
	
	/**
	 * 
	 * @param nextState
	 * @param num The Integer Object to encode.
	 * 
	 */
	
	public Info(GameState nextState, int num)
	{
		this.nextState = nextState;
		this.byteArray = Info.intToByteArr(num);
		this.dataType = DataType.INT;
		createMessage();
	}
	
	/**
	 * 
	 * @param nextState
	 * @param str The String Object to encode.
	 * 
	 */
	
	public Info(GameState nextState, String str)
	{
		this.nextState = nextState;
		this.byteArray = Info.StringToByteArr(str);
		this.dataType = DataType.STRING;
		createMessage();
	}
	
	/**
	 * 
	 * @param nextState
	 * @param card The Card Object to encode.
	 * 
	 */
	
	public Info(GameState nextState, Card card)
	{
		this.nextState = nextState;
		this.byteArray = card.toByteArr();
		this.dataType = DataType.CARD;
		createMessage();
	}
	
	//-----------------------------------------------------------
	//Kodierungs- und DekodierungsAlgorithmen
	
	/**
	 * This Function specifies in which order the three types of Data will be wrapped
	 * up into a single Array of bytes.
	 * Order of the byteArray = ('nextState' 'dataType' "message")
	 * Whereas GameState can be UNDEF, DataType has to be specifically defined!
	 * 
	 */
	
	private void createMessage()
	{
		if((this.byteArray != null) && (getType() != DataType.UNDEF))
		{
			this.message = new byte[byteArray.length + 2];
			message[0] = (byte)nextState.ordinal();
			message[1] = (byte)dataType.ordinal();
			for(int i = 0; i < byteArray.length; i ++)
				message[i+2] = byteArray[i];
		}
	}
	
	/**
	 * This function disassembles the byteMessage into its three types of Data.
	 * It acts as the countermeasure to createMessage().
	 * 
	 */
	
	private void decodeMessage()
	{
		if(message != null)
		{
			this.nextState = GameState.values()[message[0]];
			this.dataType = DataType.values()[message[1]];
			this.byteArray = Arrays.copyOfRange(message, 2, message.length);
		}
	}
	
	//----------------------------------------------------------------
	//Funktionen zur DateiKonvertierung:
	
	/**
	 * Returns a PayloadObject of the InfoObject, which can be instantly send to a
	 * 		Client or Host.
	 * @return Payload.
	 */
	
	public Payload asPayload()
	{
		return Payload.fromBytes(byteArray);
	}
	
	/**
	 * Converts a byteArray into a single Integer.
	 * @param byteArr is not limited in its size, so overflow problematics
	 * 		need to be considered.
	 * @return
	 */
	
	public static int byteArrToInt(byte[] byteArr)
	{
		int ret = 0;
		int exp = 1;
		for(int i = 0; i < byteArr.length; i++)
		{
			ret += Byte.toUnsignedInt(byteArr[i])*exp;
			exp = 256*exp;
		}
		return ret;
	}
	
	/**
	 * Convorts a integer into a byteArray.
	 * @param k value must be below 4 Million and positiv.
	 * @return
	 */
	
	public static byte[] intToByteArr(int k)
	{
		if(k < 4000000 && k >= 0)		//MaximalWert um überläufe des intArrays zu vermeiden.
		{
			byte[] arr = new byte[4];
			for(int i = 0; i < arr.length; i++)
			{
				arr[i] = (byte) (k%256);
				k = k/256;
			}
			return arr;
		}
		else
			return null;
	}
	
	/**
	 * Converts a byteArray into a String.
	 * @param byteArr
	 * @return
	 */
	
	public static String byteArrToString(byte[] byteArr)
	{
		return new String(byteArr, Charset.forName("UTF-8"));
	}
	
	/**
	 * Converts a String into a byte Array.
	 * @param str
	 * @return
	 */
	
	public static byte[] StringToByteArr(String str)
	{
		return str.getBytes(Charset.forName("UTF-8"));	
	}
	
	/**
	 * Converts a boolean into a byte.
	 * @param b
	 * @return
	 */
	
	public static byte boolToByte(boolean b)
	{
		if(b)
			return 1;
		else
			return 0;
	}
	
	/**
	 * Converts a byte into a boolean.
	 * @param b
	 * @return
	 */
	
	public static boolean byteToBool(byte b)
	{
		if(b ==1)
			return true;
		else
			return false;
	}
		
	
	//-----------------------------------------------------------------
	//Komplexe Getters:
	
	/**
	 * Returns a CardObject, which was reconstructed from the byteArray
	 * @return is null, if dataType wasn't CARD.
	 */
	
	public Card getCardMessage()
	{
		if(dataType == DataType.CARD)
		{
			return new Card(byteArray);
		}
		else
			return null;
	}
	
	/**
	 * Returns a StringObject, which was reconstructed from the byteArray
	 * @return is null, if dataType wasn't STRING.
	 */

	public String getStringMessage()
	{
		if(dataType == DataType.STRING)
		{
			return Info.byteArrToString(byteArray);
		}
		else
			return null;
	}
	
	/**
	 * Returns a integer, which was reconstructed from the byteArray
	 * @return is -1, if dataType wasnt INT.
	 */
	
	public int getIntMessage()
	{
		if(dataType == DataType.INT)
		{
			return Info.byteArrToInt(byteArray);
		}
		else
			return -1;
	}
	//-----------------------------------------------------------------
	//Einfache Getters:
	
	public GameState getState()
	{
		return nextState;
	}
	
	public byte[] getMessage()
	{
		return byteArray;
	}
	
	public DataType getType() 
	{
		return this.dataType;
	}
	
}
