package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import monopoly.Game;
import monopoly.GameEvent;

public class ButtonBuyStreet extends JButton implements ActionListener
{

	private Game game;

	public ButtonBuyStreet()
	{
		game = Game.getMonopoly();
		setText("Buy Street");
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() != this)
			return;
		if (game.turn.myTurn(game.getMyID()))
			game.turn.addEvent(GameEvent.buyStreet);
	}
}
