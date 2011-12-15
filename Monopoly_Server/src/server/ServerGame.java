package server;

import java.util.Random;

import monopoly.SpecialTown;
import monopoly.Street;
import monopoly.TownManager;
import monopoly.Turn;
import server.game.PlayerMP;
import server.packet.Packet;
import server.packet.Packet00SetTurn;

public class ServerGame
{
	
	public ServerGame()
	{
		players = new PlayerMP[6];
		turn = new Turn();
		turnId = -1;
		dice = new byte[] { 1, 1 };
		rand = new Random();
		initStreets();
	}
	
	/* Game Simulation */
	
	public void initStreets()
	{
		towns = new TownManager();
		towns.Add("Start", Street.Start, 0, 0, SpecialTown.Start);
		towns.Add("Dorpstraat", Street.Ons_Dorp, 1000, 500);
		towns.Add("Algemeen Fonds", Street.AlgemeenFonds, 0, 0, SpecialTown.Algemeen_Fonds);
		towns.Add("Brink", Street.Ons_Dorp, 1200, 600);
		towns.Add("Inkomsten Belasting", Street.Tax, 20000, 0, SpecialTown.Belasting);
		towns.Add("Station Zuid", Street.Station, 10000, 0, SpecialTown.Station);
		towns.Add("Steenstraat", Street.Arnhem, 5000, 1000);
		towns.Add("Kans", Street.Kans, 0, 0, SpecialTown.Kans);
		towns.Add("Ketelstraat", Street.Arnhem, 5000, 1000);
		towns.Add("Velperplein", Street.Arnhem, 6000, 1200);
		towns.Add("Gevangenis", Street.Jail, 0, 0, SpecialTown.Jail);
		towns.Add("Barteljorisstraat", Street.Haarlem, 7500, 2000);
		towns.Add("Electriciteits Bedrijf", Street.Voorziening, 10000, 0, SpecialTown.Voorzieningen);
		towns.Add("Zeilweg", Street.Haarlem, 7500, 2000);
		towns.Add("Houtstraat", Street.Haarlem, 8500, 2400);
		towns.Add("Station West", Street.Station, 10000, 0, SpecialTown.Station);
		towns.Add("Neude", Street.Utrecht, 12000, 3000);
		towns.Add("Algemeen Fonds", Street.AlgemeenFonds, 0, 0, SpecialTown.Algemeen_Fonds);
		towns.Add("Bilstraat", Street.Utrecht, 12000, 3000);
		towns.Add("Vreeburg", Street.Utrecht, 15000, 4000);
		towns.Add("Vrij Parkeren", Street.VrijParkeren, 0, 0, SpecialTown.Vrij_Parkeren);
		towns.Add("A-Kerkhof", Street.Groningen, 20000, 5000);
		towns.Add("Kans", Street.Kans, 0, 0, SpecialTown.Kans);
		towns.Add("Groote Markt", Street.Groningen, 20000, 5000);
		towns.Add("Heerestraat", Street.Groningen, 22000, 6000);
		towns.Add("Station Noord", Street.Station, 10000, 0, SpecialTown.Station);
		towns.Add("Spui", Street.Gravenhaven, 25000, 7000);
		towns.Add("Plein", Street.Gravenhaven, 25000, 7000);
		towns.Add("Waterleiding", Street.Voorziening, 10000, 0, SpecialTown.Voorzieningen);
		towns.Add("Lange Poten", Street.Gravenhaven, 26000, 8000);
		towns.Add("Naar de Gevangenis", Street.ToJail, 0, 0, SpecialTown.ToJail);
		towns.Add("Hofplein", Street.Rotterdam, 30000, 9000);
		towns.Add("Blaak", Street.Rotterdam, 30000, 9000);
		towns.Add("Algemeen Fonds", Street.AlgemeenFonds, 0, 0, SpecialTown.Algemeen_Fonds);
		towns.Add("Coolsingel", Street.Rotterdam, 32000, 10000);
		towns.Add("Station Oost", Street.Station, 10000, 0, SpecialTown.Station);
		towns.Add("Kans", Street.Kans, 0, 0, SpecialTown.Kans);
		towns.Add("Leidestraat", Street.Amsterdam, 40000, 15000);
		towns.Add("Extra Belasting", Street.Tax_Extra, 10000, 0, SpecialTown.Belasting_Extra);
		towns.Add("Kalverstraat", Street.Amsterdam, 45000, 20000);
	}
	
	public void rollDice()
	{
		dice[0] = (byte)(1 + rand.nextInt(6));
		dice[1] = (byte)(1 + rand.nextInt(6));
	}
	
	public byte[] getDices()
	{
		return dice;
	}
	
	public void advanceTurn()
	{
		while(true)
		{
			if(getPlayer(++turnId) != null)
			{
				sendPacket(new Packet00SetTurn(turnId));
				turn.ResetTurn(turnId);
				break;
			}
			if(turnId == 5)
				turnId = -1;
		}
	}
	
	/* MP Area */
	
	public PlayerMP getCurrentPlayer()
	{
		return getPlayer(MMP.getServer().Monopoly().turn.getID());
	}
	
	public void setPlayer(PlayerMP player, byte slot)
	{
		players[slot] = player;
	}
	
	public PlayerMP[] getPlayers()
	{
		return players;
	}
	
	public PlayerMP getPlayer(int i)
	{
		if(players[i] == null)
			return null;
		return players[i];
	}
	
	public void setUsername(byte b, String s)
	{
		players[b].setUsername(s);
	}
	
	public void setColor(byte b, int i)
	{
		players[b].setColor(i);
	}
	
	public void setPhase(int i)
	{
		phase = (byte)i;
	}
	
	public boolean isPhase(int i)
	{
		return (byte)i == phase;
	}
	
	public void setDice(byte diceA, byte diceB)
	{
		dice[0] = diceA;
		dice[1] = diceB;
	}
	
	public int diceEyesCount()
	{
		return dice[0] + dice[1];
	}
	
	public boolean diceDoubles()
	{
		return dice[0] == dice[1];
	}
	
	public void sendPacket(Packet p, byte id)
	{
		if(players[id] != null)
			players[id].addPacket(p);
	}
	
	public void sendPacket(Packet p)
	{
		for(int i = 0; i < 6; i++)
		{
			if(players[i] == null)
				continue;
			players[i].addPacket(p);
		}
	}
	
	public TownManager getTownManager()
	{
		return towns;
	}
	
	private byte[] dice;
	private byte phase;
	private PlayerMP[] players;
	private TownManager towns;
	public Turn turn;
	private byte turnId;
	private Random rand;
	
}
