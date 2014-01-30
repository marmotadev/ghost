package org.perfectbits.ghost.model;

public class GhostCard {
	public enum GhostPosition  {
		ON_CARD, ON_BOARD1, ON_BOARD2;
	}
	private Strength strengthPoints;
	private boolean haunting;
	private GhostPosition position = GhostPosition.ON_CARD;
	public boolean isHaunting() {
		return haunting;
	}
	
	
}
