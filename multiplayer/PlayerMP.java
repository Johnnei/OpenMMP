package multiplayer;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import multiplayer.packet.Packet;

public class PlayerMP
{
	Socket socket;
	private List<Packet> toSend;
	private DataOutputStream out;

	public PlayerMP(int id, Socket s) throws IOException
	{
		socket = s;
		toSend = new ArrayList<Packet>();
		out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}

	public void add(Packet p)
	{
		toSend.add(p);
	}

	public void sendAllPacket() throws IOException
	{
		while (toSend.size() > 0)
		{
			Packet.sendPacket(toSend.remove(0), out);
		}
	}

}
