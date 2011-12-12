package monopoly;

public class Turn
{
	byte playerId;
	boolean canRoll;
	
	public Turn()
	{
		
	}
	
	public byte getID()
	{
		return playerId;
	}
	
	public void ResetTurn(byte id)
	{
		playerId = id;
		canRoll = true;
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
