package multiplayer;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import multiplayer.packet.Packet;

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
					Packet.sendPacket(packetQueue.remove(0), outStream);
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
