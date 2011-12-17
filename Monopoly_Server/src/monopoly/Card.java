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
				//TODO Send packets to do normal movement and make de server handle it
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
				//TODO Send packets to do normal movement and make de server handle it
			}
			else
			{
				//TODO Send packets to move
			}
		}
			
	}
}
