package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet08SetDice extends Packet
{
	
	byte dice;
	byte dice2;
	
	public Packet08SetDice()
	{
	}
	
	public void readData(DataInputStream d) throws IOException
	{
		dice = d.readByte();
		dice2 = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
	}

}
