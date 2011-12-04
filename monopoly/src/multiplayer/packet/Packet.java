package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import monopoly.Game;

public abstract class Packet
{
	
	private static Class[] packetArray = new Class[5];

	public Packet()
	{
	}

	public abstract void readData(DataInputStream d) throws IOException;

	public abstract void writeData(DataOutputStream d) throws IOException;

	public abstract void handle();
	
	public abstract int size();

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
		Class packetClass = packetArray[packetId];
		Packet p = (Packet)packetClass.newInstance();
		p.readData(d);
		return p;
	}
	
	static
	{
		registerClass(0, Packet00SetTurn.class);
		registerClass(1, Packet01PlayerJoin.class);
		registerClass(2, Packet02GiveID.class);
		registerClass(3, Packet03Username.class);
		registerClass(4, Packet04Colorcode.class);
	}
	
	public static void registerClass(int id, Class c)
	{
		packetArray[id] = c;
	}

}
