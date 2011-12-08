package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;
import monopoly.Player;
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
		Game.Log("0");
		id = d.readByte();
		Game.Log("1");
		user = readString(d);
		Game.Log("2");
		colorCode = d.readInt();
		Game.Log("3");
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
		if(Game.Monopoly().getMyID() == id) //Ignore Myself
		{
			Game.Log("I joined the game! (" + id + ")");
			return;
		}
		Game.Log("1");
		PlayerMP player = new PlayerMP(user, id, colorCode);
		Game.Log("2");
		Game.Monopoly().registerPlayer(player);
		Game.Log("3");
	}
	
	public int size()
	{
		return 5;
	}

	String user;
	int colorCode;
	byte id;
}
