package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Town;

import server.MMP;

public class Packet09BuyHouse extends Packet
{
	private final int BUY = 1;
	private final int SELL = 0;
	
	byte index;
	byte data;
	
	public Packet09BuyHouse()
	{
		
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		index = d.readByte();
		data = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
		MMP.Log("Buying/Selling House at index " + index + "...");
		if(index == -1)
			MMP.Log("Invalid Street Supplied");
		if(MMP.getServer().getMonopoly().getTownManager().isHouseInvalid(index)) {
			MMP.Log("Invalid House Spot");
			return;
		}
		if(!MMP.getServer().getMonopoly().getTownManager().hasCompleteStreet(MMP.getServer().getMonopoly().getCurrentPlayer().getId(), index)) {
			MMP.Log("Incomplete Street");
			return;
		}
		
		Town t = MMP.getServer().getMonopoly().getTownManager().get(index);
		int housePrice = MMP.getServer().getMonopoly().getTownManager().getHousePrice(index);
		
		if(data == BUY) {
			MMP.Log("House Data: Buy");
			if(MMP.getServer().getMonopoly().getPlayer(t.getOwnerId()).getMoney() >= housePrice) {
				if(t.getHouseCount() < 5) {
					MMP.getServer().getMonopoly().getTownManager().setHouseCount(index, t.getHouseCount() + 1);
					MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(t.getOwnerId(), -MMP.getServer().getMonopoly().getTownManager().getHousePrice(index)));
					MMP.getServer().getMonopoly().sendPacket(new Packet10HouseCount((byte)index, (byte)(t.getHouseCount() + 1)));
				}
			}
		} else if(data == SELL) {
			MMP.Log("House Data: Sell");
			if(t.getHouseCount() > 0) {
				MMP.getServer().getMonopoly().getTownManager().setHouseCount(index, t.getHouseCount() - 1);
				MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(t.getOwnerId(), (int)(housePrice * 0.5)));
				MMP.getServer().getMonopoly().sendPacket(new Packet10HouseCount((byte)index, (byte)(t.getHouseCount() - 1)));
			}
		} //Else invalid
	}

}
