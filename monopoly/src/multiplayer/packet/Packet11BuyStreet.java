package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet11BuyStreet extends Packet
{
	byte id;
	byte sIndex;
	
	public Packet11BuyStreet(byte playerId, byte index)
	{
		id = playerId;
		sIndex = index;
	}
	
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
		d.writeByte(id);
		d.writeByte(sIndex);
	}

	@Override
	public void handle()
	{
	}

}
