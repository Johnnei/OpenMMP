package monopoly;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class CardDeck
{
	/**
	 * Deck Used in the game
	 */
	private List<Card> deck;
	/**
	 * Original Deck
	 */
	private List<Card> deckCopy;
	/**
	 * Random Engine to shuffle the cards
	 */
	private Random rand;
	
	/**
	 * Define CardDeck with 2 lists;
	 */
	public CardDeck(long seed)
	{
		deck = new ArrayList<Card>();
		deckCopy = new ArrayList<Card>();
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
		deck.add(c);
		if(newCard)
			deckCopy.add(c);
	}
	
	/**
	 * Pop the next card from the deck, If the deck is empty: Create a new deck
	 * @return The card at index 0 of the CardDeck
	 */
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
	
	/**
	 * Shuffle the deck, using the global seed.
	 */
	public void shuffle()
	{
		Card[] shuffledDeck = new Card[deck.size()];
		while(deck.size() > 0)
		{
			Card c = deck.remove(0);
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
	}
}
