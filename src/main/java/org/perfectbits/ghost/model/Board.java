package org.perfectbits.ghost.model;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.perfectbits.ghost.model.Player.Power;

public class Board {
	private Set<Player.Power> powers = EnumSet.of(Player.Power.FLY_ANYWHERE);
	private PlayerType playerType;
	private boolean upside;
	

	public Board(Set<Power> powers, PlayerType pt, boolean upside) {
		super();
		this.powers = powers;
		this.playerType = pt;
		this.upside = upside;
	}

	public static Board build(PlayerType pt, boolean upside) {
		
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

	public PlayerType getPlayerType() {
		return playerType;
	}
}
