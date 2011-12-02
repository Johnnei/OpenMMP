package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JComponent;
import javax.imageio.ImageIO;

import monopoly.Game;
import monopoly.Player;

public class Board extends JComponent
{

	private Game game;
	private int width;
	private int height;

	public Board(Game game, int WIDTH, int HEIGHT)
	{
		try
		{
			this.game = game;
			width = WIDTH;
			height = HEIGHT;
			setPreferredSize(new Dimension(width, height));
			setMinimumSize(new Dimension(width, height));
			setMaximumSize(new Dimension(width, height));
			setBackground(Color.BLACK);
			new BoardGUIUpdater(this).start();
		} catch (Exception e)
		{
			e.printStackTrace();
			Game.Log(e.getMessage());
		}
	}

	public void doUpdate()
	{
		repaint();
	}

	public int[] getCords(int i)
	{
		int x = 0;
		int y = 0;
		if (i >= 0 && i <= 10) // Bottom
		{
			y = height - 30;
			x = width;
			if (i == 0)
				x -= 30;
			else if (i == 1)
				x -= 110;
			else
				x -= (110 + ((i - 1) * 55));
		}
		else if (i > 10 && i <= 20) // Left
		{
			i -= 10;
			x = 30;
			y = height;
			if (i == 1)
				y -= 110;
			else
				y -= (110 + ((i - 1) * 50));
		}
		else if (i > 20 && i <= 30) // Top
		{
			i -= 20;
			y = 30;
			if (i == 1)
				x += 110;
			else
				x += (110 + ((i - 1) * 55));
		}
		else if (i > 30 && i <= 40) // Right
		{
			i -= 30;
			x = width - 30;
			if (i == 1)
				y += 110;
			else
				y += (110 + ((i - 1) * 50));
		}
		return new int[] { x, y };
	}

	private void drawImage(Graphics g, BufferedImage img, int x, int y, int x2, int y2)
	{
		g.drawImage(img, x, y, x2, y2, 0, 0, img.getWidth(), img.getHeight(), null);
	}

	public void paint(Graphics g)
	{
		drawImage(g, Images.getImages().Bord, 0, 0, width, height);
		Player[] players = game.getPlayers();
		for (int i = 0; i < players.length; i++)
		{
			if (players[i] != null)
			{
				int[] cord = getCords(players[i].Index());
				g.setColor(new Color(0x666666));
				g.drawRect(cord[0] - 1, cord[1] - 1, 11, 11);
				g.setColor(players[i].getColor());
				g.fillRect(cord[0], cord[1], 10, 10);
			}
		}
		drawImage(g, Images.getImages().Dice[game.getDice(0) - 1], 100, 100, 200, 200);
		drawImage(g, Images.getImages().Dice[game.getDice(1) - 1], 210, 100, 310, 200);
	}

}
