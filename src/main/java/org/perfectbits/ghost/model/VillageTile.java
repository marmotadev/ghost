package org.perfectbits.ghost.model;

public class VillageTile {
	public enum VillagePower {
		RESURRECT_DEAD, MOVE_GHOST;
	}
	private String name;
	private VillagePower power;
	private boolean haunted;
	
	
	public VillageTile(String name, VillagePower power, boolean haunted) {
		super();
		this.name = name;
		this.power = power;
		this.haunted = haunted;
	}
	public boolean isHaunted() {
		return haunted;
	}
	public void setHaunted(boolean haunted) {
		this.haunted = haunted;
	}
	public String getName() {
		return name;
	}
	public VillagePower getPower() {
		return power;
	}
	
	
	
}
