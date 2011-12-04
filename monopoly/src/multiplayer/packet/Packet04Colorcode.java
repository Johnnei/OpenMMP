package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet04Colorcode extends Packet
{
	byte id;
	int code;
	
	public Packet04Colorcode(int color, byte userId)
	{
		id = userId;
		code = color;
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		id = d.readByte();
		code = d.readInt();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
		d.writeInt(code);
	}

	@Override
	public void handle()
	{
	}

}
