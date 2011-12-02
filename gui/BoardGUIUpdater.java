package gui;

public class BoardGUIUpdater extends Thread
{

	public BoardGUIUpdater(Board frame)
	{
		setDaemon(true);
		gf = frame;
	}

	public void run()
	{
		try
		{
			while (true)
			{
				sleep(100);
				gf.doUpdate();
			}
		} catch (Exception e)
		{
		}
	}

	final Board gf;

}
