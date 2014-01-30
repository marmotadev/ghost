package org.perfectbits.ghost.model;


import java.util.HashMap;
import java.util.Map;

public class Strength {
	private Map<Color, Integer> strength = new HashMap<Color, Integer>();
	
	public Strength(Map<Color, Integer> strength) {
		super();
		this.strength = strength;
	}
	public static Strength singleColored(Color pt, int count) {
		Map<Color, Integer> strength = new HashMap<Color, Integer>();
		strength.put(pt, count);
		return new Strength(strength);
	}
	public boolean isMultiColored() {
		return false;
	}
	public Map<Color, Integer> getPoints() {
		return strength;
	}
}
