package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet05StartGame extends Packet
{
	public Packet05StartGame()
	{
		
	}
	
	public Packet05StartGame(byte playerCount)
	{
		
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		pCount = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(pCount);
	}

	@Override
	public void handle()
	{
	}
	
	byte pCount;
}
