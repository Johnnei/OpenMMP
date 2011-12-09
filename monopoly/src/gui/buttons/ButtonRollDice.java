package gui.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import monopoly.Game;
import monopoly.GameEvent;

public class ButtonRollDice extends JButton implements ActionListener
{

	private Game game;

	public ButtonRollDice()
	{
		game = Game.Monopoly();
		setText("Roll Dice");
		addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() != this)
			return;
		if (game.turn.myTurn(game.getMyID()))
			game.turn.addEvent(GameEvent.rollDice);
	}
}
