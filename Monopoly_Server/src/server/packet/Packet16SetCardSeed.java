package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet16SetCardSeed extends Packet
{
	long seed;
	
	public Packet16SetCardSeed()
	{
	}
	
	public Packet16SetCardSeed(long seed)
	{
		this.seed = seed;
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeLong(seed);
	}

	@Override
	public void handle()
	{
	}

}
