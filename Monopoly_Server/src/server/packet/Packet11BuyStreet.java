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
		byte pId = MMP.getServer().getMonopoly().turn.getID();
		byte sIndex = MMP.getServer().getMonopoly().getPlayer(pId).getIndex();
		if(MMP.getServer().getMonopoly().getTownManager().isBuyable(sIndex))
		{
			if(MMP.getServer().getMonopoly().getPlayer(pId).getMoney() >= MMP.getServer().getMonopoly().getTownManager().getPrice(sIndex))
			{
				MMP.Log(MMP.getServer().getMonopoly().getPlayer(pId).getUsername() + " has bought " + MMP.getServer().getMonopoly().getTownManager().get(sIndex).getName());
				MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(pId, -MMP.getServer().getMonopoly().getTownManager().getPrice(sIndex)));
				MMP.getServer().getMonopoly().sendPacket(new Packet12OwnerStreet(pId, sIndex));
				MMP.getServer().getMonopoly().getTownManager().setOwner(pId, sIndex);
				MMP.getServer().getMonopoly().getPlayer(pId).Buy(MMP.getServer().getMonopoly().getTownManager().getPrice(sIndex));
			}
		}
	}

}
