package multiplayer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import monopoly.Game;

public class Connection extends Thread {
	
	private static final Connection instance = new Connection();
	public static Connection Get() { return instance; }
	private boolean isHost() { return Game.Monopoly().host; }
	List<PlayerMP> clients;
	public boolean hostMode = false;
	private String server;
	
	public void setMode(String server)
	{
		if(server == null)
			hostMode = true;
		else
			this.server = server;
	}
	
	private Connection()
	{
		clients = new ArrayList<PlayerMP>();
	}
	
	public void run()
	{
		if(hostMode)
			Host();
		else
			Connect();
	}
	
	public void Connect()
	{
		try
		{
			playerSocket = new Socket(server, 27960);
			if(server.equals("127.0.0.1"))
			{
				try
				{
					sleep(100);
				}
				catch(Exception e){}
			}
			playerSocket.connect(null); //TODO: Look what to pass here
			input = new DataInputStream(playerSocket.getInputStream());
			output = new DataOutputStream(new BufferedOutputStream(playerSocket.getOutputStream()));
		}
		catch(IOException e){}
	}
	
	public void Host()
	{
		try
		{
			Game.Monopoly().host = true;
			hostSocket = new ServerSocket(27960, 0, null);
			hostConnection = new Connection(); //SNEAKY SNEAKY!
			hostConnection.setMode("127.0.0.1");
			Thread t = new Thread(hostConnection);
			t.setDaemon(true);
			t.start();
			input = new DataInputStream(hostConnection.playerSocket.getInputStream());
			output = new DataOutputStream(new BufferedOutputStream(hostConnection.playerSocket.getOutputStream()));
			new ServerHandler().start(); //Handles All Server Input/Output
			System.out.println(" Done");
			while(!Game.Monopoly().started) //This Will make this Thread the Client Join Thread
			{
				clients.add(new PlayerMP(clients.size(), hostSocket.accept()));
			}
		}
		catch(IOException e){}
	}
	
	ServerSocket hostSocket;
	Connection hostConnection;
	Socket playerSocket;
	DataInputStream input;
	DataOutputStream output;
	
}
