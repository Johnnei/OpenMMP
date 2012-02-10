package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet14ChangeMoney extends Packet
{
	byte id;
	int amount;
	
	public Packet14ChangeMoney()
	{
	}
	
	public Packet14ChangeMoney(byte pId, int mChange)
	{
		id = pId;
		amount = mChange;
		MMP.Log(MMP.getServer().Monopoly().getPlayer(id).getUsername() + " has gained " + amount + " money");
	}
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
		d.writeInt(amount);
	}

	@Override
	public void handle()
	{
	}

}
