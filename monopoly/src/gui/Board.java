package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import monopoly.Game;
import monopoly.Player;
import monopoly.Town;

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
				int[] cord = getCords(players[i].getIndex());
				g.setColor(new Color(0x666666));
				g.drawRect(cord[0] - 1, cord[1] - 1, 11, 11);
				g.setColor(players[i].getColor());
				g.fillRect(cord[0], cord[1], 10, 10);
			}
		}
		drawImage(g, Images.getImages().Dice[game.getDice(0) - 1], 100, 100, 200, 200);
		drawImage(g, Images.getImages().Dice[game.getDice(1) - 1], 210, 100, 310, 200);
		g.setColor(new Color(Integer.valueOf("000000", 16)));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		g.drawString(Game.getMonopoly().getCurrentUser() + "'s Turn", 100, 225);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		g.drawString(Game.getMonopoly().getState(), 100, 245);
		g.drawString(Game.getMonopoly().getStateSub(), 100, 260);
		
		for (int i = 0; i < Game.getMonopoly().towns.size(); i++)
		{
			Town t = Game.getMonopoly().towns.get(i);
			/* Bottom Row */
			//Start
			if(i == 1)
				t.paint(g, 511, 520, 52, 78, true);
			//General Funds
			else if(i == 3)
				t.paint(g, 405, 520, 52, 78, true);
			//Income Tax
			else if(i == 5)
				t.paint(g, 298, 520, 52, 78, true);
			else if(i == 6)
				t.paint(g, 244, 520, 52, 78, true);
			//Random Funds
			else if(i == 8)
				t.paint(g, 139, 520, 52, 78, true);
			else if(i == 9)
				t.paint(g, 86, 520, 51, 78, true);
			//Jail
			/* Left Column */
			else if(i == 11)
				t.paint(g, 0, 472, 86, 49, false);
			else if(i == 12)
				t.paint(g, 0, 423, 86, 49, false);
			else if(i == 13)
				t.paint(g, 0, 374, 86, 49, false);
			else if(i == 14)
				t.paint(g, 0, 325, 86, 48, false);
			else if(i == 15)
				t.paint(g, 0, 275, 86, 49, false);
			else if(i == 16)
				t.paint(g, 0, 225, 86, 49, false);
			//General Funds
			else if(i == 18)
				t.paint(g, 0, 127, 86, 49, false);
			else if(i == 19)
				t.paint(g, 0, 79, 86, 49, false);
			//Free Parking
			/* Top Row */
			else if(i == 21)
				t.paint(g, 86, 0, 52, 78, true);
			//Random Funds
			else if(i == 23)
				t.paint(g, 191, 0, 52, 78, true);
			else if(i == 24)
				t.paint(g, 245, 0, 52, 78, true);
			else if(i == 25)
				t.paint(g, 299, 0, 52, 78, true);
			else if(i == 26)
				t.paint(g, 352, 0, 52, 78, true);
			else if(i == 27)
				t.paint(g, 405, 0, 52, 78, true);
			else if(i == 28)
				t.paint(g, 459, 0, 52, 78, true);
			else if(i == 29)
				t.paint(g, 512, 0, 52, 78, true);
			//To Jail
			/* Right Column */
			else if(i == 31)
				t.paint(g, 564, 79, 83, 48, false);
			else if(i == 32)
				t.paint(g, 564, 128, 83, 48, false);
			//General Funds
			else if(i == 34)
				t.paint(g, 564, 225, 83, 49, false);
			else if(i == 35)
				t.paint(g, 564, 275, 83, 48, false);
			//Random Funds
			else if(i == 37)
				t.paint(g, 564, 374, 83, 48, false);
			//Special Tax
			else if(i == 39)
				t.paint(g, 564, 472, 83, 48, false);
		}
	}

}
