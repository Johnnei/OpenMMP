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
		this.id = d.readByte();
		user = d.readUTF();
		colorCode = d.readInt();
	}

	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(id);
		d.writeUTF(user);
		d.writeInt(colorCode);
	}

	public void handle()
	{
		if(Game.Monopoly().getMyID() == id) //Ignore Myself
		{
			Game.Log("I joined the game! (" + id + ")");
			return;
		}
		PlayerMP player = new PlayerMP(user, id, colorCode);
		Game.Monopoly().registerPlayer(player);
		Game.Log(user + " has joined the game!");
	}
	
	public int size()
	{
		return 5;
	}

	String user;
	int colorCode;
	byte id;
}
