package monopoly;

/**
 * Class Holding the same cards as in the Server.
 * Although for the client we only need it to handle the text-processing.
 * @author Johnnei
 *
 */
public class Card
{
	private String text; //Text on card
	private int jackpotMoney;
	
	public Card(String txt, int moneyChange)
	{
		this.text = txt;
		jackpotMoney = 0;
		if(moneyChange != 0) {
			if(moneyChange < 0) {
				jackpotMoney += -moneyChange;
			}
		}
	}
	
	public Card(String text, int index, boolean move, boolean normal)
	{
		this(text, 0);
	}
	
	public Card(String text, int index, boolean move)
	{
		this(text, index, move, false);
	}
	
	public String[] getStateStrings()
	{
		if(text.contains("\n"))
			return text.split("\n");
		return new String[] { text };
	}
	
	public int getJackpotMoney() {
		return jackpotMoney;
	}
}
