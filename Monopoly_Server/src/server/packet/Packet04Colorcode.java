package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet04Colorcode extends Packet
{
	byte id;
	int code;
	
	public Packet04Colorcode()
	{
		
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		id = d.readByte();
		code = d.readInt();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
		d.writeInt(code);
	}

	@Override
	public void handle()
	{
		MMP.Log("Client " + MMP.getServer().getMonopoly().getPlayer(id).getUsername() + " picked color " + code);
		MMP.getServer().getMonopoly().setColor(id, code);
	}

}
