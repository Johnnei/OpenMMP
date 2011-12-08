package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import monopoly.Game;

public abstract class Packet
{
	
	private static Class[] packetArray = new Class[6];

	public Packet()
	{
	}

	public abstract void readData(DataInputStream d) throws IOException;

	public abstract void writeData(DataOutputStream d) throws IOException;

	public abstract void handle();

	public final int getPacketID()
	{
		String packetClass = getClass().getName();
		String id = packetClass.substring(25, 27);
		return Integer.parseInt(id);
	}

	public static void sendPacket(Packet p, DataOutputStream d) throws IOException
	{
		d.writeShort(p.getPacketID());
		p.writeData(d);
	}

	public static Packet readPacket(DataInputStream d) throws Exception
	{
		if (d.available() <= 0)
			return null;
		short packetId = d.readShort();
		Game.Log("Retrieving Packet ID: " + packetId);
		Class packetClass = packetArray[packetId];
		Packet p = (Packet)packetClass.newInstance();
		p.readData(d);
		return p;
	}
	
	protected String readString(DataInputStream d) throws IOException
	{
		short l = d.readShort();
		String s = "";
		for(int i = 0; i < l; i++)
		{
			s = s + (char)d.readByte();
		}
		return s;
	}
	
	protected void writeString(DataOutputStream d, String s) throws IOException
	{
		d.writeShort((short)s.length());
		for(int i = 0; i < s.length(); i++)
		{
			d.writeByte(s.substring(i, i + 1).charAt(0));
		}
	}
	
	static
	{
		registerClass(0, Packet00SetTurn.class);
		registerClass(1, Packet01PlayerJoin.class);
		registerClass(2, Packet02GiveID.class);
		registerClass(3, Packet03Username.class);
		registerClass(4, Packet04Colorcode.class);
		registerClass(5, Packet05StartGame.class);
	}
	
	public static void registerClass(int id, Class c)
	{
		packetArray[id] = c;
	}

}
