package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import server.MMP;

public class Packet06NextTurn extends Packet
{

	public Packet06NextTurn()
	{
	}
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
	}

	@Override
	public void handle()
	{
		if(!MMP.getServer().Monopoly().turn.canRollDice())
			MMP.getServer().Monopoly().advanceTurn();
	}

}
