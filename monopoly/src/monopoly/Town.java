package monopoly;

import java.awt.Color;
import java.awt.Graphics;

import multiplayer.PlayerMP;

public class Town
{

	public Street street;
	private String name;
	private PlayerMP owner;
	private int houseCount;
	private int price;
	private int cost;
	private SpecialTown type;
	private Color color;

	public Town(String name, Street street, int price, int cost)
	{
		this.cost = cost;
		this.name = name;
		this.price = price;
		this.street = street;
		houseCount = 0;
		type = SpecialTown.Normaal;
	}
	
	public Town(String name, Street street, int price, int cost, Color c) {
		this(name, street, price, cost);
		color = c;
	}

	public Town(String name, Street street, int price, int cost, SpecialTown special)
	{
		this(name, street, price, cost);
		type = special;
	}
	
	public void setHouseCount(int count) {
		houseCount = count;
	}

	public void paint(Graphics g, int x, int y, int width, int height, boolean hor)
	{
		if (owner != null)
		{
			g.setColor(owner.getColor());
			g.drawRect(x, y, width, height);
			if(hor)
			{
				g.drawRect(x + 1, y + 2, width - 2, height - 3);
				g.drawRect(x + 2, y + 3, width - 4, height - 5);
			}
			else
			{
				g.drawRect(x + 2, y + 1, width - 4, height - 2);
				g.drawRect(x + 3, y + 2, width - 6, height - 4);
			}
			
			boolean isLeft = (y == 520 || x == 0); //Left side of board (diagonal left top, right bot)
			
			if (houseCount == 5) //Hotel
			{
				int hWidth = 35;
				if (isLeft)
				{
					if(hor)
					{
						g.setColor(new Color(Integer.valueOf("000000", 16)));
						g.drawRect(x + 7, y + 5, hWidth + 1, 9);
						g.setColor(new Color(Integer.valueOf("ff0000", 16)));
						g.fillRect(x + 8, y + 6, hWidth, 8);
					}
					else
					{
						g.setColor(new Color(Integer.valueOf("000000", 16)));
						g.drawRect(x + width - 16, y + 6, 9, hWidth + 1);
						g.setColor(new Color(Integer.valueOf("ff0000", 16)));
						g.fillRect(x + width - 15, y + 7, 8, hWidth);
					}
				}
				else
				{
					if(hor)
					{
						g.setColor(new Color(Integer.valueOf("000000", 16)));
						g.drawRect(x + 7, y + height - 13, hWidth + 1, 9);
						g.setColor(new Color(Integer.valueOf("ff0000", 16)));
						g.fillRect(x + 8, y + height - 12, hWidth, 8);
					}
					else
					{
						g.setColor(new Color(Integer.valueOf("000000", 16)));
						g.drawRect(x + 6, y + 5, 9, hWidth + 1);
						g.setColor(new Color(Integer.valueOf("ff0000", 16)));
						g.fillRect(x + 7, y + 6, 8, hWidth);
					}
				}
			}
			else if(houseCount > 0)
			{
				int hWidth = 10;
				if (isLeft)
				{
					if(hor)
					{
						for(int i = 0; i < houseCount; i++)
						{
							g.setColor(new Color(Integer.valueOf("000000", 16)));
							g.drawRect(x + 2 + (12 * i), y + 5, hWidth + 1, 9);
							g.setColor(new Color(Integer.valueOf("00ff00", 16)));
							g.fillRect(x + 3 + (12 * i), y + 6, hWidth, 8);
						}
					}
					else
					{
						for(int i = 0; i < houseCount; i++)
						{
							g.setColor(new Color(Integer.valueOf("000000", 16)));
							g.drawRect(x + width - 16, y + 2 + (i * 12), hWidth + 1, 9);
							g.setColor(new Color(Integer.valueOf("00ff00", 16)));
							g.fillRect(x + width - 15, y + 3 + (i * 12), hWidth, 8);
						}
					}
				}
				else
				{
					if(hor)
					{
						for(int i = 0; i < houseCount; i++)
						{
							g.setColor(new Color(Integer.valueOf("000000", 16)));
							g.drawRect(x + 2 + (12 * i), y + height - 13, hWidth + 1, 9);
							g.setColor(new Color(Integer.valueOf("00ff00", 16)));
							g.fillRect(x + 3 + (12 * i), y + height - 12, hWidth, 8);
						}
					}
					else
					{
						for(int i = 0; i < houseCount; i++)
						{
							g.setColor(new Color(Integer.valueOf("000000", 16)));
							g.drawRect(x + 4, y + 2 + (i * 12), hWidth + 1, 9);
							g.setColor(new Color(Integer.valueOf("00ff00", 16)));
							g.fillRect(x + 5, y + 3 + (i * 12), hWidth, 8);
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the buy price of this town
	 * @return buy price
	 */
	public int getPrice()
	{
		return price;
	}

	/**
	 * Gets the "rent" price of this town
	 * @return costs on landing
	 */
	public int getCost()
	{
		return cost + ((((cost * 10) - cost) / 5) * houseCount);
	}
	
	/**
	 * Gets the amount of houses on this town
	 * @return House Count
	 */
	public int getHouseCount() {
		return houseCount;
	}

	public String getOwner()
	{
		return (owner == null) ? null : owner.getUsername();
	}
	
	public byte getOwnerId()
	{
		if(!hasOwner())
			return 127;
		return owner.getId();
	}
	
	public boolean isSameOwner(int id)
	{
		if(!hasOwner())
			return false;
		return id == owner.getId();
	}
	
	public boolean hasOwner()
	{
		return owner != null;
	}

	public Player getPlayer()
	{
		return owner;
	}

	public void setOwner(PlayerMP player)
	{
		owner = player;
	}

	public void removeOwner()
	{
		owner = null;
	}

	public String getName()
	{
		return name;
	}

	public SpecialTown getType()
	{
		return type;
	}
	
	public Color getColor() {
		return (color != null) ? color : null;
	}
	
	public String toString() {
		return getName();
	}

}
