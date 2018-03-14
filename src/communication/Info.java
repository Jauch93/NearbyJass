package communication;

import cards_and_Deck.Card;
import game.GameState;

//
//
//In dieser Klasse soll die übermittlung der Daten vereinfacht werden.
//Ziel: Mit einer einkommenden Payload soll direkt in der CallBackFunktion ein InfoObjekt erstellt werden, die die verschiedenen 
//		Daten aufschlüsselt und verarbeitungsbereit macht.
//
//


public class Info
{
	private byte[] byteString;			//Das Der effektive String/Int, whatever, der übermittelt werden soll
	private GameState nextState;	//Nächste State, der das GameObject einnehmen soll
	private DataType dataType = DataType.UNDEF;		//ist message als int/String/Card zu entschlüsseln?
	
	private byte[] message;
	
	public Info(Payload payload)		//Die erhaltene Payload soll hier aufgeschlüsselt werden und in die verschiedenen Informationen unterteilt werden.
	{
		byteString = payload.asBytes();
		
		//TODO: Trennung von state und message
	}
	
	public Info(GameState nextState, byte[] message, DataType dataType)
	{
		this.byteString = message;
		this.nextState = nextState;
		this.dataType = dataType;
		this.message = new byte[message.length + 2];
		
		createMessage();
	}
	
	public Info(GameState nextState, int num)
	{
		this.nextState = nextState;
		this.byteString = Info.intToByteArr(num);
		this.dataType = DataType.INT;
		createMessage();
	}
	
	public Info(GameState gameState, Card card)
	{
	//TODO: Konstruktor, der eine Karte direkt verarbeiten kann
	}
	
	public void createMessage()
	{
		if((this.byteString != null) && (getState() != GameState.UNDEF) && (getType() != DataType.UNDEF))
		{
			//Aufbau Byte-Array: ('nextState' 'dataType' "message")
			message[0] = (byte)nextState.ordinal();
			message[1] = (byte)dataType.ordinal();
			for(int i = 0; i < byteString.length; i ++)
				message[i+2] = byteString[i];
		}
	}
	
	//----------------------------------------------------------------
	//Funktionen zur DateiKonvertierung:
	
	public Payload asPayload()
	{
		return Payload.fromBytes(byteString);
	}
	
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
	
	public static byte[] intToByteArr(int k)
	{
		if(k < 4000000)		//MaximalWert um überläufe des intArrays zu vermeiden.
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
	
	//-----------------------------------------------------------------
	//Komplexe Getters:
	
	public String getStringMessage()
	{
		if(dataType == DataType.STRING)
		{
			return byteString.toString();
		}
		else
			return null;
	}
	
	public int getIntMessage()
	{
		if(dataType == DataType.INT)
		{
			return Info.byteArrToInt(message);
		}
		else
			return 0;
	}
	//-----------------------------------------------------------------
	//Einfache Getters:
	
	public GameState getState()
	{
		return nextState;
	}
	
	public byte[] getMessage()
	{
		return byteString;
	}
	
	private DataType getType() 
	{
		return this.dataType;
	}
	
	public static void main(String args[])
	{
		//TESTCASE 1: Int-Umwandlung
		int test = 3999999;
		byte[] res = Info.intToByteArr(test);
		System.out.println(Info.byteArrToInt(res));
	}
	
}
