package org.perfectbits.ghost.model;

public class GhostSlot {
	private boolean empty;
	private GhostCard ghost;
	private boolean protectedByBudda;
	private int assignedNumber;
	
	public GhostSlot(int i) {
		this.assignedNumber = i;
	}
	public boolean hasGhost() {
		return getGhost() != null;
	}
	public boolean isEmpty() {
		return empty;
	}
	public GhostCard getGhost() {
		return ghost;
	}
	public boolean isProtectedByBudda() {
		return protectedByBudda;
	}
	public int getAssignedNumber() {
		return assignedNumber;
	}
	
	
}
