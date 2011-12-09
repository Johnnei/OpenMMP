package server;

import monopoly.Turn;
import server.game.PlayerMP;
import server.packet.Packet;

public class ServerGame
{
	
	public ServerGame()
	{
		players = new PlayerMP[6];
		turn = new Turn();
	}
	
	public void setPlayer(PlayerMP player, byte slot)
	{
		players[slot] = player;
	}
	
	public PlayerMP[] getPlayers()
	{
		return players;
	}
	
	public PlayerMP getPlayer(int i)
	{
		if(players[i] == null)
			return null;
		return players[i];
	}
	
	public void setUsername(byte b, String s)
	{
		players[b].setUsername(s);
	}
	
	public void setColor(byte b, int i)
	{
		players[b].setColor(i);
	}
	
	public void setPhase(int i)
	{
		phase = (byte)i;
	}
	
	public boolean isPhase(int i)
	{
		return (byte)i == phase;
	}
	
	public void sendPacket(Packet p, byte id)
	{
		if(players[id] != null)
			players[id].addPacket(p);
	}
	
	public void sendPacket(Packet p)
	{
		for(int i = 0; i < 6; i++)
		{
			if(players[i] == null)
				continue;
			players[i].addPacket(p);
		}
	}
	
	private byte phase;
	private PlayerMP[] players;
	public Turn turn;
	
}
