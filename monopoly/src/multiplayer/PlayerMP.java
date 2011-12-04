package multiplayer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import monopoly.Player;
import multiplayer.packet.Packet;
import multiplayer.packet.Packet03Username;

public class PlayerMP extends Player
{
	private Socket socket;
	private ThreadDataReader reader;
	private ThreadDataWriter writer;
	private List<Packet> toSend;
	private byte playerId;

	public PlayerMP(Socket s) throws IOException
	{
		socket = s;
		toSend = new ArrayList<Packet>();
		writer = new ThreadDataWriter(s.getOutputStream());
		writer.start();
		reader = new ThreadDataReader(s.getInputStream());
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
