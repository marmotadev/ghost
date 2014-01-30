package org.perfectbits.ghost.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {
	private GameDifficulty gameDifficulty = GameDifficulty.NORMAL;
	private int numberOfPlayers = 4;
	
	private Board[] boards = new Board[4];
	private Random rnd = new Random(System.currentTimeMillis());
	public void start() {
		// placeVillageTiles()
		 placePlayerBoards();
		// prepareCardDeck();
//		 assignPlayersItems(gameDifficulty, numberOfPlayers);
	}

	private void assignPlayersItems(GameDifficulty gameDifficulty2,
			int numberOfPlayers2) {
		// TODO Auto-generated method stub
		
	}

	private void placePlayerBoards() {
		List<Board> boardsShuffled = new ArrayList<Board>();
		boardsShuffled.add(Board.build(PlayerType.GREEN, flipACoin()));
		Collections.shuffle(boardsShuffled);
		for(int i=0; i < 4; i++) {
			boards[i] = boardsShuffled.get(i);
		}
		// randomly order bords
	}

	private boolean flipACoin() {
		return (rnd.nextInt(1) == 0)?false:true;
	}
	
	

}
