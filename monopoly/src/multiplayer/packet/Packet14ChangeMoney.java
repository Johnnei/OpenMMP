package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet14ChangeMoney extends Packet
{
	byte playerId;
	int amount;
	
	public Packet14ChangeMoney()
	{
	}
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
		playerId = d.readByte();
		amount = d.readInt();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
		Game.getMonopoly().changePlayerMoney(playerId, amount);
	}

}
