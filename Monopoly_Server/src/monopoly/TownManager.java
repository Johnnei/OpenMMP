package monopoly;

import java.util.List;
import java.util.ArrayList;

import server.MMP;

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
	
	public boolean isTownType(int sIndex, SpecialTown t)
	{
		return towns.get(sIndex).getType() == t;
	}
	
	public void setOwner(int pId, int sIndex)
	{
		Town t = towns.get(sIndex);
		t.setOwner((byte)pId);
		towns.set(sIndex, t);
	}
	
	public void setHouseCount(int index, int count) {
		Town t = towns.get(index);
		t.setHouseCount(count);
		towns.set(index, t);
	}
	
	public int getPayPrice(int index)
	{
		Town town = towns.get(index);
		if(town.getType() == SpecialTown.Belasting)
			return 20000;
		if(town.getType() == SpecialTown.Belasting_Extra)
			return 10000;
		if(!town.hasOwner())
			return 0;
		if(town.getType() == SpecialTown.Kans || town.getType() == SpecialTown.Algemeen_Fonds)
			return 0;
		else
			return getCost(index);
	}
	
	public int getPrice(int index)
	{
		return towns.get(index).getPrice();
	}
	
	/**
	 * Get the Street by the given index
	 * @param index of the town
	 * @return Street of the town
	 */
	public Street getStreetByIndex(int index) {
		return towns.get(index).street;
	}
	
	/**
	 * Checks if the giver PlayerId owns all streets belonging to the given index
	 * @param playerId, Id of the Player who should own the street
	 * @param sIndex, Index of the Town which is part of the street
	 * @return Player owns all town in the street? true, else false
	 */
	public boolean hasCompleteStreet(int playerId, int sIndex) {
		Street s = getStreetByIndex(sIndex);
		for(Town t : towns) {
			if(t.street == s) {
				if(t.getOwnerId() != playerId)
					return false;
			}
		}
		return true;
	}
	
	public boolean isHouseInvalid(int index) {
		if (isInvalid(index) || index == 5 || index == 15 || index == 25 || index == 35)
			return true;
		return false;
	}
	
	public boolean isInvalid(int index)
	{
		return 
		(
			index == 0 || index == 10 || index == 20 || index == 30 ||//Corners
			index == 2 || index == 17 || index == 33 || //General Funds
			index == 7 || index == 22 || index == 36 || //Random Funds
			index == 4 || index == 38//Taxes
		);
	}

	public boolean isBuyable(int index)
	{
		if(isInvalid(index))
			return false;
		Town town = towns.get(index);
		if (!towns.get(index).hasOwner())
		{
			SpecialTown t = town.getType();
			if(t == SpecialTown.Normaal || t == SpecialTown.Voorzieningen || t == SpecialTown.Station)
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
			int[] cost = new int[] { 1250, 5000, 10000, 20000 };
			int stationCount = 0;
			byte ownerId = t.getOwnerId();
			if(towns.get(5).isSameOwner(ownerId))
				stationCount++;
			if(towns.get(15).isSameOwner(ownerId))
				stationCount++;
			if(towns.get(25).isSameOwner(ownerId))
				stationCount++;
			if(towns.get(35).isSameOwner(ownerId))
				stationCount++;
			return cost[stationCount - 1];
		}
		else if(t.getType() == SpecialTown.Voorzieningen)
		{
			int voorzieningCount = 1;
			if(towns.get(12).isSameOwner(towns.get(28).getOwnerId()))
				voorzieningCount = 2;
			return MMP.getServer().Monopoly().diceEyesCount() * 500 * voorzieningCount;
		}
		else
			return t.getCost();
	}
	
	public int getHousePrice(int index) {
		if(index < 10)
			return 5000;
		else if (index < 20)
			return 10000;
		else if (index < 30)
			return 15000;
		else
			return 20000;
	}

	public void Add(String townName, Street streetName, int Price, int Cost)
	{
		Add(townName, streetName, Price, Cost, SpecialTown.Normaal);
	}

	public void Add(String townName, Street streetName, int Price, int Cost, SpecialTown Special)
	{
		if (streetIsFull(streetName))
		{
			MMP.Log("Can not Add \"" + townName + "\" in " + streetName.toString() + ": Street is full");
			return;
		}
		towns.add(new Town(townName, streetName, Price, Cost, Special));
	}

}
