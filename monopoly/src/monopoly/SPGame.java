package monopoly;

import gui.GameFrame;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

import multiplayer.Connection;

public class SPGame
{

	public TownManager towns;
	public GameFrame gframe;
	public boolean host;
	public String myPlayer;
	public Turn turn;
	int[] dice;
	Player[] players;
	Random random;
	Thread mpThread;
	public boolean started = false;
	int currentPlayer = 0;
	final static SPGame instance = new SPGame();

	public static SPGame Monopoly()
	{
		return instance;
	}

	private SPGame()
	{
		host = false;
		towns = new TownManager();
		dice = new int[] { 1, 1 };
		players = new Player[6];
		random = new Random();
	}

	public void run()
	{
		Log("Initialising Game Board");
		players[0] = new Player(myPlayer, 0x000000);
		turn = new Turn(this, players[0]);
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
		Log("Starting Game GUI");
		new Thread(new GameFrame()).start();
		Log("Made by Johnnei");
		GameLoop();
	}

	public void GameLoop()
	{
		do
		{
			switch (turn.getEvent())
			{
				case none:
					Sleep(100);
					break;
				case rollDice:
					turn.rollDice();
					break;
				case nextTurn:
					NextTurn();
					break;
				case buyHouse:
					break;
				case buyStreet:
					break;
				case trade:
					break;
			}
		} while (true);
	}

	public void rollDice()
	{
		dice[0] = 1 + random.nextInt(6);
		dice[1] = 1 + random.nextInt(6);

		int movePlaces = diceEyesCount();
		int index = players[currentPlayer].Index();
		while (movePlaces-- > 0)
		{
			index = (index + 1 >= 40) ? index = 0 : index + 1;
			players[currentPlayer].setIndex(index);
			if (index == 0)
				players[currentPlayer].getMoney(20000);
			gframe.board.doUpdate();
			Sleep(500);
		}
		Town t = towns.get(players[currentPlayer].Index());
		if (t.getType() == SpecialTown.Start)
		{
			players[currentPlayer].getMoney(20000);
		}
	}

	private void Sleep(long ms)
	{
		try
		{
			Thread.sleep(ms);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void NextTurn()
	{
		do
		{
			if (++currentPlayer >= 6)
				currentPlayer = 0;
			turn = new Turn(this,players[currentPlayer]);
		} while (players[currentPlayer] == null);
	}

	public int diceEyesCount()
	{
		return dice[0] + dice[1];
	}

	public int getDice(int i)
	{
		return dice[i];
	}

	public Player[] getPlayers()
	{
		return players;
	}

	public void setPlayer(int i, Player p)
	{
		players[i] = p;
	}

	public static void Log(String line)
	{
		System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "] " + line);
	}

	private static String readInput()
	{
		Scanner input = new Scanner(System.in);
		return input.nextLine();
	}

	public static void main(String[] args)
	{
		System.out.print("Username: ");
		String user = readInput();
		SPGame.Monopoly().myPlayer = user;
		System.out.print("Server: ");
		String server = readInput();
		System.out.print("Connecting... ");
		Connection.Get().setServer(server);
		SPGame.Monopoly().mpThread = new Thread(Connection.Get());
		SPGame.Monopoly().mpThread.setDaemon(true);
		SPGame.Monopoly().mpThread.start();
		SPGame.Monopoly().run();
		System.out.println("Press any key to Start the Game!");
		readInput();
		SPGame.Monopoly().started = true;
	}

}