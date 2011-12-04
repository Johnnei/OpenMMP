package server.game;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import server.MMP;
import server.packet.Packet;

public class ThreadDataWriter extends Thread
{
	public ThreadDataWriter(OutputStream socketOut)
	{
		outStream = new DataOutputStream(socketOut);
		packetQueue = new ArrayList<Packet>();
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				if(packetQueue.size() > 0)
				{
					Packet p = packetQueue.remove(0);
					MMP.Log("Sending Packet " + p.getPacketID());
					p.writeData(outStream);
				}
				sleep(1);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void queuePacket(Packet p)
	{
		packetQueue.add(p);
	}
	
	private List<Packet> packetQueue;
	private DataOutputStream outStream;
}
