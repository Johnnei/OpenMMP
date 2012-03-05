package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;
import multiplayer.PlayerMP;

public class Packet01PlayerJoin extends Packet
{

	public Packet01PlayerJoin()
	{
	}

	public Packet01PlayerJoin(String username, int color, byte id)
	{
		user = username;
		colorCode = color;
		this.id = id;
	}

	public void readData(DataInputStream d) throws IOException
	{
		id = d.readByte();
		user = readString(d);
		colorCode = d.readInt();
	}

	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
		writeString(d, user);
		d.writeInt(colorCode);
	}

	public void handle()
	{
		Game.Log("Player " + id + " has joined the game as 0x" + colorCode + " \"" + user + "\"");
		if(Game.getMonopoly().getMyID() == id) //Ignore Myself
		{
			Game.Log("I joined the game! (" + id + ")");
			return;
		}
		PlayerMP player = new PlayerMP(user, id, colorCode);
		Game.getMonopoly().registerPlayer(player);
	}
	
	public int size()
	{
		return 5;
	}

	String user;
	int colorCode;
	byte id;
}
