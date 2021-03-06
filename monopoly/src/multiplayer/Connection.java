package multiplayer;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection extends Thread
{

	private static final Connection instance = new Connection();

	public static Connection Get()
	{
		return instance;
	}

	List<PlayerMP> clients;
	private String server;

	public void setServer(String server)
	{
		this.server = server;
	}

	private Connection()
	{
		clients = new ArrayList<PlayerMP>();
	}

	public void run()
	{
		Connect();
	}

	public void Connect()
	{
		try
		{
			socket = new Socket(server, 27960);
			socket.connect(null);
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e)
		{
		}
	}

	Socket socket;
	DataInputStream input;
	DataOutputStream output;

}
