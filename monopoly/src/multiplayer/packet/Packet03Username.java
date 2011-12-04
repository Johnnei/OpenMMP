package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet03Username extends Packet
{
	byte id;
	String username;
	
	public Packet03Username(String user, byte userId)
	{
		id = userId;
		username = user;
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		id = d.readByte();
		username = d.readUTF();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
		d.writeUTF(username);
	}

	@Override
	public void handle()
	{
	}

}
