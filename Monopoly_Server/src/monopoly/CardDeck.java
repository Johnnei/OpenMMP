package monopoly;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class CardDeck
{
	private List<Card> deck;
	private List<Card> deckCopy;
	
	public CardDeck()
	{
		deck = new ArrayList<Card>();
		deckCopy = new ArrayList<Card>();
	}
	
	public void addCard(Card c)
	{
		addCard(c, true);
	}
	
	private void addCard(Card c, boolean newCard)
	{
		deck.add(c);
		if(newCard)
			deckCopy.add(c);
	}
	
	public Card drawCard()
	{
		if(deck.size() == 0)
		{
			for(int i = 0; i < deckCopy.size(); i++)
			{
				addCard(deckCopy.get(i), false);
			}
			shuffle();
		}
		return deck.remove(0);
	}
	
	public void shuffle()
	{
		Card[] shuffledDeck = new Card[deck.size()];
		Random r = new Random();
		while(deck.size() > 0)
		{
			Card c = deck.remove(0);
			while(true)
			{
				int i = r.nextInt(shuffledDeck.length);
				if(shuffledDeck[i] == null)
				{
					shuffledDeck[i] = c;
					break;
				}
			}
		}
	}
}
