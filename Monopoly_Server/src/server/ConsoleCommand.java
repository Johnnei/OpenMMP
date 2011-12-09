package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleCommand extends Thread
{
	public ConsoleCommand(MMP mmp)
	{
		this.mmp = mmp;
		setDaemon(false);
	}
	
	public void run()
	{
		String cmd = "";
		while(!cmd.equals("stop"))
		{
			try
			{
				cmd = in.readLine();
				if(cmd.equals("start"))
				{
					mmp.startGame();
				}
				
			} catch (IOException e)
			{
				System.err.println("CCError: " + e.getMessage());
			}
		}
	}
	
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private MMP mmp;
}