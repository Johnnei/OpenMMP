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
		String sMoney = Integer.toString(money);
		for (int i = sMoney.length(), j = 1; i > 0; i--, j++)
		{
			if (j % 3 == 0 && i != sMoney.length())
			{
				sMoney = sMoney.substring(0, i) + "'" + sMoney.substring(i);
			}
		}
		return sMoney;
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
	
	public void addIndex(int i)
	{
		index += i;
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
