package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet11BuyStreet extends Packet
{
	
	public Packet11BuyStreet()
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
		byte pId = MMP.getServer().Monopoly().turn.getID();
		byte sIndex = MMP.getServer().Monopoly().getPlayer(pId).getIndex();
		if(MMP.getServer().Monopoly().getTownManager().isBuyable(sIndex))
		{
			if(MMP.getServer().Monopoly().getPlayer(pId).Money() >= MMP.getServer().Monopoly().getTownManager().getPrice(sIndex))
			{
				MMP.Log(MMP.getServer().Monopoly().getPlayer(pId).Username() + " has bought " + MMP.getServer().Monopoly().getTownManager().get(sIndex).getName());
				MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(pId, -MMP.getServer().Monopoly().getTownManager().getPrice(sIndex)));
				MMP.getServer().Monopoly().sendPacket(new Packet12OwnerStreet(pId, sIndex));
				MMP.getServer().Monopoly().getTownManager().setOwner(pId, sIndex);
				MMP.getServer().Monopoly().getPlayer(pId).Buy(MMP.getServer().Monopoly().getTownManager().getPrice(sIndex));
			}
		}
	}

}
