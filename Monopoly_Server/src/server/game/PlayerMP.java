package server.game;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import server.game.Player;
import server.packet.Packet;

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
		reader = new ThreadDataReader(s.getInputStream());
	}
	
	public void addPacket(Packet p)
	{
		writer.queuePacket(p);
	}
	
	public void setUsername(String username)
	{
		
	}
	
	public void setId(byte id)
	{
		playerId = id;
	}

}
