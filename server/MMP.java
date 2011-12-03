package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import multiplayer.PlayerMP;
import multiplayer.packet.Packet01PlayerJoin;

public class MMP
{
	
	public static void main(String[] args)
	{
		MMP server = new MMP();
		server.Host();
	}

	public void Host()
	{
		System.out.println("Launching Server...");
		try
		{
			System.out.print("Preparing OpenMMP Server...");
			game = new ServerGame(this);
			System.out.println("Done");
			
			System.out.print("Binding to port 27960... ");
			hostSocket = new ServerSocket(27960);
			System.out.println("Succeed");
			
			System.out.print("Launching Console Command Handler...");
			conComm = new ConsoleCommand(this);
			Thread t = new Thread(conComm);
			t.start();
			System.out.println("Succeed");
			
			playGame();
			
			System.out.print("Stopping Server...");
			hostSocket.close();
			System.out.println("Succeed");
		}
		catch(Exception e)
		{
			System.out.println("failed");
			e.printStackTrace();
		}
	}
	
	public void addPlayer(PlayerMP player)
	{
		for(int i = 0; i < 6; i++)
		{
			game.getPlayer(i).addPacket(new Packet01PlayerJoin(player.Username()));
		}
	}
	
	public void startGame()
	{
		if(game.isPhase(1))
		{
			System.out.println("Stopping Player Acception");
			game.setPhase(2);
		}
	}
	
	private void playGame()
	{
		// Phase 1 - Waiting for players
		System.out.println("Phase 1 - Waiting for players...");
		ThreadPlayerAccept pAccepter = new ThreadPlayerAccept(this);
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
		System.out.println("Starting Game...");
		// Phase 2 - Let's Play
		System.out.println("Game has ended...");
		try
		{
			hostSocket.close();
		} catch (IOException e)
		{
			System.out.println("Failed to close port!");
			e.printStackTrace();
		}
	}
	
	//Game
	private ServerGame game;
	
	//Server
	private ServerSocket hostSocket;
	private ConsoleCommand conComm;
	
	//Getter
	public ServerSocket getSocket() { return hostSocket; }

}
