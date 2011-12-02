package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;

import monopoly.Game;

public class RightPanel extends JComponent
{

	public RightPanel(Game game, int width, int height, String user)
	{
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		add(new PlayerList(game, width, height / 4), "North");
		add(new PlayerInput(game, width, height / 4, user), "South");
	}

}
