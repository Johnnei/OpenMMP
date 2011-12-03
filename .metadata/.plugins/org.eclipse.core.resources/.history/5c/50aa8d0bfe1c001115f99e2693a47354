package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;
import monopoly.Turn;

public class SetTurn extends Packet{

	public SetTurn() {}
	public SetTurn(byte b) { turn = b; }
	
	void readData(DataInputStream d) throws IOException
	{
		turn = d.readByte();
	}

	void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(turn);
	}
	
	void handle()
	{
		Game.Monopoly().turn = new Turn(Game.Monopoly(), Game.Monopoly().getPlayers()[turn]);
	}
	
	byte turn;
}
