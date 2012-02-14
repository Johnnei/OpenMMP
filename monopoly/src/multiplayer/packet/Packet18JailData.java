package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;

public class Packet18JailData extends Packet
{
	
	private final int ENTER_JAIL = 0x01;
	private final int LEAVE_JAIL = 0x02;
	
	byte playerId;
	byte jail;
	
	public Packet18JailData()
	{
	}
	
	public Packet18JailData(byte playerId, byte data)
	{
		this.playerId = playerId;
		jail = data;
	}
	
	public void readData(DataInputStream d) throws IOException
	{
		playerId = d.readByte();
		jail = d.readByte();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte(playerId);
		d.writeByte(jail);
	}

	@Override
	public void handle()
	{
		Game.Log("Recieved jail data for player: " + playerId);
		if((jail & ENTER_JAIL) == ENTER_JAIL) {
			Game.Monopoly().jailPlayer(playerId, true);
		} else if ((jail & LEAVE_JAIL) == LEAVE_JAIL) {
			Game.Monopoly().jailPlayer(playerId, false);
		}
	}

}
