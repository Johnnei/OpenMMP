package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet02GiveID extends Packet
{

	public byte id;
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
		id = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
	}

	@Override
	public void handle()
	{
		Game.Log("Recieved ID: " + id);
		Game.Monopoly().getPlayer().setId(id);
		Packet p = new Packet03Username(Game.Monopoly().getPlayer().Username(), id);
		Packet p2 = new Packet04Colorcode(Game.Monopoly().getPlayer().getColorCode(), id);
		Game.Monopoly().getPlayer().addPacket(p);
		Game.Monopoly().getPlayer().addPacket(p2);
	}

}
