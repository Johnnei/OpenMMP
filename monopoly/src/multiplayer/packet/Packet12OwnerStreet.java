package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet12OwnerStreet extends Packet
{
	byte pId;
	byte sIndex;

	public Packet12OwnerStreet()
	{
		
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
		Game.getMonopoly().towns.setOwner(pId, sIndex);
		Game.getMonopoly().updateStateString();
	}

}
