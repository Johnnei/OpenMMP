package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet02GiveID extends Packet
{

	public byte id;
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
		id = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
	}

	@Override
	public void handle()
	{
	}

}
