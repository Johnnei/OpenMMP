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
			executeMove(true);
		}
	}
	
	public static void executeMove(boolean normalTurn) {
		//If the player gets jailed by rolling trice doubles, End the roll-phase before applying any effect
		if(MMP.getServer().Monopoly().turn.sendJail())
		{
			MMP.getServer().Monopoly().getCurrentPlayer().jailPlayer();
			MMP.getServer().Monopoly().sendPacket(new Packet15SetIndex(MMP.getServer().Monopoly().getCurrentPlayer().getId(), (byte)10));
			return;
		}
		//Player is in jail, Only handle jail
		if(MMP.getServer().Monopoly().getCurrentPlayer().isInJail())
		{
			if(MMP.getServer().Monopoly().getCurrentPlayer().getJailTime() == 3)
			{
				if(MMP.getServer().Monopoly().diceDoubles())
					MMP.getServer().Monopoly().getCurrentPlayer().releaseFromJail();
				//TODO Jail
				//Pay and release
				//Check if has free jail card else pay 5000
				MMP.getServer().Monopoly().getCurrentPlayer().releaseFromJail();
			}
			MMP.getServer().Monopoly().getCurrentPlayer().addJailTime();
			return;
		}
		
		MMP.getServer().Monopoly().getCurrentPlayer().addIndex((byte)MMP.getServer().Monopoly().diceEyesCount());
		MMP.Sleep((MMP.getServer().Monopoly().diceEyesCount() * 150) + 10); //Delay actions so it merges with the clients
		//Pay Rent
		byte pId = MMP.getServer().Monopoly().getCurrentPlayer().getId();
		int sIndex = MMP.getServer().Monopoly().getCurrentPlayer().getIndex();
		int sCost = MMP.getServer().Monopoly().getTownManager().getPayPrice(sIndex);
		if(sCost > 0)
			MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(pId, -sCost));
		//OnArrival Events
		if(MMP.getServer().Monopoly().getTownManager().isTownType(sIndex, SpecialTown.Kans))
		{
			MMP.getServer().Monopoly().getDeck(true).drawCard().Handle();
		}
		else if(MMP.getServer().Monopoly().getTownManager().isTownType(sIndex, SpecialTown.Algemeen_Fonds))
		{
			MMP.getServer().Monopoly().getDeck(false).drawCard().Handle();
		}
		else if(MMP.getServer().Monopoly().getTownManager().isTownType(sIndex, SpecialTown.ToJail))
		{
			MMP.getServer().Monopoly().sendPacket(new Packet15SetIndex(MMP.getServer().Monopoly().getCurrentPlayer().getId(), (byte)10));
		}
		else if(MMP.getServer().Monopoly().getTownManager().isTownType(sIndex, SpecialTown.Start))
		{
			MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(pId, 20000));
		}
		//If has thrown doubles the player is allowed to roll again
		if(MMP.getServer().Monopoly().diceDoubles() && normalTurn)
		{
			MMP.getServer().Monopoly().turn.ResetTurn(MMP.getServer().Monopoly().getCurrentPlayer().getId());
		}
		
	}

}
