package gui;

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
import monopoly.TownManager;
import multiplayer.PlayerMP;

public class TradeGui extends JFrame implements ActionListener {
	
	JLabel[] textFields;
	JTextField[] moneyFields;
	JCheckBox[] placeGiveBoxes;
	JCheckBox[] placeGetBoxes;
	JButton[] tradeButtons;
	JComboBox playerList;
	boolean isRequest;
	
	public TradeGui() {
		this(false);
	}
	
	public TradeGui(boolean isRequestScreen) {
		isRequest = isRequestScreen;
		textFields = new JLabel[4];
		textFields[0] = new JLabel("Money Given: €");
		textFields[1] = new JLabel("Money Requested: €");
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
		PlayerMP[] players = Game.Monopoly().getPlayers();
		ArrayList<String> playerListTemp = new ArrayList<String>();
		for(int i = 0 ; i < 6; i++) {
			if(players[i] != null) {
				if(players[i].getId() != Game.Monopoly().getMyID())
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
		setLocation((int)(Game.Monopoly().getGameFrame().getLocationOnScreen().getX() + (Game.Monopoly().getGameFrame().getWidth() / 2) - (width / 2)), (int)(Game.Monopoly().getGameFrame().getLocationOnScreen().getY() + (Game.Monopoly().getGameFrame().getHeight() / 2) - (height / 2)));
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
		//Wrap it up
		pack();
	}
	
	/**
	 * Will update available items to select on the Trade Screen
	 */
	private void updateItems() {
		PlayerMP player = null;
		for(byte i = 0; i < 6; i++) {
			if(Game.Monopoly().getPlayer(i).getUsername().equals(playerList.getSelectedItem())) {
				player = Game.Monopoly().getPlayer(i);
				break;
			}
		}
		if(player == null)
			return;
		ArrayList<Street> ownedStreets = new ArrayList<Street>();
		ArrayList<Street> tradeableStreets = new ArrayList<Street>();
		TownManager townManager = Game.Monopoly().towns;
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
		for(int i = 0; i < ownedStreets.size(); i++) {
			Street s = ownedStreets.get(i);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox) {
			updateItems();
			generateScreen();
		}
	}
}
