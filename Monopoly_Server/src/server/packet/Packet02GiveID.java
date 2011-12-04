package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;
import server.packet.Packet;

public class Packet02GiveID extends Packet
{

	public byte id;
	
	public Packet02GiveID()
	{
		
	}
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
		id = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		MMP.Log("Sending ID Data to: " + id);
		d.writeByte(id);
	}

	@Override
	public void handle()
	{
	}

}
