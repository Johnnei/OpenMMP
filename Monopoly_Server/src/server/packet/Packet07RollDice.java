package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet07RollDice extends Packet
{
	
	public Packet07RollDice()
	{
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
		if(MMP.getServer().Monopoly().turn.canRollDice())
		{
			MMP.Log("Rolling Dices...");
			MMP.getServer().Monopoly().turn.rollDice();
			MMP.getServer().Monopoly().rollDice();
			MMP.getServer().Monopoly().sendPacket(new Packet08SetDice(MMP.getServer().Monopoly().getDices()));
		}
	}

}
