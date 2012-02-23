package monopoly;

import java.util.ArrayList;
import java.util.List;

import multiplayer.PlayerMP;
import multiplayer.packet.Packet07RollDice;

public class Turn
{

	private byte playerId;
	boolean finished;
	List<GameEvent> events;

	public Turn(PlayerMP player)
	{
		playerId = player.getId();
		finished = false;
		events = new ArrayList<GameEvent>();
	}
	
	public byte getId()
	{
		return playerId;
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
		if (!myTurn(Game.Monopoly().getMyID()))
			return;
		Game.Monopoly().getMyPlayer().addPacket(new Packet07RollDice());
	}

}
