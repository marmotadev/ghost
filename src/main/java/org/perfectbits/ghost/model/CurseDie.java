package org.perfectbits.ghost.model;

import java.util.Random;

public enum CurseDie {
	LOOSE_ALL_ITEMS, NEW_GHOST;
	
	public static CurseDie roll() {
		Random r = new Random(System.currentTimeMillis());
		int i = r.nextInt(CurseDie.values().length);
		return CurseDie.values()[i];
	}
}
