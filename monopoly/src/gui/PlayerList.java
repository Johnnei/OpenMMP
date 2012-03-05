package gui;

import java.awt.Dimension;
import java.util.ArrayList;
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
		ArrayList<String> pList = new ArrayList<String>();
		for(int i = 0; i < game.getPlayers().length; i++) {
			if(game.getPlayer((byte)i) != null)
				pList.add(game.getPlayer((byte)i).getUsername());
		}
		String[] plData = new String[pList.size()];
		for(int i = 0; i < plData.length; i++) {
			plData[i] = pList.get(i);
		}
		setListData(plData);
	}

}
