package gui;

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
	JButton[] buyHouseButtons;
	JButton[] sellHouseButtons;
	
	public BuyHouseGUI() {
		setTitle("openMMP - " + Game.VERSION + " - Housing");
		
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
		if(streets.length > 0) {
			streetList.setSelectedIndex(0);
			updateHouseButtons((String)streetList.getItemAt(0));
		}
			
	}
	
	private void updateHouseButtons(String streetName) {
		TownManager townManager = Game.Monopoly().getTownManager();
		int streetSize = townManager.getStreetSize(ownedStreets[0]);
		buyHouseButtons = new JButton[streetSize];
		sellHouseButtons = new JButton[streetSize];
		for(int i = 0; i < streetSize; i++) {
			JButton buy = new JButton();
			buy.setText("Buy House on " + townManager.getStreetNameByIndex(ownedStreets[0], i));
			JButton sell = new JButton();
			sell.setText("Sell House on " + townManager.getStreetNameByIndex(ownedStreets[0], i));
			buyHouseButtons[i] = buy;
			sellHouseButtons[i] = sell;
		}
		setLayout(new GridLayout(0, 2));
		add(streetList);
		add(new JPanel()); //Spacer to fill up the 2nd collum on the first row
		for(int i = 0; i < buyHouseButtons.length; i++) {
			add(buyHouseButtons[i]);
			add(sellHouseButtons[i]);
		}
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JComboBox) {
			updateHouseButtons((String)((JComboBox)e.getSource()).getSelectedItem());
		}
	}
}
