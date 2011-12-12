package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet12OwnerStreet extends Packet
{
	byte pId;
	byte sIndex;

	public Packet12OwnerStreet()
	{
		
	}

	public Packet12OwnerStreet(byte playerId, byte streetIndex)
	{
		pId = playerId;
		sIndex = streetIndex;
	}
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(pId);
		d.writeByte(sIndex);
	}

	@Override
	public void handle()
	{
	}

}
