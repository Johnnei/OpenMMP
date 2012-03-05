package server.game;

import java.io.IOException;
import java.net.Socket;

import server.MMP;
import server.game.Player;
import server.packet.Packet;
import server.packet.Packet14ChangeMoney;
import server.packet.Packet18JailData;

public class PlayerMP extends Player
{
	private Socket socket;
	private ThreadDataReader reader;
	private ThreadDataWriter writer;
	private byte playerId;

	public PlayerMP(Socket s) throws IOException
	{
		socket = s;
		writer = new ThreadDataWriter(socket.getOutputStream());
		writer.start();
		reader = new ThreadDataReader(socket.getInputStream());
		reader.start();
	}
	
	public void setIndex(byte b) {
		index = b;
	}
	
	public void addIndex(byte value)
	{
		index += value;
		if (index > 40)
		{
			index -= 40;
			MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(playerId, 20000)); //Gain Money on passing start
		}
		else if (index < 0)
			index += 40;
	}
	
	
	public void jailPlayer()
	{
		MMP.getServer().getMonopoly().sendPacket(new Packet18JailData(playerId, (byte)0x01));
		super.jailPlayer();
	}
	
	public void releaseFromJail()
	{
		MMP.getServer().getMonopoly().sendPacket(new Packet18JailData(playerId, (byte)0x02));
		super.releaseFromJail();
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
