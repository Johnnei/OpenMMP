package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;
import monopoly.Turn;

public class Packet00SetTurn extends Packet
{

	public Packet00SetTurn()
	{
	}

	public Packet00SetTurn(byte b)
	{
		turn = b;
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
		Game.Monopoly().turn = new Turn(Game.Monopoly().getPlayers()[turn]);
	}
	
	public int size()
	{
		return 1;
	}

	byte turn;
}
