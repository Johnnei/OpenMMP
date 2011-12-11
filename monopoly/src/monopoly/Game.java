package monopoly;

import gui.GameFrame;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import multiplayer.PlayerMP;
import multiplayer.packet.Packet03Username;
import multiplayer.packet.Packet04Colorcode;
import multiplayer.packet.Packet06NextTurn;
import multiplayer.packet.Packet07RollDice;
import multiplayer.packet.Packet11BuyStreet;

public class Game
{
	/* GUI Data */
	public String myPlayer;
	public GameFrame gframe;
	
	/* Game Data */
	private int[] dice;
	public Turn turn;
	public TownManager towns;
	
	/* MP Data */
	private byte myId = 127;
	PlayerMP[] players;
	public byte playerCount = 127;
	public byte gotPlayers = 1;
	
	/* Pass by Reference */
	private static Game game;
	public static Game Monopoly() { return game; }
	
	public PlayerMP getPlayer() { return players[myId]; }
	public byte getMyID() { return myId; }
	public void setMyID(byte b) { myId = b; }
	
	public static void main(String[] args)
	{
		game = new Game();
		game.StartGame();
	}
	
	private Game()
	{	
	}
	
	private void StartGame()
	{
		Log("OpenMMP, Open Source Monopoly Multiplayer!");
		Log("OpenMMP V0.1 Alpha");
		Log("Made by Johnnei");
		Log("Preparing OpenMMP");
		init();
		System.out.print("Username: ");
		String username = readInput();
		myPlayer = username;
		System.out.print("Color Code: 0x");
		String code = readInput();
		int colorCode = Integer.valueOf(code, 16);
		System.out.print("Server IP: ");
		String ip = readInput();
		Log("Connecting to Server...");
		try
		{
			Socket pSocket = new Socket(ip, 27960);
			PlayerMP socket = new PlayerMP(pSocket);
			socket.setUsername(username);
			socket.setColor(colorCode);
			Log("Waiting for ID...");
			while(myId == 127)
			{
				Thread.sleep(100);
			}
			socket.setId(myId);
			players[socket.getId()] = socket;
			Log("Sending Player Data...");
			getPlayer().addPacket(new Packet03Username(username, socket.getId()));
			getPlayer().addPacket(new Packet04Colorcode(colorCode, socket.getId()));
			Log("Waiting for Server to start the game!");
			
			while(playerCount != gotPlayers)
			{
				//Idle to keep connection, just for testing atm
				Sleep(100);
			}
			Log("Game Can Start!");
			new Thread(new GameFrame()).start();
			GameLoop();
		} catch (Exception e)
		{
			Log("Failed!");
			e.printStackTrace();
		}
	}
	
	public static void Sleep(long l)
	{
		try
		{
			Thread.sleep(l);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void GameLoop()
	{
		do
		{
			switch (turn.getEvent())
			{
				case none:
					Sleep(10);
					break;
				case rollDice:
					turn.rollDice();
					Game.Log("Rolling Dice...");
					break;
				case nextTurn:
					getPlayer().addPacket(new Packet06NextTurn());
					Game.Log("Ending turn...");
					break;
				case buyHouse:
					Game.Log("Buying Houses...");
					break;
				case buyStreet:
					getPlayer().addPacket(new Packet11BuyStreet(getPlayer().getId(), (byte)getPlayer().Index()));
					Game.Log("Buying Street...");
					break;
				case trade:
					Game.Log("Trading...");
					break;
			}
		} while (true);
	}
	
	private void init()
	{
		players = new PlayerMP[6];
		dice = new int[] { 1, 1};
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
	
	private static String readInput()
	{
		Scanner input = new Scanner(System.in);
		return input.nextLine();
	}
	
	public static void Log(String line)
	{
		System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "] " + line);
	}
	
	/* Public Getters / Setters */
	
	public void changePlayerMoney(byte id, int amount)
	{
		players[id].addMoney(amount);
	}

	public PlayerMP[] getPlayers()
	{
		return players;
	}
	
	public void registerPlayer(PlayerMP player)
	{
		players[player.getId()] = player;
		++gotPlayers;
	}
	
	public void setDice(byte dice, byte dice2)
	{
		this.dice[0] = dice;
		this.dice[1] = dice2;
	}
	
	public int getDice(int i)
	{
		return dice[i];
	}

	public int diceEyesCount()
	{
		return dice[0] + dice[1];
	}
}
