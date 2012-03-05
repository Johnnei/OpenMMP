package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet15SetIndex extends Packet
{
	byte pId;
	byte sIndex;
	
	public Packet15SetIndex()
	{
	}
	
	public Packet15SetIndex(byte id, byte index)
	{
		pId = id;
		sIndex = index;
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		pId = d.readByte();
		sIndex = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
		Game.getMonopoly().getPlayer(pId).setIndex(sIndex);
	}

}
