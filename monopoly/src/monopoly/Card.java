package monopoly;

/**
 * Class Holding the same cards as in the Server.
 * Although for the client we only need it to handle the text-processing.
 * @author Johnnei
 *
 */
public class Card
{
	String text; //Text on card
	
	public Card(String txt, int moneyChange)
	{
		this.text = txt;
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
}
