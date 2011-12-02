package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;
import monopoly.Player;

public class PlayerJoin extends Packet {
	
	public PlayerJoin() {};
	public PlayerJoin(String username) { user = username; }
	public PlayerJoin(String username, boolean isHost) { this(username); host = true; }
	
	public void readData(DataInputStream d) throws IOException
	{
		user = d.readUTF();
		colorCode = d.readInt();
		host = d.readBoolean();
	}
	
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeUTF(user);
		d.writeInt(colorCode);
		d.writeBoolean(host);
	}
	
	public void handle()
	{
		Player p = new Player(user, colorCode);
		Player[] ps = Game.Monopoly().getPlayers();
		for(int i = 0; i < ps.length; i++)
		{
			if(ps[i] == null)
			{
				Game.Monopoly().setPlayer(i, p);
				return;
			}
		}
	}
	
	String user;
	int colorCode;
	boolean host = false;
}
