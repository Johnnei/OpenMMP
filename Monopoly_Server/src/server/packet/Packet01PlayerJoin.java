package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.packet.Packet;

public class Packet01PlayerJoin extends Packet
{
	
	public Packet01PlayerJoin()
	{
		
	}

	public Packet01PlayerJoin(String username, int color, byte id)
	{
		user = username;
		colorCode = color;
		this.id = id;
	}

	public void readData(DataInputStream d) throws IOException
	{
		user = readString(d);
		colorCode = d.readInt();
	}

	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
		writeString(d, user);
		d.writeInt(colorCode);
	}

	public void handle()
	{
	}

	String user;
	int colorCode;
	byte id;
}
