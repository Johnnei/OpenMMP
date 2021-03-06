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
	
	public int getStreetSize(Street s) {
		int count = 0;
		for(Town t : towns) {
			if(t.street == s)
				++count;
		}
		return count;
	}
	
	public String getStreetNameByIndex(Street s, int index) {
		int i = 0;
		for(Town t : towns) {
			if(t.street == s) {
				if(i++ == index) {
					return t.getName();
				}
			}
		}
		return "ERR_NO_TOWN_FOUND";
	}
	
	public boolean hasCompleteStreet(Street s, byte ownerId) {
		for(Town t : towns) {
			if(t.street == s) {
				if(t.getOwnerId() != ownerId)
					return false;
			}
		}
		return true;
	}
	
	public byte getTownIndexByName(String s) {
		for(int i = 0; i < towns.size(); i++) {
			if(get(i).getName().equals(s)) {
				return (byte)i;
			}
		}
		return (byte)-1;
	}
	
	public void setHouse(int index, int count) {
		Town t = towns.get(index);
		t.setHouseCount(count);
		towns.set(index, t);
	}
	
	/**
	 * Get the global Street index buy the given Street and the index relative from the street
	 * @param s Street to search in
	 * @param i Index relative to the start of the Street
	 * @return Index of the street on the normal Board Index
	 */
	public int getTownIndexByStreetAndIndex(Street s, int i) {
		int index = -1;
		for(Town t : towns) {
			++index;
			if(t.street == s) {
				if (i-- == 0)
					return index;
			}
		}
		return -1;
	}
	
	public boolean canTradeStreet(Street s) {
		for(int i = 0; i < towns.size(); i++) {
			Town t = towns.get(i);
			if(t.street == s) {
				if(!t.hasOwner())
					return false;
				if(t.getHouseCount() > 0)
					return false;
			}
		}
		return true;
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
	
	public void setOwner(byte pId, byte sIndex)
	{
		Town t = towns.get(sIndex);
		t.setOwner(Game.getMonopoly().getPlayer(pId));
		towns.set(sIndex, t);
	}

	public boolean isBuyable(int index)
	{
		if (towns.get(index).getPlayer() == null) {
			SpecialTown t = towns.get(index).getType();
			if(t == SpecialTown.Normaal || t == SpecialTown.Voorzieningen)
				return true;
			return false;
		} else
			return false;
	}
	
	public boolean isRandomFunds() {
		int cIndex = Game.getMonopoly().getCurrentIndex();
		return get(cIndex).getType() == SpecialTown.Kans;
	}
	
	public boolean isGeneralFunds() {
		int cIndex = Game.getMonopoly().getCurrentIndex();
		return get(cIndex).getType() == SpecialTown.Algemeen_Fonds;
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
		else
		// SpecialTown.Voorziening
		{
			int voorzieningCount = 1;
			if(towns.get(12).isSameOwner(towns.get(28).getOwnerId()))
				voorzieningCount = 2;
			return Game.getMonopoly().diceEyesCount() * 500 * voorzieningCount;
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
