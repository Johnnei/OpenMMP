package monopoly;

import server.MMP;
import server.packet.Packet14ChangeMoney;
import server.packet.Packet15SetIndex;

public class Card
{
	String text; //Text on card
	int moneyChange; //Money gain
	byte gotoIndex; //Forced goto index
	byte moveIndex; //Forced move amount
	boolean gotoIndexNormal; //If the movement should apply normal effects
	
	public Card(String txt, int moneyChange)
	{
		this.text = txt;
		this.moneyChange = moneyChange;
	}
	
	public Card(String text, int index, boolean move, boolean normal)
	{
		this(text, 0);
		gotoIndexNormal = move;
		if(move)
			moveIndex = (byte)index;
		else
			gotoIndex = (byte)index;
	}
	
	public Card(String text, int index, boolean move)
	{
		this(text, index, move, false);
	}
	
	public String[] getStateStrings()
	{
		if(text.contains("\n"))
			return text.split("\n");
		return new String[] { text };
	}
	
	public void Handle()
	{
		if(moneyChange != 0)
		{
			MMP.getServer().Monopoly().getCurrentPlayer().getMoney(moneyChange);
			MMP.getServer().Monopoly().sendPacket(new Packet14ChangeMoney(MMP.getServer().Monopoly().getCurrentPlayer().getId(), moneyChange));
		}
		if(gotoIndex > -1)
		{
			if(gotoIndexNormal)
			{
				//TODO Send packets to do normal movement and make the server handle it
			}
			else
			{
				MMP.getServer().Monopoly().sendPacket(new Packet15SetIndex(MMP.getServer().Monopoly().getCurrentPlayer().getId(), gotoIndex));
			}
		}
		if(moveIndex != 0)
		{
			if(gotoIndexNormal)
			{
				//TODO Send packets to do normal movement and make the server handle it
			}
			else
			{
				byte index = MMP.getServer().Monopoly().getCurrentPlayer().getIndex();
				index += moveIndex;
				MMP.getServer().Monopoly().sendPacket(new Packet15SetIndex(MMP.getServer().Monopoly().getCurrentPlayer().getId(), index));
			}
		}
			
	}
}
