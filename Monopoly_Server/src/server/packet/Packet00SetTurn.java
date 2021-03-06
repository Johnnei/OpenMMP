package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;
import server.packet.Packet;

public class Packet00SetTurn extends Packet
{

	public Packet00SetTurn()
	{
	}

	public Packet00SetTurn(byte b)
	{
		turn = b;
		MMP.Log("Turn has been given to " + MMP.getServer().getMonopoly().getPlayer(turn).getUsername());
	}

	public void readData(DataInputStream d) throws IOException
	{
		turn = d.readByte();
	}

	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(turn);
	}

	public void handle()
	{
	}

	byte turn;
}
