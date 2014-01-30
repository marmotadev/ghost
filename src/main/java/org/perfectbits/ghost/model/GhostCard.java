package org.perfectbits.ghost.model;

public class GhostCard {
	public enum Action {
		
	}
	public enum Reward {
		
	}
	public enum GhostPosition  {
		ON_CARD, ON_BOARD1, ON_BOARD2;
		public GhostPosition next() {
			switch (this) {
			case ON_CARD:
				return ON_BOARD1;
			case ON_BOARD1:
				return ON_BOARD2;
			case ON_BOARD2:
				return ON_CARD;
			}
			return null;
		}
	}
	private Action entryAction;
	private Reward reward;
	private String name;
	private Strength strengthPoints;
	private boolean haunting;
	private GhostPosition position = GhostPosition.ON_CARD;
	
	
	
	public GhostCard(String name, Strength strengthPoints, boolean haunting,
			GhostPosition position) {
		super();
		this.name = name;
		this.strengthPoints = strengthPoints;
		this.haunting = haunting;
		this.position = position;
	}
	public GhostCard(String name, Strength strengthPoints) {
		this.name = name;
		this.strengthPoints = strengthPoints;
	}
	public boolean isHaunting() {
		return haunting;
	}
	public GhostPosition getPosition() {
		return position;
	}
	public void setPosition(GhostPosition position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public Strength getStrengthPoints() {
		return strengthPoints;
	}
	public Action getEntryAction() {
		return entryAction;
	}
	public Reward getReward() {
		return reward;
	}
	
	
	
}
