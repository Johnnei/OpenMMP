package server.game;

import java.awt.Color;

import server.MMP;
import server.packet.Packet18JailData;

public class Player
{

	private String username;
	private int money;
	private int value;
	protected byte index;
	private Color color;
	private int colorcode;
	private boolean inJail;
	private byte jailTime;

	public Player()
	{

	}

	public Player(String user, int colorCode)
	{
		username = user;
		money = 0;
		value = money;
		index = 0;
		inJail = false;
		jailTime = 0;
	}

	public void setUsername(String user)
	{
		username = user;
	}

	public void setColor(int code)
	{
		color = new Color(code);
		colorcode = code;
	}

	public void addIndex(byte value)
	{
		index += value;
		if (index > 40)
			index -= 40;
		else if (index < 0)
			index += 40;
	}

	public byte getIndex()
	{
		return index;
	}

	public String MoneyString()
	{
		String smoney = Integer.toString(money);
		for (int i = smoney.length(); i > 0; i--)
		{
			if (i % 3 == 0 && i != smoney.length())
			{
				smoney = smoney.substring(0, i) + "'" + smoney.substring(i);
			}
		}
		return smoney;
	}
	
	
	public void jailPlayer()
	{
		index = 10;
		inJail = true;
	}
	
	public void releaseFromJail()
	{
		inJail = false;
		jailTime = 0;
	}
	
	public void addJailTime()
	{
		++jailTime;
	}
	
	public byte getJailTime()
	{
		return jailTime;
	}
	
	public boolean isInJail()
	{
		return inJail;
	}

	public int getColorCode()
	{
		return colorcode;
	}
	
	public void pay(int amount) {
		money -= amount;
		value -= amount;
	}

	public void incrementMoney(int amount)
	{
		money += amount;
		value += amount;
	}

	public void Buy(int cost)
	{
		money -= cost;
		value += cost;
	}

	public String getUsername()
	{
		return username;
	}

	public int getMoney()
	{
		return money;
	}

	public int getValue()
	{
		return value;
	}

	public Color getColor()
	{
		return color;
	}
}
