package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet15SetIndex extends Packet
{
	byte pId;
	byte sIndex;
	
	public Packet15SetIndex()
	{
	}
	
	public Packet15SetIndex(byte id, byte index)
	{
		pId = id;
		sIndex = index;
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
