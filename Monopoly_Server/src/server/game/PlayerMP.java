package server.game;

import java.io.IOException;
import java.net.Socket;
import server.game.Player;
import server.packet.Packet;

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
		reader = new ThreadDataReader(s.getInputStream());
		writer.run();
		reader.run();
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
