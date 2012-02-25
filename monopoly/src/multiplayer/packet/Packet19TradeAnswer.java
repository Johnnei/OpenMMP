package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet19TradeAnswer extends Packet
{

	boolean accepted;
	
	public Packet19TradeAnswer() {
		
	}
	
	public Packet19TradeAnswer(boolean answer) {
		accepted = answer;
	}
	
	@Override
	public void readData(DataInputStream d) throws IOException
	{
		accepted = d.readBoolean();
	}

	@Override
	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeBoolean(accepted);
	}

	@Override
	public void handle()
	{
	}

}
