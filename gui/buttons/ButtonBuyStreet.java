package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import monopoly.Game;
import monopoly.GameEvent;

public class ButtonBuyStreet extends JButton implements ActionListener
{

	private Game game;
	private String owner;

	public ButtonBuyStreet(String owner)
	{
		game = Game.Monopoly();
		this.owner = owner;
		setText("Buy Street");
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() != this)
			return;
		if (game.turn.myTurn(owner))
			game.turn.addEvent(GameEvent.buyStreet);
	}
}
