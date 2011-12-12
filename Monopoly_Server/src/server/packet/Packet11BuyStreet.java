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
				MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(pId, MMP.getServer().Monopoly().getTownManager().getPrice(sIndex)));
				MMP.getServer().Monopoly().sendPacket(new Packet12OwnerStreet(pId, sIndex));
			}
		}
	}

}
