package server.game;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import server.packet.Packet;

public class ThreadDataWriter extends Thread
{
	public ThreadDataWriter(OutputStream socketOut)
	{
		outStream = new BufferedOutputStream(new DataOutputStream(socketOut));
		packetQueue = new ArrayList<Packet>();
	}
	
	public void run()
	{
		//TODO: Packet Sending
	}
	
	public void queuePacket(Packet p)
	{
		packetQueue.add(p);
	}
	
	private List<Packet> packetQueue;
	private BufferedOutputStream outStream;
}
