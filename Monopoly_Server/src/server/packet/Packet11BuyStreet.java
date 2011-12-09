package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet11BuyStreet extends Packet
{
	
	byte pId;
	byte pIndex;
	
	public Packet11BuyStreet()
	{
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		pId = d.readByte();
		pIndex = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
	}

}
