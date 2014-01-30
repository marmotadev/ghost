package org.perfectbits.ghost.model;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.perfectbits.ghost.model.Player.Power;

public class Board {
	private Set<Player.Power> powers = EnumSet.of(Player.Power.FLY_ANYWHERE);
	private Color playerType;
	private boolean upside;
	private boolean neutral;
	private boolean posessed;
	
	private GhostSlot[] slots = new GhostSlot[3];
	private int assignedPosition;
	private Player player;

	public Board(Set<Power> powers, Color pt, boolean upside) {
		super();
		this.powers = powers;
		this.playerType = pt;
		this.upside = upside;
		slots[0] = new GhostSlot(1);
		slots[1] = new GhostSlot(2);
		slots[2] = new GhostSlot(3);
	}

	public static Board build(Color pt, boolean upside) {
		
		Set<Player.Power> powers = EnumSet.of(Player.Power.FLY_ANYWHERE);
		switch (pt) {
			case BLUE:
				if (upside)
					powers.add(Player.Power.FLY_ANYWHERE);
				else 
					powers.add(Player.Power.MOVE_OTHER_PLAYERS);
			break;
			case GREEN:
				break;
			case YELLOW:
				break;
			case RED:
				break;
		}
		return new Board(powers, pt,upside);
		
	}

	private void addPower(Power power) {
		powers.add(power);
	}

	public Set<Player.Power> getPowers() {
		return Collections.unmodifiableSet(powers);
	}

	public boolean isUpside() {
		return upside;
	}

	public Color getPlayerType() {
		return playerType;
	}

	public GhostSlot ghostSlot(int i) {
		if (i > 3 || i < 1)
			throw new IllegalArgumentException("slot = " + i);
		return slots[i];
	}

	public boolean isNeutral() {
		return neutral;
	}

	public boolean isPosessed() {
		return posessed;
	}

	public GhostSlot[] getSlots() {
		return slots;
	}

	public void setAssignedPos(int i) {
		this.assignedPosition = i;
	}

	public int getAssignedPosition() {
		return assignedPosition;
	}

	public Player getPlayer() {
		return player;
	}

	
}
