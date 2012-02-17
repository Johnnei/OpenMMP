package gui;

public class PlayerListGUIUpdater extends Thread
{

	public PlayerListGUIUpdater(PlayerList frame)
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
			e.printStackTrace();
		}
	}

	final PlayerList gf;

}
