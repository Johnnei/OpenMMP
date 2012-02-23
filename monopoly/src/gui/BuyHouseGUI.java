package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import monopoly.Game;
import monopoly.Street;
import monopoly.TownManager;
import multiplayer.packet.Packet09BuyHouse;

/**
 * Frame to handle Buying and Selling of housing.
 * @author Johnnei
 *
 */
public class BuyHouseGUI extends JFrame implements ActionListener {
	
	/**
	 * List of the available streets where the player can put houses on
	 */
	JComboBox streetList;
	Street[] ownedStreets;
	int selectedStreet;
	JButton[] buyHouseButtons;
	JButton[] sellHouseButtons;
	
	public BuyHouseGUI() {
		int width = 400;
		int height = 300;
		setTitle("openMMP - " + Game.VERSION + " - Housing");
		setIconImage(Images.getImages().Icon);
		setLocation((int)(Game.Monopoly().getGameFrame().getLocationOnScreen().getX() + (Game.Monopoly().getGameFrame().getWidth() / 2) - (width / 2)), (int)(Game.Monopoly().getGameFrame().getLocationOnScreen().getY() + (Game.Monopoly().getGameFrame().getHeight() / 2) - (height / 2)));
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setResizable(false);
		
		//Go through all streets (which can have housing) and check which are owned by the player
		ArrayList<Street> ownedStreets = new ArrayList<Street>();
		TownManager townManager = Game.Monopoly().getTownManager();
		
		if(townManager.hasCompleteStreet(Street.Ons_Dorp, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Ons_Dorp);
		if(townManager.hasCompleteStreet(Street.Arnhem, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Arnhem);
		if(townManager.hasCompleteStreet(Street.Haarlem, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Haarlem);
		if(townManager.hasCompleteStreet(Street.Utrecht, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Utrecht);
		if(townManager.hasCompleteStreet(Street.Groningen, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Groningen);
		if(townManager.hasCompleteStreet(Street.Gravenhaven, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Gravenhaven);
		if(townManager.hasCompleteStreet(Street.Groningen, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Groningen);
		if(townManager.hasCompleteStreet(Street.Rotterdam, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Rotterdam);
		if(townManager.hasCompleteStreet(Street.Amsterdam, Game.Monopoly().getMyID()))
			ownedStreets.add(Street.Amsterdam);
		
		String[] streets = new String[ownedStreets.size()];
		this.ownedStreets = new Street[ownedStreets.size()];
		
		for(int i = 0; i < ownedStreets.size(); i++) {
			this.ownedStreets[i] = ownedStreets.get(i);
			streets[i] = Street.toString(ownedStreets.get(i));
		}
		
		streetList = new JComboBox(streets);
		
		setLayout(new GridLayout(0, 2));
		add(streetList);
		add(new JPanel()); //Spacer to fill up the 2nd collum on the first row
		
		if(streets.length > 0) {
			streetList.setSelectedIndex(0);
			selectedStreet = 0;
			updateHouseButtons((String)streetList.getItemAt(0));
		} else {
			pack();
		}	
	}
	
	private void updateHouseButtons(String streetName) {
		selectedStreet = -1;
		for(int i = 0; i < ownedStreets.length; i++) {
			if(ownedStreets[i].toString().equals(streetName)) {
				selectedStreet = i;
				break;
			}
		}
		
		if(buyHouseButtons != null) {
			for(int i = 0; i < buyHouseButtons.length; i++) {
				remove(buyHouseButtons[i]);
				remove(sellHouseButtons[i]);
			}
		}
		
		if(selectedStreet == -1)
			return;
		
		TownManager townManager = Game.Monopoly().getTownManager();
		int streetSize = townManager.getStreetSize(ownedStreets[selectedStreet]);
		buyHouseButtons = new JButton[streetSize];
		sellHouseButtons = new JButton[streetSize];
		for(int i = 0; i < streetSize; i++) {
			JButton buy = new JButton();
			buy.setText("Buy House on " + townManager.getStreetNameByIndex(ownedStreets[selectedStreet], i));
			buy.addActionListener(this);
			JButton sell = new JButton();
			sell.setText("Sell House on " + townManager.getStreetNameByIndex(ownedStreets[selectedStreet], i));
			sell.addActionListener(this);
			buyHouseButtons[i] = buy;
			sellHouseButtons[i] = sell;
		}
		for(int i = 0; i < buyHouseButtons.length; i++) {
			add(buyHouseButtons[i]);
			add(sellHouseButtons[i]);
		}
		pack();
	}
	
	private void buyHouse(int index) {
		Game.Log("Buying House on " + Game.Monopoly().getTownManager().getStreetNameByIndex(ownedStreets[selectedStreet], index));
		int sIndex = Game.Monopoly().getTownManager().getTownIndexByStreetAndIndex(ownedStreets[selectedStreet], index);
		if(Game.Monopoly().getMyPlayer().getMoney() >= Game.Monopoly().getTownManager().getHousePrice(sIndex))
			Game.Monopoly().addPacket(new Packet09BuyHouse((byte)sIndex));
	}
	
	private void sellHouse(int index) {
		Game.Log("Selling House on " + Game.Monopoly().getTownManager().getStreetNameByIndex(ownedStreets[selectedStreet], index));
		int sIndex = Game.Monopoly().getTownManager().getTownIndexByStreetAndIndex(ownedStreets[selectedStreet], index);
		Game.Monopoly().addPacket(new Packet09BuyHouse((byte)sIndex, false));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox) {
			updateHouseButtons((String)((JComboBox)e.getSource()).getSelectedItem());
		} else {
			for(int i = 0; i < buyHouseButtons.length; i++) {
				if(e.getSource().equals(buyHouseButtons[i])) {
					buyHouse(i);
					return;
				}
				if(e.getSource().equals(sellHouseButtons[i])) {
					sellHouse(i);
					return;
				}
			}
		}
	}
}
