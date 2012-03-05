package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet13Trade extends Packet
{
	public byte[] townsGet;
	public byte[] townsGive;
	public int moneyGet;
	public int moneyGive;
	public byte playerGetId;
	public byte playerGiveId;
	
	public Packet13Trade()
	{
	}
	
	public Packet13Trade(byte[] townsGet, byte[] townsGive, int moneyGet, int moneyGive, byte pGet, byte pGive) {
		this.townsGet = townsGet;
		this.townsGive = townsGive;
		this.moneyGet = moneyGet;
		this.moneyGive = moneyGive;
		playerGetId = pGet;
		playerGiveId = pGive;
	}

	@Override
	public void readData(DataInputStream d) throws IOException
	{
		townsGet = new byte[d.readByte()];
		for(int i = 0; i < townsGet.length; i++) {
			townsGet[i] = d.readByte();
		}
		townsGive = new byte[d.readByte()];
		for(int i = 0; i < townsGive.length; i++) {
			townsGive[i] = d.readByte();
		}
		moneyGet = d.readInt();
		moneyGive = d.readInt();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeByte((byte)townsGet.length);
		for(int i = 0; i < townsGet.length; i++) {
			d.writeByte(townsGet[i]);
		}
		d.writeByte((byte)townsGive.length);
		for(int i = 0; i < townsGive.length; i++) {
			d.writeByte(townsGive[i]);
		}
		d.writeInt(moneyGet);
		d.writeInt(moneyGive);
	}

	@Override
	public void handle()
	{
	}

}
