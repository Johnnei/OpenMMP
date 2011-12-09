package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JComponent;
import monopoly.Game;
import gui.buttons.*;

public class PlayerInput extends JComponent
{

	Game game;
	int width;
	int height;

	public PlayerInput(Game game, int width, int height, String player)
	{
		this.game = game;
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setBackground(Color.BLACK);
		setLayout(new GridLayout(0, 1));
		add(new ButtonRollDice());
		add(new ButtonBuyStreet());
		add(new ButtonBuyHouse());
		add(new ButtonTrade());
		add(new ButtonNextTurn());
	}
}
