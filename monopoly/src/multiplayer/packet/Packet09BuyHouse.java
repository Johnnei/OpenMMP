package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet09BuyHouse extends Packet
{
	
	byte index;
	byte data;
	
	public Packet09BuyHouse()
	{
	}
	
	public Packet09BuyHouse(byte sIndex) {
		this(sIndex, true);
	}
	
	public Packet09BuyHouse(byte sIndex, boolean isBuy) {
		index = sIndex;
		data = (isBuy) ? (byte)1 : (byte)0;
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(index);
		d.writeByte(data);
	}

	@Override
	public void handle()
	{
	}

}
