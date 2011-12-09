package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import monopoly.Game;
import monopoly.GameEvent;

public class ButtonBuyHouse extends JButton implements ActionListener
{

	private Game game;

	public ButtonBuyHouse()
	{
		game = Game.Monopoly();
		setText("Buy House");
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() != this)
			return;
		if (game.turn.myTurn(game.getMyID()))
			game.turn.addEvent(GameEvent.buyHouse);
	}
}