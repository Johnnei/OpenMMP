package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

public class MMP
{

	public void Host()
	{
		System.out.println("Launching Server...");
		try
		{
			System.out.print("Binding to port 27960... ");
			hostSocket.bind(new InetSocketAddress(27960));
			System.out.println("Succeed");
			playGame();
			System.out.print("Stopping Server...");
			hostSocket.close();
			System.out.println("Succeed");
		}
		catch(IOException e)
		{
			System.out.println("failed");
			e.printStackTrace();
		}
	}
	
	private void playGame()
	{
		
	}

	DataInputStream input;
	DataOutputStream output;
	ServerSocket hostSocket;

}
