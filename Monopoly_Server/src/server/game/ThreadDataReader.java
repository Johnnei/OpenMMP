package server.game;

import java.io.DataInputStream;
import java.io.InputStream;

import server.packet.Packet;

public class ThreadDataReader extends Thread
{

	public ThreadDataReader(InputStream socketIn)
	{
		super("Network Data Reader");
		setDaemon(true);
		inStream = new DataInputStream(socketIn);
	}

	public void run()
	{
		while(true)
		{
			try
			{
				if (inStream.available() > 0)
				{
					Packet p = Packet.readPacket(inStream);
					p.handle();
				}
				sleep(1);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private DataInputStream inStream;
}
