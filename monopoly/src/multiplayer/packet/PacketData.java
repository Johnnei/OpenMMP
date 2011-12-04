package multiplayer.packet;

import java.util.HashMap;

import monopoly.Game;

public class PacketData
{
	public static HashMap<Class, Integer> classToID = new HashMap<Class, Integer>();
	public static HashMap<Integer, Class> IDToClass = new HashMap<Integer, Class>();
	
	private static PacketData packetData = new PacketData();
	public static PacketData getData() { return packetData; } 
}
