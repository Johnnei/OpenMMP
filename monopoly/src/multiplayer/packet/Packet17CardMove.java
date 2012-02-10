package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet17CardMove extends Packet
{
	
	byte amount;
	
	public Packet17CardMove()
	{
	}
	
	public Packet17CardMove(byte amount)
	{
		this.amount = amount;
	}
	
	public void readData(DataInputStream d) throws IOException
	{
		amount = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(amount);
	}

	@Override
	public void handle()
	{
		Game.Monopoly().movePlayer(amount);
	}

}
