package server.game;

import java.io.IOException;
import java.net.Socket;

import server.MMP;
import server.game.Player;
import server.packet.Packet;
import server.packet.Packet14ChangeMoney;

public class PlayerMP extends Player
{
	private Socket socket;
	private ThreadDataReader reader;
	private ThreadDataWriter writer;
	private byte playerId;

	public PlayerMP(Socket s) throws IOException
	{
		socket = s;
		writer = new ThreadDataWriter(s.getOutputStream());
		writer.start();
		reader = new ThreadDataReader(s.getInputStream());
		reader.start();
	}
	
	public void addIndex(byte value)
	{
		index += value;
		if (index > 40)
		{
			index -= 40;
			MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(playerId, 20000)); //Gain Money on passing start
		}
		else if (index < 0)
			index += 40;
	}
	
	public void setID(byte id)
	{
		playerId = id;
	}
	
	public byte getId()
	{
		return playerId;
	}
	
	public void addPacket(Packet p)
	{
		writer.queuePacket(p);
	}
	
	public void setId(byte id)
	{
		playerId = id;
	}

}
