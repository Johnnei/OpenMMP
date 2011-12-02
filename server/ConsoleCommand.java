package server;

public class ConsoleCommand extends Thread
{
	public ConsoleCommand(MMP mmp)
	{
		this.mmp = mmp;
		setDaemon(false);
		run();
	}
	
	public void run()
	{
		
	}
	
	private MMP mmp;
}
