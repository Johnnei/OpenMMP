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
			MMP.getServer().Monopoly().getCurrentPlayer().addIndex((byte)MMP.getServer().Monopoly().diceEyesCount());
			MMP.Sleep((MMP.getServer().Monopoly().diceEyesCount() * 150) + 10); //Delay actions so it merges with the clients
			int cost = MMP.getServer().Monopoly().getTownManager().getPayPrice(MMP.getServer().Monopoly().getCurrentPlayer().getIndex());
			if(cost > 0)
				MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(MMP.getServer().Monopoly().getCurrentPlayer().getId(), -cost));
		}
	}

}
