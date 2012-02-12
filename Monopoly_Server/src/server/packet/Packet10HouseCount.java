package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet10HouseCount extends Packet
{
	byte index;
	byte count;
	
	public Packet10HouseCount()
	{
	}
	
	public Packet10HouseCount(byte sIndex, byte hCount) {
		index = sIndex;
		count = hCount;
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(index);
		d.writeByte(count);
	}

	@Override
	public void handle()
	{	
	}
}
