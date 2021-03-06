package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import server.game.PlayerMP;
import server.packet.Packet01PlayerJoin;
import server.packet.Packet02GiveID;
import server.packet.Packet05StartGame;
import server.packet.Packet14ChangeMoney;
import server.packet.Packet16SetCardSeed;

public class MMP
{
	
	public static void main(String[] args)
	{
		serverAcces = new MMP();
		getServer().Host();
	}
	
	private MMP()
	{
	}

	public void Host()
	{
		Log("Launching Server...");
		try
		{
			Log("Preparing OpenMMP Server...");
			game = new ServerGame();
			
			Log("Binding to port 27960... ");
			hostSocket = new ServerSocket(27960);
			
			Log("Launching Console Command Handler...");
			conComm = new ConsoleCommand(this);
			Thread t = new Thread(conComm);
			t.start();
			
			playGame();
			
			Log("Stopping Server...");
			hostSocket.close();
		}
		catch(Exception e)
		{
			Log("Error Occured!");
			e.printStackTrace();
		}
	}
	
	public void addPlayer(Socket player)
	{
		Log("Incoming Connection... (" + nextId + ")");
		try {
			PlayerMP playermp = new PlayerMP(player);
			playermp.setId(nextId++);
			game.setPlayer(playermp, playermp.getId());
			Packet02GiveID packet = new Packet02GiveID();
			packet.id = playermp.getId();
			getMonopoly().sendPacket(packet, packet.id);
			getMonopoly().sendPacket(new Packet16SetCardSeed(getMonopoly().getCardSeed()), packet.id);
			Log("Player " + packet.id + " has joined");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void startGame()
	{
		if(game.isPhase(1) && nextId >= 2)
		{
			Log("Stopping Player Acception");
			game.setPhase(2);
		}
		else
		{
			Log("Cannot start the Game (Already Started / Only 1 player)");
			Log("Player Count: " + nextId);
		}
	}
	
	private void playGame()
	{
		// Phase 1 - Waiting for players
		Log("Phase 1 - Waiting for players...");
		game.setPhase(1);
		ThreadPlayerAccept pAccepter = new ThreadPlayerAccept();
		pAccepter.start();
		while(game.isPhase(1))
		{
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
			}
		}
		pAccepter.interrupt();
		Log("Preparing Game...");
		
		getMonopoly().sendPacket(new Packet05StartGame(nextId));
		for(int i = 0; i < getMonopoly().getPlayers().length; i++)
		{
			PlayerMP player = getMonopoly().getPlayer(i);
			if(player != null)
			{
				getMonopoly().sendPacket(new Packet01PlayerJoin(player.getUsername(), player.getColorCode(), player.getId()));
				getMonopoly().sendPacket(new Packet14ChangeMoney(player.getId(), 150000));
				getMonopoly().getPlayer(i).incrementMoney(150000);
			}
		}
		
		Log("Starting Game...");
		// Phase 2 - Let's Play
		game.setPhase(2);
		getMonopoly().advanceTurn();
		
		while(game.isPhase(2))
		{
			/*
			 * This will halt the program until somethings happens which changes the game phase
			 * This currently can't happen and has to be replaced to once per 100ms to check if the game has ended
			 */
			Sleep(1);
		}
		
		Log("Game has ended...");
		try
		{
			hostSocket.close();
		} catch (IOException e)
		{
			System.out.println("Failed to close port!");
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
			e.printStackTrace();
		}
	}
	
	public static void Log(String line)
	{
		Log(line, true, true);
	}
	
	public static void Log(String line, boolean endline)
	{
		Log(line, endline, true);
	}
	
	public static void Log(String line, boolean endline, boolean addTime)
	{
		if(endline && addTime)
			System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "] " + line);
		else if(endline)
			System.out.println(line);
		else if(addTime)
			System.out.print("[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "] " + line);
	}
	
	//Game
	private ServerGame game;
	public ServerGame getMonopoly() { return game; }
	
	//Connection Data
	private byte nextId = 0;
	
	//Server
	private ServerSocket hostSocket;
	private ConsoleCommand conComm;
	
	//Getter
	public ServerSocket getSocket() { return hostSocket; }
	
	//Public Server Acces
	private static MMP serverAcces;
	public static MMP getServer() { return serverAcces; }

}
