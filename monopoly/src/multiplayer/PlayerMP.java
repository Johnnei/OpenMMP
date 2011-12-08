package multiplayer;

import java.io.IOException;
import java.net.Socket;
import monopoly.Player;
import multiplayer.packet.Packet;

public class PlayerMP extends Player
{
	private Socket socket;
	private ThreadDataReader reader;
	private ThreadDataWriter writer;
	private byte playerId;

	public PlayerMP(String username, byte id, int colorC)
	{
		setUsername(username);
		setId(id);
		setColor(colorC);
	}
	
	public PlayerMP(Socket s) throws IOException
	{
		socket = s;
		writer = new ThreadDataWriter(socket.getOutputStream());
		writer.start();
		reader = new ThreadDataReader(socket.getInputStream());
		reader.start();
		playerId = 127;
	}
	
	public void addPacket(Packet p)
	{
		writer.queuePacket(p);
	}
	
	public void setId(byte id)
	{
		playerId = id;
	}
	
	public byte getId()
	{
		return playerId;
	}
	
	public boolean hasId()
	{
		return playerId != 127;
	}

}
