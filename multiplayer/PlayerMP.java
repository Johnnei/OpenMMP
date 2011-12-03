package multiplayer;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import monopoly.Player;
import multiplayer.packet.Packet;

public class PlayerMP extends Player
{
	Socket socket;
	private List<Packet> toSend;
	private DataOutputStream out;
	private byte playerId;

	public PlayerMP(String username, int color, Socket s) throws IOException
	{
		super(username, color);
		socket = s;
		toSend = new ArrayList<Packet>();
		out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	
	public void setPlayerId(byte id)
	{
		playerId = id;
	}

	public void addPacket(Packet p)
	{
		toSend.add(p);
	}

	public void sendAllPackets() throws IOException
	{
		while (toSend.size() > 0)
		{
			Packet.sendPacket(toSend.remove(0), out);
		}
	}

}
