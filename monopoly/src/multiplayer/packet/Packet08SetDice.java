package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet08SetDice extends Packet
{
	
	byte dice;
	byte dice2;
	
	public Packet08SetDice()
	{
	}
	
	public void readData(DataInputStream d) throws IOException
	{
		dice = d.readByte();
		dice2 = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
		Game.getMonopoly().setDice(dice, dice2);
		if(!Game.getMonopoly().getMyPlayer().isInJail()) //Prevent Client/Server De-sync when the player is in jail!
			Game.getMonopoly().movePlayer(Game.getMonopoly().getDice(0) + Game.getMonopoly().getDice(1));
	}

}
