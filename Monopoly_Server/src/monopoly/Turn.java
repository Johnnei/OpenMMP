package monopoly;

public class Turn
{
	byte playerId;
	boolean canRoll;
	byte turnCount;
	
	public Turn()
	{
		turnCount = 0;
	}
	
	public byte getID()
	{
		return playerId;
	}
	
	public void ResetTurn(byte id)
	{
		if(playerId == id) //Same ID again: Double Eyes!
			turnCount++;
		else
			turnCount = 1;
		playerId = id;
		canRoll = true;
	}
	
	public boolean sendJail()
	{
		return turnCount > 3;
	}
	
	public boolean canRollDice()
	{
		return canRoll;
	}
	
	public void rollDice()
	{
		canRoll = false;
	}
}
