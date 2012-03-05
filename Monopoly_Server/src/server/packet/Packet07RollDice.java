package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Card;
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
		if(MMP.getServer().getMonopoly().turn.canRollDice())
		{
			MMP.Log("Rolling Dices...");
			MMP.getServer().getMonopoly().turn.rollDice();
			MMP.getServer().getMonopoly().rollDice();
			MMP.getServer().getMonopoly().sendPacket(new Packet08SetDice(MMP.getServer().getMonopoly().getDices()));
			executeMove(true);
		}
	}
	
	/**
	 * Handle's all event after throwing the dices.
	 * The function will delay the thread by the same amount which the client will
	 * @param normalTurn If the turn should apply effects which can only happen after an actual dice throw.
	 */
	public static void executeMove(boolean normalTurn) {
		//Player Data
		byte pId = MMP.getServer().getMonopoly().getCurrentPlayer().getId();
		
		//Move player (server side) and sleep to sync up with the client!
		MMP.getServer().getMonopoly().getCurrentPlayer().addIndex((byte)MMP.getServer().getMonopoly().diceEyesCount());
		MMP.Sleep((MMP.getServer().getMonopoly().diceEyesCount() * 150) + 10); //10ms extra delay to cover up a bit of lag
		
		//If the player gets jailed by rolling trice doubles, End the roll-phase before applying any effect
		if(MMP.getServer().getMonopoly().turn.sendJail())
		{
			MMP.getServer().getMonopoly().getCurrentPlayer().jailPlayer();
			MMP.getServer().getMonopoly().sendPacket(new Packet15SetIndex(MMP.getServer().getMonopoly().getCurrentPlayer().getId(), (byte)10));
			return;
		}
		//Player is in jail, Only handle jail
		if(MMP.getServer().getMonopoly().getCurrentPlayer().isInJail())
		{
			if(MMP.getServer().getMonopoly().getCurrentPlayer().getJailTime() == 3)
			{
				if(MMP.getServer().getMonopoly().diceDoubles())
					MMP.getServer().getMonopoly().getCurrentPlayer().releaseFromJail();
				else {
					MMP.getServer().getMonopoly().getCurrentPlayer().pay(5000);
					MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(pId, -5000));
					MMP.getServer().getMonopoly().getCurrentPlayer().releaseFromJail();
				}
			}
			MMP.getServer().getMonopoly().getCurrentPlayer().addJailTime();
			return;
		}
		
		//Pay Rent
		int sIndex = MMP.getServer().getMonopoly().getCurrentPlayer().getIndex();
		int sCost = MMP.getServer().getMonopoly().getTownManager().getPayPrice(sIndex);
		if(sCost > 0) {
			MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(pId, -sCost));
			MMP.getServer().getMonopoly().getCurrentPlayer().pay(sCost);
		}
		//OnArrival Events
		if(MMP.getServer().getMonopoly().getTownManager().isTownType(sIndex, SpecialTown.Kans))
		{
			Card c = MMP.getServer().getMonopoly().getDeck(true).drawCard();
			c.Handle();
			MMP.getServer().getMonopoly().addJackpotMoney(c.getJackpotMoney());
		}
		else if(MMP.getServer().getMonopoly().getTownManager().isTownType(sIndex, SpecialTown.Algemeen_Fonds))
		{
			Card c = MMP.getServer().getMonopoly().getDeck(false).drawCard();
			c.Handle();
			MMP.getServer().getMonopoly().addJackpotMoney(c.getJackpotMoney());
		}
		else if(MMP.getServer().getMonopoly().getTownManager().isTownType(sIndex, SpecialTown.ToJail))
		{
			MMP.getServer().getMonopoly().sendPacket(new Packet15SetIndex(MMP.getServer().getMonopoly().getCurrentPlayer().getId(), (byte)10));
			MMP.getServer().getMonopoly().getCurrentPlayer().jailPlayer();
		}
		else if(MMP.getServer().getMonopoly().getTownManager().isTownType(sIndex, SpecialTown.Start))
		{
			MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(pId, 20000));
			MMP.getServer().getMonopoly().getCurrentPlayer().incrementMoney(20000);
		}
		else if(MMP.getServer().getMonopoly().getTownManager().isTownType(sIndex, SpecialTown.Vrij_Parkeren))
		{
			MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(pId, MMP.getServer().getMonopoly().getJackpot()));
			MMP.getServer().getMonopoly().clearJackpot();
		}
		//If has thrown doubles the player is allowed to roll again
		if(MMP.getServer().getMonopoly().diceDoubles() && normalTurn)
		{
			MMP.getServer().getMonopoly().turn.ResetTurn(MMP.getServer().getMonopoly().getCurrentPlayer().getId());
		}
		
	}

}
