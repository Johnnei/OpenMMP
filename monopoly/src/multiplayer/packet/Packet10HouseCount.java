package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet10HouseCount extends Packet
{
	
	byte index;
	byte count;
	
	public Packet10HouseCount()
	{
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		index = d.readByte();
		count = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
		Game.Monopoly().towns.setHouse(index, count);
	}
}
