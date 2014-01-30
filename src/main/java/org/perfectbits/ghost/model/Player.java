package org.perfectbits.ghost.model;

import java.util.HashSet;
import java.util.Set;

public class Player {
	
	public enum Power {
		FLY_ANYWHERE, MOVE_OTHER_PLAYERS;
	}
	private Set<Power> powers = new HashSet<Power>(); 
	private Items items = new Items();
	private boolean alive = true;
	private int tilePosition = 5; // defaults to center

	public Set<Power> getPowers() {
		return powers;
	}

	public Items getItems() {
		return items;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean hasPower(Power flyAnywhere) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getTilePosition() {
		return tilePosition;
	}
}
