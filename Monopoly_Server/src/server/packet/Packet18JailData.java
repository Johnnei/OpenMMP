package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet18JailData extends Packet
{
	
	byte playerId;
	byte jail;
	
	public Packet18JailData()
	{
	}
	
	public Packet18JailData(byte playerId, byte data)
	{
		this.playerId = playerId;
		jail = data;
	}
	
	public void readData(DataInputStream d) throws IOException
	{
		playerId = d.readByte();
		jail = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(playerId);
		d.writeByte(jail);
	}

	@Override
	public void handle()
	{
	}

}
