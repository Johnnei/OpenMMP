package monopoly;

import java.util.ArrayList;
import java.util.List;

import multiplayer.PlayerMP;

public class Turn
{

	private byte playerId;
	private boolean rolledDice;
	boolean finished;
	List<GameEvent> events;

	public Turn(PlayerMP player)
	{
		playerId = player.getId();
		rolledDice = false;
		finished = false;
		events = new ArrayList<GameEvent>();
	}

	public boolean myTurn(byte id)
	{
		return playerId == id;
	}

	public void addEvent(GameEvent s)
	{
		events.add(s);
	}

	public GameEvent getEvent()
	{
		if (events.size() > 0)
		{
			return events.remove(0);
		}
		else
		{
			return GameEvent.none;
		}
	}

	public void rollDice()
	{
		if (rolledDice)
			return;
		rolledDice = true;
		//TODO: Send request to roll dices
	}

}
