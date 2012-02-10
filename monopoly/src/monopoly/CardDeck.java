package monopoly;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class CardDeck
{
	/**
	 * Deck Used in the game
	 */
	private List<Card> playDeck;
	/**
	 * Original Deck
	 */
	private List<Card> deckSource;
	/**
	 * Random Engine to shuffle the cards
	 */
	private Random rand;
	
	/**
	 * Define CardDeck with 2 lists;
	 */
	public CardDeck(long seed)
	{
		playDeck = new ArrayList<Card>();
		deckSource = new ArrayList<Card>();
		rand = new Random(seed);
	}
	
	/**
	 * Add an Card to the Deck
	 * @param c The Card to add to the Deck
	 */
	public void addCard(Card c)
	{
		addCard(c, true);
	}
	
	/**
	 * Add an Card to the Deck
	 * @param c The Card to add to the Deck
	 * @param newCard If the card should be added in both decks (original and in-use)
	 */
	private void addCard(Card c, boolean newCard)
	{
		deckSource.add(c);
		if(newCard)
			playDeck.add(c);
	}
	
	/**
	 * Pop the next card from the deck, If the deck is empty: Create a new deck
	 * @return The card at index 0 of the CardDeck
	 */
	public Card drawCard()
	{
		if(playDeck.size() == 0)
			shuffle();
		return playDeck.remove(0);
	}
	
	/**
	 * Shuffle the deck, using the global seed.
	 */
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
