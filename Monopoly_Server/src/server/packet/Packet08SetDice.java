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
	
	public Packet08SetDice(byte[] dices)
	{
		dice = dices[0];
		dice2 = dices[1];
	}
	
	public void readData(DataInputStream d) throws IOException
	{
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(dice);
		d.writeByte(dice2);
	}

	@Override
	public void handle()
	{
	}

}
