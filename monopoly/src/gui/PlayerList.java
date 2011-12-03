package gui;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JList;
import monopoly.Game;
import monopoly.Player;

public class PlayerList extends JList
{

	private Game game;

	public PlayerList(Game game, int WIDTH, int HEIGHT)
	{
		this.game = game;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		new PlayerListGUIUpdater(this).start();
	}

	public void doUpdate()
	{
		Vector<String> vector = new Vector<String>();
		Player[] players = game.getPlayers();
		for (int i = 0; i < players.length; i++)
		{
			if (players[i] != null)
			{
				vector.add(players[i].Username() + " €" + players[i].MoneyString());
			}
		}
		setListData(vector);
	}

}
