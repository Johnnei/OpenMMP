package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;
import monopoly.Game;

public class PlayerList extends JComponent
{

	String[] playerList;
	
	public PlayerList(int WIDTH, int HEIGHT)
	{
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		new PlayerListGUIUpdater(this).start();
	}

	public void doUpdate()
	{
		ArrayList<String> pList = new ArrayList<String>();
		for(int i = 0; i < Game.getMonopoly().getPlayers().length; i++) {
			if(Game.getMonopoly().getPlayer((byte)i) != null)
				pList.add(Game.getMonopoly().getPlayer((byte)i).getUsername());
		}
		String[] plData = new String[pList.size()];
		for(int i = 0; i < plData.length; i++) {
			plData[i] = pList.get(i);
		}
		playerList = plData;
	}
	
	public void paint(Graphics g) {
		
	}

}
