package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import monopoly.Game;
import monopoly.Street;
import monopoly.Town;
import monopoly.TownManager;
import multiplayer.PlayerMP;
import multiplayer.packet.Packet13Trade;
import multiplayer.packet.Packet19TradeAnswer;

public class TradeGui extends JFrame implements ActionListener {
	
	JLabel[] textFields;
	JTextField[] moneyFields;
	JCheckBox[] placeGiveBoxes;
	JCheckBox[] placeGetBoxes;
	JButton[] tradeButtons;
	JComboBox playerList;
	byte otherPlayerId = -1;
	boolean isRequest;
	
	public TradeGui() {
		this(false);
	}
	
	public TradeGui(boolean isRequestScreen) {
		isRequest = isRequestScreen;
		textFields = new JLabel[4];
		textFields[0] = new JLabel("Money Given: �");
		textFields[1] = new JLabel("Money Requested: �");
		textFields[2] = new JLabel("Streets Given:");
		textFields[3] = new JLabel("Streets Requested:");
		moneyFields = new JTextField[2];
		moneyFields[0] = new JTextField();
		moneyFields[1] = new JTextField();
		//Generate tradeButtons
		tradeButtons = (isRequest) ? new JButton[2] : new JButton[1];
		if(isRequest) {
			tradeButtons[0] = new JButton("Accept");
			tradeButtons[1] = new JButton("Decline");
		} else
			tradeButtons[0] = new JButton("Send Request");
		//Generate a List of all Player Names
		PlayerMP[] players = Game.getMonopoly().getPlayers();
		ArrayList<String> playerListTemp = new ArrayList<String>();
		for(int i = 0 ; i < 6; i++) {
			if(players[i] != null) {
				if(players[i].getId() != Game.getMonopoly().getMyID())
					playerListTemp.add(players[i].getUsername());
			}
		}
		String[] pList = new String[playerListTemp.size()];
		for(int i = 0; i < pList.length; i++) {
			pList[i] = playerListTemp.get(i);
		}
		playerList = new JComboBox(pList);
		playerList.setSelectedIndex(0);
		placeGiveBoxes = new JCheckBox[0];
		placeGetBoxes = new JCheckBox[0];
		updateItems();
		generateScreen();
	}
	
	/**
	 * Generate the layout and JFrame
	 */
	private void generateScreen() {
		//Generate JFrame Dimensions
		int width = 500;
		int height = 400;
		setTitle("openMMP - " + Game.VERSION + " - Trading");
		setIconImage(Images.getImages().Icon);
		setLocation((int)(Game.getMonopoly().getGameFrame().getLocationOnScreen().getX() + (Game.getMonopoly().getGameFrame().getWidth() / 2) - (width / 2)), (int)(Game.getMonopoly().getGameFrame().getLocationOnScreen().getY() + (Game.getMonopoly().getGameFrame().getHeight() / 2) - (height / 2)));
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setResizable(false);
		//Generate Components and Layout
		setLayout(new GridLayout(0,2));
		//Generate Select Player Section
		add(playerList);
		add(new JPanel());//Spacer
		//Generate Tables for Towns and Money
		//Money Given
		add(textFields[0]);
		add(moneyFields[0]);
		//Money Requested
		add(textFields[1]);
		add(moneyFields[1]);
		//Given Streets
		add(textFields[2]);
		add(new JPanel());
		for(int i = 0; i < placeGiveBoxes.length; i++) {
			add(placeGiveBoxes[i]);
		}
		if(placeGiveBoxes.length % 2 != 0) 
			add(new JPanel());
		add(textFields[3]);
		add(new JPanel());
		for(int i = 0; i < placeGetBoxes.length; i++) {
			add(placeGetBoxes[i]);
		}
		if(placeGetBoxes.length % 2 != 0) 
			add(new JPanel());
		//Add Buttons
		for(int i = 0; i < tradeButtons.length; i++) {
			add(tradeButtons[i]);
		}
		//Wrap it up
		pack();
	}
	
	private void updateTradeItems(PlayerMP player, boolean isMyPlayer) {
		if(!isMyPlayer)
			otherPlayerId = player.getId();
		ArrayList<Street> ownedStreets = new ArrayList<Street>();
		ArrayList<Street> tradeableStreets = new ArrayList<Street>();
		JCheckBox[] streetBoxes; //Temporary value to prevent lots of if then else's
		TownManager townManager = Game.getMonopoly().towns;
		if(townManager.hasCompleteStreet(Street.Ons_Dorp, player.getId()))
			ownedStreets.add(Street.Ons_Dorp);
		if(townManager.hasCompleteStreet(Street.Arnhem, player.getId()))
			ownedStreets.add(Street.Arnhem);
		if(townManager.hasCompleteStreet(Street.Haarlem, player.getId()))
			ownedStreets.add(Street.Haarlem);
		if(townManager.hasCompleteStreet(Street.Utrecht, player.getId()))
			ownedStreets.add(Street.Utrecht);
		if(townManager.hasCompleteStreet(Street.Groningen, player.getId()))
			ownedStreets.add(Street.Groningen);
		if(townManager.hasCompleteStreet(Street.Gravenhaven, player.getId()))
			ownedStreets.add(Street.Gravenhaven);
		if(townManager.hasCompleteStreet(Street.Groningen, player.getId()))
			ownedStreets.add(Street.Groningen);
		if(townManager.hasCompleteStreet(Street.Rotterdam, player.getId()))
			ownedStreets.add(Street.Rotterdam);
		if(townManager.hasCompleteStreet(Street.Amsterdam, player.getId()))
			ownedStreets.add(Street.Amsterdam);
		//Filter streets, only "tradeable" streets/towns should be added
		for(int i = 0; i < ownedStreets.size(); i++) {
			Street s = ownedStreets.get(i);
			if(townManager.canTradeStreet(s)) {
				tradeableStreets.add(s);
			}
		}
		int totalSize = 0;
		for(int i = 0; i < tradeableStreets.size(); i++) {
			totalSize += townManager.getStreetSize(tradeableStreets.get(i));
		}
		Game.Log("Total Size: " + totalSize);
		streetBoxes = new JCheckBox[totalSize];
		for(int i = 0, currentItem = 0; i < tradeableStreets.size(); i++) {
			Street s = tradeableStreets.get(i);
			int streetSize = townManager.getStreetSize(s);
			for(int j = 0; j < streetSize; j++) {
				Town t = townManager.get(townManager.getTownIndexByStreetAndIndex(s, j));
				JCheckBox jc = new JCheckBox(t.getName());
				jc.setBackground(t.getColor());
				streetBoxes[currentItem++] = jc;
			}
		}
		//Push data to the class and wait for a repaint
		if(isMyPlayer)
			placeGiveBoxes = streetBoxes;
		else
			placeGetBoxes = streetBoxes;
	}
	
	/**
	 * Will update available items to select on the Trade Screen
	 */
	private void updateItems() {
		PlayerMP player = null;
		for(byte i = 0; i < 6; i++) {
			if(Game.getMonopoly().getPlayer(i).getUsername().equals(playerList.getSelectedItem())) {
				player = Game.getMonopoly().getPlayer(i);
				break;
			}
		}
		if(player == null)
			return;
		updateTradeItems(player, false);
		updateTradeItems(Game.getMonopoly().getMyPlayer(), true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox) {
			updateItems();
			generateScreen();
		} else if(e.getSource() instanceof JButton) {//One of the x tradeButtons[]
			for(int i = 0; i < tradeButtons.length; i++) {
				if(e.getSource().equals(tradeButtons[i])) {
					if(i == 0 && isRequest) {
						//Accept
						PopupManager.manager.close();
						Game.getMonopoly().addPacket(new Packet19TradeAnswer(true));
					} else if(i == 0) {
						//0 == MoneyGive, 1 == MoneyGet
						if(!isInt(moneyFields[0].getText())) {
							moneyFields[0].setBackground(Color.red);
							return;
						}
						if(!isInt(moneyFields[1].getText())) {
							moneyFields[1].setBackground(Color.red);
							return;
						}
						if(otherPlayerId == -1)
							return;
						int moneyGet = Integer.parseInt(moneyFields[1].getText());
						int moneyGive = Integer.parseInt(moneyFields[0].getText());
						//Send Request
						//Generate Data for Packet13
						ArrayList<Byte> streetsToGet = new ArrayList<Byte>();
						ArrayList<Byte> streetsToGive = new ArrayList<Byte>();
						for(int j = 0; j < placeGetBoxes.length; j++) {
							if(placeGetBoxes[j].isSelected())
								streetsToGet.add(Game.getMonopoly().getTownManager().getTownIndexByName(placeGetBoxes[j].getText()));
						}
						for(int j = 0; j < placeGiveBoxes.length; j++) {
							if(placeGiveBoxes[j].isSelected())
								streetsToGive.add(Game.getMonopoly().getTownManager().getTownIndexByName(placeGiveBoxes[j].getText()));
						}
						byte[] streetsGet = new byte[streetsToGet.size()];
						byte[] streetsGive = new byte[streetsToGive.size()];
						for(int j = 0; j < streetsGet.length; j++) {
							streetsGet[j] = streetsToGet.get(j);
						}
						for(int j = 0; j < streetsGive.length; j++) {
							streetsGive[j] = streetsToGive.get(j);
						}
						Packet13Trade trade = new Packet13Trade();
						trade.moneyGet = moneyGet;
						trade.moneyGive = moneyGive;
						trade.townsGet = streetsGet;
						trade.townsGive = streetsGive;
						trade.playerGetId = Game.getMonopoly().getMyID();
						trade.playerGiveId = otherPlayerId;
						Game.getMonopoly().addPacket(trade);
						PopupManager.manager.close();
					} else {
						//Decline
						PopupManager.manager.close();
						Game.getMonopoly().addPacket(new Packet19TradeAnswer(false));
					}
				}
			}
		}
	}
	
	private boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
