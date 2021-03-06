package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

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
		Game.getMonopoly().playerCount = pCount;
		Game.Log("Server wants to start! We got " + pCount + " players!");
	}
	
	byte pCount;
}
