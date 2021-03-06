package monopoly;

import gui.BuyHouseGUI;
import gui.GameFrame;
import gui.PopupManager;
import gui.TradeGui;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import multiplayer.PlayerMP;
import multiplayer.packet.Packet;
import multiplayer.packet.Packet03Username;
import multiplayer.packet.Packet04Colorcode;
import multiplayer.packet.Packet06NextTurn;
import multiplayer.packet.Packet11BuyStreet;

public class Game
{
	/* Public final static */
	public static final String VERSION = "Alpha 0.1";
	
	/* GUI Data */
	public String myPlayer;
	public GameFrame gframe;
	private String stateString;
	private String stateSubString;
	
	/* Game Data */
	private int[] dice;
	private int jackpot;
	public Turn turn;
	public TownManager towns;
	private CardDeck randomFunds;
	private CardDeck generalFunds;
	
	/* MP Data */
	private byte myId = 127;
	PlayerMP[] players;
	public byte playerCount = 127;
	public byte gotPlayers = 1;
	private boolean hasSeed = false;
	private long cardSeed;
	
	/* Pass by Reference */
	private static Game game;
	public static Game getMonopoly() { return game; }
	
	public PlayerMP getPlayer(byte b) { return players[b]; }
	public PlayerMP getMyPlayer() { return players[myId]; }
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
			getMyPlayer().addPacket(new Packet03Username(username, socket.getId()));
			getMyPlayer().addPacket(new Packet04Colorcode(colorCode, socket.getId()));
			Log("Waiting for Server to start the game!");
			
			while(playerCount != gotPlayers || !hasSeed)
			{
				//Halt the Thread until we got everything sorted.
				Sleep(100);
			}
			Log("Generating Card Decks");
			randomFunds = new CardDeck(cardSeed);
			generalFunds = new CardDeck(cardSeed);
			initCardDecks();
			Log("Game Can Start!");
			new Thread(new GameFrame()).start();
			GameLoop();
		} catch (Exception e)
		{
			Log("Failed!");
			e.printStackTrace();
		}
	}
	
	private void initCardDecks() {
		generalFunds.addCard(new Card("Your building contract has expired.\nPay �5000 to renew it.", -5000));
		generalFunds.addCard(new Card("You recieve 5% goverment rent", 7500));
		generalFunds.addCard(new Card("The bank made a mistake in your account, you've been refunded", 10000));
		generalFunds.addCard(new Card("The bank made a mistake in your account, you've been charged", -10000));
		generalFunds.addCard(new Card("The Tax-Company messed-up. You'll be charged.", -10000));
		generalFunds.addCard(new Card("Your investments have been paid.\nYou'll recieve �15000", 15000));
		generalFunds.addCard(new Card("Pay your monthly taxes.\nBill: �25000", -25000));
		generalFunds.addCard(new Card("You've been caught speeding.\nBill: �5000", -5000));
		
		randomFunds.addCard(new Card("Go to Jail!\nDo not pass start, You'll not recieve 20'000", 10, false));
		randomFunds.addCard(new Card("Go to Start!\nYou'll recieve �40'000", 0, true, true));
		randomFunds.addCard(new Card("You've been caught Drunk! Pay a �10'000 fine.", -10000));
		randomFunds.addCard(new Card("You found �100 on the streets.", 100));
		randomFunds.addCard(new Card("Go to Station East", 35, true, true));
		randomFunds.addCard(new Card("Go to Station West", 15, true, true));
		randomFunds.addCard(new Card("Pay your Car Rental\nYou'll be charged �5000", 5000));
		randomFunds.addCard(new Card("You've been trading houses, You've made: �10000 profit", 10000));
		
		generalFunds.shuffle();
		randomFunds.shuffle();
	}
	
	public static void Sleep(long l)
	{
		try
		{
			Thread.sleep(l);
		} catch (InterruptedException e)
		{
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
					getMyPlayer().addPacket(new Packet06NextTurn());
					Game.Log("Ending turn...");
					break;
				case buyHouse:
					Game.Log("Buying Houses...");
					PopupManager.manager.showFrame(new BuyHouseGUI());
					break;
				case buyStreet:
					getMyPlayer().addPacket(new Packet11BuyStreet());
					Game.Log("Buying Street...");
					break;
				case trade:
					PopupManager.manager.showFrame(new TradeGui());
					Game.Log("Trading...");
					break;
			}
		} while (true);
	}
	
	private void init()
	{
		players = new PlayerMP[6];
		dice = new int[] { 1, 1};
		jackpot = 0;
		resetStateString();
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
	
	/* Game */
	
	public void addPacket(Packet p) {
		players[getMyID()].addPacket(p);
	}
	
	public void movePlayer(int places)
	{
		byte pId = turn.getId();
		if(places > 0) //Move forward
		{
			while(places != 0)
			{
				getPlayer(pId).incrementIndex(1);
				--places;
				if(getPlayer(pId).getIndex() == 40)
					getPlayer(pId).incrementIndex(-40);
				gframe.board.doUpdate();
				Sleep(150);
			}
		}
		else //Move Backward
		{
			while(places != 0)
			{
				getPlayer(pId).incrementIndex(1);
				++places;
				if(getPlayer(pId).getIndex() == -1)
					getPlayer(pId).incrementIndex(40);
				gframe.board.doUpdate();
				Sleep(150);
			}
		}
		//Execute events on landing
		updateStateString();
	}
	
	/* Public Getters / Setters */
	
	public void updateStateString()
	{
		stateString = getCurrentUser() + " landed on " + getCurrentTown().getName();
		if(towns.isRandomFunds()) {
			Card c = randomFunds.drawCard();
			jackpot += c.getJackpotMoney();
			String[] s = c.getStateStrings();
			for(int i = 0; i < s.length; i++) {
				if(i == 0)
					stateString = s[i];
				else if (i == 1)
					stateSubString = s[i];
			}
		}
		else if(towns.isGeneralFunds()) {
			Card c = generalFunds.drawCard();
			jackpot += c.getJackpotMoney();
			String[] s = c.getStateStrings();
			for(int i = 0; i < s.length; i++) {
				if(i == 0)
					stateString = s[i];
				else if (i == 1)
					stateSubString = s[i];
			}
 		} else if (getCurrentIndex() == 10) { //Special Message for Jail
 			if(getCurrentPlayer().isInJail()) 
 				stateSubString = "You are in jail";
 			else
 				stateSubString = "You are visiting the jail";
 		} else if(getCurrentIndex() == 20) {
 			stateSubString = "You recieve �" + jackpot + " Jackpot Money!";
 			jackpot = 0;
 		} else if(towns.isInvalid(getCurrentIndex())) {
			stateSubString = "";
		} else if(towns.get(getCurrentIndex()).hasOwner()) {
			if(towns.get(getCurrentIndex()).isSameOwner(turn.getId()))
				stateSubString = getCurrentTown().getName() + " is " + getCurrentUser() + "'s own property";
			else if(!towns.isInvalid(getCurrentIndex()))
				stateSubString = getCurrentUser() + " needs to pay �" + towns.getCost(getCurrentIndex());
		}
		else
			stateSubString = getCurrentUser() + " can buy " + getCurrentTown().getName() + " for �" + getCurrentTown().getPrice();
	}
	
	public void resetStateString()
	{
		stateString = "";
		stateSubString = "";
	}
	
	public GameFrame getGameFrame() {
		return gframe;
	}
	
	public TownManager getTownManager() {
		return towns;
	}
	
	public Town getCurrentTown()
	{
		return towns.get(getCurrentIndex());
	}
	
	public Player getCurrentPlayer() {
		return getPlayers()[turn.getId()];
	}
	
	public String getCurrentUser()
	{
		return getPlayers()[turn.getId()].getUsername();
	}
	
	public int getCurrentIndex()
	{
		return getPlayers()[turn.getId()].getIndex();
	}
	
	public String getState()
	{
		return stateString;
	}
	
	public String getStateSub()
	{
		return stateSubString;
	}
	
	public void jailPlayer(byte id, boolean enter) {
		players[id].setJail(enter);
	}
	
	public void changePlayerMoney(byte id, int amount)
	{
		players[id].incrementMoney(amount);
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

	public void setCardSeed(long seed)
	{
		cardSeed = seed;
		hasSeed = true;
	}
}
