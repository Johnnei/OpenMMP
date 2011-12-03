package monopoly;

import java.awt.Color;

public class Player
{

	private String username;
	private int money;
	private int value;
	private Color color;
	
	public Player()
	{
		
	}

	public Player(String user, int colorCode)
	{
		username = user;
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

	public void getMoney(int amount)
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
}
