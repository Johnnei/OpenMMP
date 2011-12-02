package server;

import java.io.IOException;
import java.net.Socket;

import multiplayer.PlayerMP;

public class ThreadPlayerAccept extends Thread
{

	public ThreadPlayerAccept(MMP mmp)
	{
		this.mmp = mmp;
		setDaemon(true);
		run();
	}
	
	public void run()
	{
		while(nextId < 6)
		{
			try
			{
				Socket s = mmp.getSocket().accept();
				PlayerMP pmp = new PlayerMP(nextId++, s); 
				mmp.addPlayer(pmp);
			} catch (IOException e)
			{
			}
		}
	}
	
	private byte nextId = 0;
	private MMP mmp;
}
