package org.perfectbits.ghost.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PileOfCards {
	public Stack<GhostCard> deck = new Stack<GhostCard>();
	public GhostCard drawNextCard() {
		return deck.pop();
	};
}
