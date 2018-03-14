package communication;

import java.nio.charset.Charset;
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
	private byte[] byteArray;			//Das Der effektive String/Int, whatever, der übermittelt werden soll
	private GameState nextState;	//Nächste State, der das GameObject einnehmen soll
	private DataType dataType = DataType.UNDEF;		//ist message als int/String/Card zu entschlüsseln?
	
	private byte[] message;
	
	//-----------------------------------------------------------------
	//Konstruktoren um verschiedene Datentypen direkt in ByteArray zu casten.
	
	public Info(Payload payload)		//Die erhaltene Payload soll hier aufgeschlüsselt werden und in die verschiedenen Informationen unterteilt werden.
	{
		message = payload.asBytes();
		this.decodeMessage();
		
		//TODO: testing
	}
	
	public Info(GameState nextState, byte[] message, DataType dataType) //Rudimentärer Konstruktor
	{
		this.byteArray = message;
		this.nextState = nextState;
		this.dataType = dataType;		
		createMessage();
	}
	
	public Info(GameState nextState, int num)			//Verarbeitet integer Daten
	{
		this.nextState = nextState;
		this.byteArray = Info.intToByteArr(num);
		this.dataType = DataType.INT;
		createMessage();
	}
	
	public Info(GameState nextState, String str)
	{
		this.nextState = nextState;
		this.byteArray = Info.StringToByteArr(str);
		this.dataType = DataType.STRING;
		createMessage();
	}
	
	public Info(GameState nextState, Card card) //TODO: testing!
	{
		this.nextState = nextState;
		this.byteArray = card.toByteArr();
		this.dataType = DataType.CARD;
		createMessage();
	}
	
	//-----------------------------------------------------------
	//Kodierungs- und DekodierungsAlgorithmen
	
	private void createMessage()
	{
		if((this.byteArray != null) && (getType() != DataType.UNDEF)) //GameState darf UNDEF sein.
		{
			this.message = new byte[byteArray.length + 2];
			
			//Aufbau Byte-Array: ('nextState' 'dataType' "message")
			message[0] = (byte)nextState.ordinal();
			message[1] = (byte)dataType.ordinal();
			for(int i = 0; i < byteArray.length; i ++)
				message[i+2] = byteArray[i];
		}
	}
	
	private void decodeMessage()		//Zerlegt das byteArray message in seine einzelnen bestandteile.//TODO: testing!
	{
		if(message != null)
		{
			this.nextState = GameState.values()[message[0]];
			this.dataType = DataType.values()[message[1]];
			for(int i = 2; i < message.length; i++)
				byteArray[i-2] = message[i];
		}
	}
	
	//----------------------------------------------------------------
	//Funktionen zur DateiKonvertierung:
	
	public Payload asPayload()
	{
		return Payload.fromBytes(byteArray);
	}
	
	//Integer-Byte Konvertierungen:
	
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
	
	//String-Byte Konvertierungen:
	
	public static String byteArrToString(byte[] byteArr)
	{
		return new String(byteArr, Charset.forName("UTF-8"));
	}
	
	public static byte[] StringToByteArr(String str)
	{
		return str.getBytes(Charset.forName("UTF-8"));	
	}
	
	public static byte boolToByte(boolean b)
	{
		if(b)
			return 1;
		else
			return 0;
	}
	
	public static boolean byteToBool(byte b)
	{
		if(b ==1)
			return true;
		else
			return false;
	}
		
	
	//-----------------------------------------------------------------
	//Komplexe Getters:
	
	public Card getCardMessage()
	{
		if(dataType == DataType.CARD)
		{
			return new Card(byteArray);
		}
		else
			return null;
	}

	public String getStringMessage()
	{
		if(dataType == DataType.STRING)
		{
			return Info.byteArrToString(byteArray);
		}
		else
			return null;
	}
	
	public int getIntMessage()
	{
		if(dataType == DataType.INT)
		{
			return Info.byteArrToInt(byteArray);
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
		return byteArray;
	}
	
	private DataType getType() 
	{
		return this.dataType;
	}
	
	public static void main(String args[])
	{
		//TESTCASE 1: Int-Umwandlung
		Info test = new Info(GameState.UNDEF, 4239);
		System.out.println(test.getIntMessage());
	}
	
}
