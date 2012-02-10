package monopoly;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class CardDeck
{
	private List<Card> playDeck;
	private List<Card> deckSource;
	private Random rand;
	
	public CardDeck(long seed)
	{
		playDeck = new ArrayList<Card>();
		deckSource = new ArrayList<Card>();
		rand = new Random(seed);
	}
	
	public void setSeed(long seed) {
		rand = new Random(seed);
	}
	
	public void addCard(Card c)
	{
		addCard(c, true);
	}
	
	private void addCard(Card c, boolean newCard)
	{
		playDeck.add(c);
		if(newCard)
			deckSource.add(c);
	}
	
	public Card drawCard()
	{
		if(playDeck.size() == 0)
			shuffle();
		return playDeck.remove(0);
	}
	
	public void shuffle()
	{
		List<Card> deckBack = new ArrayList<Card>();
		deckBack = deckSource;
		Card[] shuffledDeck = new Card[deckSource.size()];
		while(deckSource.size() > 0)
		{
			Card c = deckSource.remove(0);
			while(true)
			{
				int i = rand.nextInt(shuffledDeck.length);
				if(shuffledDeck[i] == null)
				{
					shuffledDeck[i] = c;
					break;
				}
			}
		}
		deckSource = deckBack;
		List<Card> newDeck = new ArrayList<Card>();
		for(Card c : shuffledDeck) {
			newDeck.add(c);
		}
		playDeck = newDeck;
	}
}
