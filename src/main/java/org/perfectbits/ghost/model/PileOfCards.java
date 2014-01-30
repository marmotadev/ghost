package org.perfectbits.ghost.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PileOfCards {
	private static List<GhostCard> fullDeckGhosts = new ArrayList<GhostCard>();
	private static List<ReincarnationCard> fullDeckOfReincarnations = new ArrayList<ReincarnationCard>();
	static {
		/*
		 * initialize cards
		 */
		fullDeckGhosts.add(new GhostCard("Rotten soul", Strength.singleColored(Color.YELLOW, 3) ));
		fullDeckGhosts.add(new GhostCard("Ugly ass", Strength.singleColored(Color.RED, 2) ));
		
		fullDeckOfReincarnations.add(new ReincarnationCard("Total evil", Strength.singleColored(Color.BLACK, 5)));
		fullDeckOfReincarnations.add(new ReincarnationCard("Dead ass", Strength.singleColored(Color.BLACK, 4)));
	}
	public Stack<GhostCard> deck = new Stack<GhostCard>();
        public PileOfCards() {
            Collections.shuffle(fullDeckGhosts);
            for (GhostCard gc: fullDeckGhosts) {
                deck.push(gc);
            }
        }
        public boolean hasMoreCards() {
            return !deck.isEmpty();
        }
	public GhostCard drawNextCard() {
            if (!deck.isEmpty())
		return deck.pop();
            return null;
	};
}
