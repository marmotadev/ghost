package org.perfectbits.ghost.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.perfectbits.ghost.model.GhostCard.Action;
import org.perfectbits.ghost.model.GhostCard.GhostPosition;
import org.perfectbits.ghost.model.TurnState.Step;
import org.perfectbits.ghost.model.VillageTile.VillagePower;

public class Game {
	private static final int NORTH = 1;
	private static final int EAST = 2;
	private static final int SOUTH = 3;
	private static final int WEST = 4;
	
	private GameDifficulty gameDifficulty = GameDifficulty.NORMAL;
	private int numberOfPlayers = 4;
	
	private Board[] boards = new Board[4];
	private Random rnd = new Random(System.currentTimeMillis());
	private TurnState turn = new TurnState();
	private VillageTile[] villageTiles;
	private List<Player> players;
	private PileOfCards cards = new PileOfCards();
	
	
	public void start() {
		 placeVillageTiles();
		 placePlayerBoards();
		 prepareCardDeck(gameDifficulty, numberOfPlayers);
		 assignPlayersItems(gameDifficulty, numberOfPlayers);
		 while (true) {
			 for (int i=0;i < 4; i++) {
				 nextBoardMove(i);
			 }
		 }
	}

	private void nextBoardMove(int i) {
		System.out.println("Player " + i + " starts turn");
		Board board = boards[i];
		turn = new TurnState();
		do {
			Step step = turn.getCurrentStep();
			System.out.println("Current step: " + step);
			Object player;
			switch (step) {
			case BUDDA_PLACEMENT:
				if (board.isNeutral())
					break;
				Color color = board.getPlayerType();
				player = board.getPlayer();
				
				// ask user to place budda or skip
				break;
			case GHOSTS_MOVING:
				for (GhostSlot slot: board.getSlots()) {
					if (slot.hasGhost() || slot.getGhost().isHaunting()) {
						GhostPosition pos = slot.getGhost().getPosition();
						if (pos == GhostPosition.ON_BOARD2) {
							hauntFacingTile(board, slot.getAssignedNumber());
						}
						slot.getGhost().setPosition(pos.next());
					}
				}
				
				
				break;
			case NEW_GHOSTS_ENTERS:
				if (board.isNeutral())
					break;
				
				GhostCard next = cards.drawNextCard();
				Action entryAction = next.getEntryAction();
				applyAction(entryAction);
				// todo
				break;
			case PLAYER_ACTION:
				if (board.isNeutral())
					break;
				//todo ask user ask help or exorcise;
				
				if (anyExorcismAvailable(board, board.getPlayer())) {
					// todo : ask to choose
					if (exorcismChoiseAvailable(board, board.getPlayer())) {
						// todo ask user what to exorcise
					}
					else {
						// auto exorcise;
					}
				}
				else {
					// ask if player wants to aske help;
					askHelp(board.getPlayer());
				}
				break;
			case PLAYER_MOVES:
				if (board.isNeutral())
					break;
				
				// todo
				if (board.getPlayer().hasPower(Player.Power.FLY_ANYWHERE)) {
					// let choose anything
				}
				else {
					// let choose neighbour non diagonal tiles
				}
				break;
			default:
				break;
				
			}
		} while (turn.hasMoreSteps());
	}

	private void askHelp(Player player) {
		int pos = player.getTilePosition();
		VillageTile village = villageTiles[pos];
		VillagePower pow = village.getPower();
		
	}

	private boolean anyExorcismAvailable(Board board, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean exorcismChoiseAvailable(Board board, Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	private void applyAction(Action entryAction) {
		// TODO Auto-generated method stub
		
	}

	private void hauntFacingTile(Board board, int i) {
		int facingTileNo = 0;
		switch (board.getAssignedPosition()) {
			case NORTH:
				facingTileNo = i;
			break;
			case EAST:
				facingTileNo = i * 3;
				break;
			case SOUTH:
				facingTileNo = 6 + i;
			case WEST:
				facingTileNo = 1 + (i - 1) * 3;
			break;
		}
		villageTiles[facingTileNo].setHaunted(true);
	}

	public Affect getGlobalEffects() {
		return null;
	}
	private void prepareCardDeck(GameDifficulty gameDifficulty2, int numberOfPlayers2) {
		// TODO Auto-generated method stub
		
	}

	// shuffles and orders village tiles
	private void placeVillageTiles() {
		List<VillageTile> deck = new ArrayList<>();
		deck.add(new VillageTile("1", null, false));
		deck.add(new VillageTile("2", null, false));
		deck.add(new VillageTile("3", null, false));
		deck.add(new VillageTile("4", null, false));
		deck.add(new VillageTile("5", null, false));
		deck.add(new VillageTile("6", null, false));
		deck.add(new VillageTile("7", null, false));
		deck.add(new VillageTile("8", null, false));
		deck.add(new VillageTile("9", null, false));
		
		Collections.shuffle(deck);
		villageTiles = deck.toArray(new VillageTile[]{});
	}
	/*
	 * Assign players a set of items.
	 */
	private void assignPlayersItems(GameDifficulty gameDifficulty2,
			int numberOfPlayers2) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Shuffles sides and order of player boards
	 */
	private void placePlayerBoards() {
		List<Board> boardsShuffled = new ArrayList<Board>();
		boardsShuffled.add(Board.build(Color.GREEN, flipACoin()));
		Collections.shuffle(boardsShuffled);
		for(int i=0; i < 4; i++) {
			boards[i] = boardsShuffled.get(i);
			boards[i].setAssignedPos(i);
		}
		// randomly order bords
	}

	private boolean flipACoin() {
		return (rnd.nextInt(1) == 0)?false:true;
	}
	
	

}
