package communication;

//
// DummyKlasse --> Stub für Payload zur Datenübertragung.
//

public class Payload 
{
	public byte[] asBytes()
	{
		return null;
	}
	
	public static Payload fromBytes(byte[] bytes)
	{
		return null;
	}
	
	public int getType()
	{
		return 0;
	}
}
