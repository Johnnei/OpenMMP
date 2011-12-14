package monopoly;

import java.util.List;
import java.util.ArrayList;

public class TownManager
{

	public List<Town> towns;

	public TownManager()
	{
		towns = new ArrayList<Town>();
	}

	public Town get(int i)
	{
		return towns.get(i);
	}
	
	public int size()
	{
		return towns.size();
	}

	private boolean streetIsFull(Street s)
	{
		int count = 0;
		for (int i = 0; i < towns.size(); i++)
		{
			if (((Town) towns.get(i)).street == s)
			{
				if (++count == 4)
					return true;
			}
		}
		return false;
	}
	
	public void setOwner(byte pId, byte sIndex)
	{
		Town t = towns.get(sIndex);
		t.setOwner(Game.Monopoly().getPlayer(pId));
		towns.set(sIndex, t);
	}

	public boolean isBuyable(int index)
	{
		if (towns.get(index).getPlayer() == null)
		{
			SpecialTown t = towns.get(index).getType();
			if(t == SpecialTown.Normaal || t == SpecialTown.Voorzieningen)
				return true;
			return false;
		}
		else
			return false;
	}

	public int getCost(int index)
	{
		Town t = towns.get(index);
		if (t.getType() == SpecialTown.Normaal)
			return t.getCost();
		else if (t.getType() == SpecialTown.Station)
		{
			int cost = 1250;
			for (int i = 0; i < towns.size(); i++)
			{
				Town t2 = towns.get(i);
				if (t2.getType() == SpecialTown.Station && t2.getOwner().equals(t.getOwner()))
				{
					cost *= 2;
				}
			}
			return cost;
		}
		else
		// SpecialTown.Voorziening
		{
			int voorzieningen = 0;
			for (int i = 0; i < towns.size(); i++)
			{
				Town t2 = towns.get(i);
				if (t2.getType() == SpecialTown.Voorzieningen && t2.getOwner().equals(t.getOwner()))
				{
					voorzieningen++;
				}
			}
			return Game.Monopoly().diceEyesCount() * voorzieningen * 500;
		}
	}

	public void Add(String townName, Street streetName, int Price, int Cost)
	{
		Add(townName, streetName, Price, Cost, SpecialTown.Normaal);
	}

	public void Add(String townName, Street streetName, int Price, int Cost, SpecialTown Special)
	{
		if (streetIsFull(streetName))
		{
			Game.Log("Can not Add \"" + townName + "\" in " + streetName.toString() + ": Street is full");
			return;
		}
		towns.add(new Town(townName, streetName, Price, Cost, Special));
	}

}
