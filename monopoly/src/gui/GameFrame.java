package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

import monopoly.Game;

public class GameFrame extends JComponent implements Runnable
{

	private Game game;
	public RightPanel rightpanel;
	public Board board;

	public GameFrame()
	{
	}

	public GameFrame(int WIDTH, int HEIGHT, int Split)
	{
		this.game = Game.getMonopoly();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
		board = new Board(game, WIDTH - Split, HEIGHT);
		rightpanel = new RightPanel(game, Split, HEIGHT, game.myPlayer);
		add(rightpanel, "East");
		add(board, "West");
		game.gframe = this;
	}

	public static GameFrame initGui()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exception)
		{
		}
		GameFrame gframe = new GameFrame(800, 600, 150);
		JFrame jframe = new JFrame("openMMP - " + Game.VERSION + " - " + Game.getMonopoly().getMyPlayer().getUsername());
		jframe.setIconImage(Images.getImages().Icon);
		jframe.add(gframe);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
		jframe.addWindowListener(new GameGUIHandler());
		jframe.setResizable(false);
		return gframe;
	}

	public void run()
	{
		initGui();
	}
}
