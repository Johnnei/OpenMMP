package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet16SetCardSeed extends Packet
{
	
	public long seed;
	
	public Packet16SetCardSeed()
	{
	}
	
	public Packet16SetCardSeed(long s)
	{
		seed = s;
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		seed = d.readLong();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeLong(seed);
	}

	@Override
	public void handle()
	{
		Game.Monopoly().setCardSeed(seed);
		Game.Log("Got Random Card Seed");
	}

}
