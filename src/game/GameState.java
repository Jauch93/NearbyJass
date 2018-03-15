package game;

public enum GameState 
{
	WAIT,		//Nothing to do right now.
	DATATOFETCH,	//New Payload received and InfoObject created from it, but still needs to be processed.
	HANDCARD,	//The received Card is meant to be picked up by the LocalPlayer.
	PLAYEDCARD, //The received Card was played by another player an is to be put on the Table.
	PLAYERNAME,
	POINTS,
	ENEMYPOINTS,
	
	//--------------------------------------------------------------
	//Funktionen, die beim Client aufgerufen werden sollen:
	CHOOSECARD,
	CHOOSETRUMPF,
	ADDPLAYERNAMES,
	
	//--------------------------------------------------------------
	//Funktionen, die beim Host aufgerufen werden sollen (bsp zum SCHIEBEN)
	

	UNDEF,
	EXIT
}
