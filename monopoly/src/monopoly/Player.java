package monopoly;

import java.awt.Color;

public class Player
{

	private String username;
	private int money;
	private int value;
	private int index;
	private Color color;
	private int colorCode;
	
	public Player()
	{
		
	}

	public Player(String user, int colorCode)
	{
		username = user;
		index = 0;
		money = 200000;
		value = money;
	}
	
	public void setUsername(String user)
	{
		username = user;
	}
	
	public void setColor(int code)
	{
		color = new Color(code);
		colorCode = code;
	}
	
	public void setIndex(int i)
	{
		index = i;
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

	public void addMoney(int amount)
	{
		money += amount;
		value += amount;
	}

	public void Buy(int cost)
	{
		money -= cost;
		value += cost;
	}

	public String Username()
	{
		return username;
	}
	
	public int Index()
	{
		return index;
	}

	public int Money()
	{
		return money;
	}

	public int Value()
	{
		return value;
	}

	public Color getColor()
	{
		return color;
	}
	
	public int getColorCode()
	{
		return colorCode;
	}
}
