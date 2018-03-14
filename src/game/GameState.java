package game;

public enum GameState 
{
	WAITING,		//Nothing to do right now.
	DATATOFETCH,	//New Payload received and InfoObject created from it, but still needs to be processed.
	sendTrumpf,
	waitForTrumpf,
	sendPlayedCard,
	waitForPlayedCard,
	waitForHandCard,
	waitForPoints,
	//TODO: vervollständigen!
	UNDEF
}
