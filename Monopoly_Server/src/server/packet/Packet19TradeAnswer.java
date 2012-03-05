package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet19TradeAnswer extends Packet
{

	boolean accepted;
	
	public Packet19TradeAnswer() {
		
	}
	
	public Packet19TradeAnswer(boolean answer) {
		accepted = answer;
	}
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
		accepted = d.readBoolean();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeBoolean(accepted);
	}

	@Override
	public void handle()
	{
		if(accepted) {
			Packet13Trade trade = MMP.getServer().getMonopoly().getTradeData();
			if(trade.moneyGet != 0) {
				MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(trade.playerGetId, trade.moneyGet));
				MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(trade.playerGiveId, -trade.moneyGet));
			}
			if(trade.moneyGive != 0) {
				MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(trade.playerGiveId, trade.moneyGive));
				MMP.getServer().getMonopoly().sendPacket(new Packet14ChangeMoney(trade.playerGetId, -trade.moneyGive));
			}
			for(int i = 0; i < trade.townsGet.length; i++) {
				MMP.getServer().getMonopoly().sendPacket(new Packet12OwnerStreet(trade.playerGetId, trade.townsGet[i]));
			}
			for(int i = 0; i < trade.townsGive.length; i++) {
				MMP.getServer().getMonopoly().sendPacket(new Packet12OwnerStreet(trade.playerGiveId, trade.townsGive[i]));
			}
		}
		MMP.getServer().getMonopoly().clearTradeData();
	}

}
