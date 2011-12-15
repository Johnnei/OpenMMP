package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.SpecialTown;

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
			//Pay Rent
			int sIndex = MMP.getServer().Monopoly().getCurrentPlayer().getIndex();
			int sCost = MMP.getServer().Monopoly().getTownManager().getPayPrice(sIndex);
			if(sCost > 0)
				MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(MMP.getServer().Monopoly().getCurrentPlayer().getId(), -sCost));
			//OnArrival Events
			if(MMP.getServer().Monopoly().getTownManager().isTownType(sIndex, SpecialTown.Kans))
			{
				
			}
			else if(MMP.getServer().Monopoly().getTownManager().isTownType(sIndex, SpecialTown.Algemeen_Fonds))
			{
				
			}
			else if(MMP.getServer().Monopoly().getTownManager().isTownType(sIndex, SpecialTown.ToJail))
			{
			
			}
			//If has thrown doubles the player is allowed to roll again
			if(MMP.getServer().Monopoly().diceDoubles())
			{
				MMP.getServer().Monopoly().turn.ResetTurn(MMP.getServer().Monopoly().getCurrentPlayer().getId());
			}
		}
	}

}
