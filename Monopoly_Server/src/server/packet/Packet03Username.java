package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet03Username extends Packet
{
	byte id;
	String username;

	public Packet03Username()
	{
		
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		id = d.readByte();
		username = d.readUTF();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
		d.writeUTF(username);
	}

	@Override
	public void handle()
	{
		MMP.Log("Client " + id + " identified with " + username);
		MMP.getServer().Monopoly().setUsername(id, username);
	}

}
