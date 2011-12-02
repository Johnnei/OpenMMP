package monopoly;

import java.awt.Graphics;

public class Town {
	
	public Street street;
	private String name;
	private Player owner;
	private int houseCount;
	private int price;
	private int cost;
	private SpecialTown type;
	
	public Town(String name, Street street, int price, int cost)
	{
		this.cost = cost;
		this.name = name;
		this.price = price;
		this.street = street;
		houseCount = 0;
		type = SpecialTown.Normaal;
	}
	
	public Town(String name, Street street, int price, int cost, SpecialTown special)
	{
		this(name, street, price, cost);
		type = special;
	}
	
	public boolean isBuyable()
	{
		if(owner.Username() == null)
		{
			if(type == SpecialTown.Normaal || type == SpecialTown.Station || type == SpecialTown.Voorzieningen)
			{
				return true;
			}
		}
		return false;
	}
	
	public void paint(Graphics g, int x, int y, int width, int height, boolean hor)
	{
		if(owner != null)
		{
			g.setColor(owner.getColor());
			g.drawRect(x, y, width, height);
			if(houseCount > 0)
			{
				if(houseCount == 5)
				{
					if(hor)
					{
						
					}
					else
					{
						
					}
				}
				else
				{
					if(hor)
					{
						
					}
					else
					{
						
					}
				}
			}
		}
	}
	
	public int getPrice() { return price; }
	public int getCost() { return cost + ((((cost*10)-cost)/5)*houseCount); }
	public String getOwner() { return (owner == null) ? null : owner.Username(); }
	public Player getPlayer() { return owner; }
	public void setOwner(Player player) { owner = player; }
	public void removeOwner(Player player) { owner = null; }
	public String getName() { return name; }
	public SpecialTown getType() { return type; }

}
