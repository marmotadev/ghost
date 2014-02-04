package org.perfectbits.ghost.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.perfectbits.ghost.model.GhostCard.Action;
import org.perfectbits.ghost.model.GhostCard.GhostPosition;
import org.perfectbits.ghost.model.TurnState.Step;
import static org.perfectbits.ghost.model.TurnState.Step.BUDDA_PLACEMENT;
import static org.perfectbits.ghost.model.TurnState.Step.GHOSTS_MOVING;
import static org.perfectbits.ghost.model.TurnState.Step.NEW_GHOSTS_ENTERS;
import static org.perfectbits.ghost.model.TurnState.Step.PLAYER_ACTION;
import static org.perfectbits.ghost.model.TurnState.Step.PLAYER_MOVES;
import org.perfectbits.ghost.model.VillageTile.VillagePower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    private static final Logger log = LoggerFactory.getLogger(Game.class);
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
    private Map<Color, Player> players = new HashMap<Color, Player>();
    private PileOfCards cards = new PileOfCards();
    private boolean started = false;
    private boolean lost = false;
    private GameEventListener eventListener;
    private int activeBoard;

    public void start() {

        started = true;
        log.debug("Starting Game");
        placeVillageTiles();
        initializePlayers();
        placePlayerBoards();
        prepareCardDeck(gameDifficulty, numberOfPlayers);
        assignPlayersItems(gameDifficulty, numberOfPlayers);
        advanceOneStep();

    }

    private void nextBoardMove(int i) {
        final String msg = "Player " + i + " starts turn";
        log.debug(msg);
        eventListener.playerStartsTurn(i);
        turn = new TurnState();

    }

    public void playerDecidesIfHeWantsToMove(Player player) {
        eventListener.playerCanMove(player);
    }

    public void playerDecidesIfHeWantsToAskForHelp(Player player) {
        int pos = player.getTilePosition();
        VillageTile village = villageTiles[pos];
        eventListener.playCanAskForHelp(player, village);
    }

    public void askHelp(Player player) {
        int pos = player.getTilePosition();
        VillageTile village = villageTiles[pos];
        VillagePower pow = village.getPower();
        eventListener.playerAsksForHelpFromVillager(player, village);
        advanceOneStep();
    }

    public void exorcice(Player p, Board board, int slot) {
        eventListener.playerExorcisesGhost(p, board, slot);
        advanceOneStep();
    }

    public boolean anyExorcismAvailable(Board board, Player player) {
        log.warn("anyExorcismAvailable: not implemented");
        return false;
    }

    public boolean exorcismChoiseAvailable(Board board, Player player) {
        log.warn("exorcismChoiseAvailable: not implemented");
        return false;
    }

    public void applyAction(Action entryAction) {
        log.warn("applyAction: not implemented");
    }

    public void hauntFacingTile(Board board, int i) {
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
        eventListener.villageBecomesHaunted(villageTiles[facingTileNo], facingTileNo);
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

        List<VillageTile> deck = new ArrayList<VillageTile>();
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
        Board b;

        b = Board.build(Color.GREEN, flipACoin());
        b.setPlayer(players.get(Color.GREEN));
        boardsShuffled.add(b);

        b = Board.build(Color.RED, flipACoin());
        b.setPlayer(players.get(Color.RED));
        boardsShuffled.add(b);

        b = Board.build(Color.BLUE, flipACoin());
        b.setPlayer(players.get(Color.BLUE));
        boardsShuffled.add(b);

        b = Board.build(Color.YELLOW, flipACoin());
        b.setPlayer(players.get(Color.YELLOW));
        boardsShuffled.add(b);

        Collections.shuffle(boardsShuffled);
        for (int i = 0; i < 4; i++) {
            boards[i] = boardsShuffled.get(i);
            boards[i].setAssignedPos(i);
        }
        // randomly order bords
    }

    private boolean flipACoin() {
        return (rnd.nextInt(1) == 0) ? false : true;
    }

    public boolean isStarted() {
        return started;
    }

    private void looseGame() {
        lost = true;
        eventListener.lostGame();
    }

    private boolean reincarnationIsNotDefeated() {
        log.warn("reincarnationIsNotDefeated: Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return true;
    }

    private void initializePlayers() {
        players.put(Color.GREEN, new Player());
        players.put(Color.BLUE, new Player());
        players.put(Color.YELLOW, new Player());
        players.put(Color.RED, new Player());
    }

    private void placeGhost(GhostCard next) {
        log.warn(" placeGhost Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setEventListener(GameEventListener gameEventListener) {
        this.eventListener = gameEventListener;
    }

    private void advanceOneStep() {

        if (turn.isInitial()) {
            log.debug("Initial move, choosing board 0");
            this.activeBoard = 0;
            turn.setInitial(false);
        } else {
            log.debug("Step advances from " + turn + " -> " + turn.nextStep());
            TurnState next = turn.nextStep();
            eventListener.stepChanges(turn, next);
            turn = next;
//            } while (turn.hasMoreSteps() && !lost);
        }

        if (turn.hasMoreSteps() == false) {
            log.debug("No more steps for board " + activeBoard);
            this.activeBoard = ++this.activeBoard % 3;
            eventListener.turnGoesToNewBoard(this.activeBoard);
            checkGameOver();
            nextBoardMove(activeBoard);
        } else {
            log.debug("Board " + activeBoard + " advances further");
            Board board = boards[activeBoard];
//            do {
            Step step = turn.getCurrentStep();
            log.debug("Current step: " + step);
            Player player;
            switch (step) {
                case BUDDA_PLACEMENT:
                    if (board.isNeutral()) {
                        break;
                    }
                    Color color = board.getPlayerType();
                    player = board.getPlayer();

                    eventListener.userCanPlaceBuddah(board, player);
                    // ask user to place budda or skip
                    break;
                case GHOSTS_MOVING:
                    eventListener.ghostsStartMoving(board);
                    for (GhostSlot slot : board.getSlots()) {
                        if (slot.hasGhost() && slot.getGhost().isHaunting()) {
                            GhostPosition pos = slot.getGhost().getPosition();
                            if (pos == GhostPosition.ON_BOARD2) {
                                hauntFacingTile(board, slot.getAssignedNumber());
                            }
                            eventListener.ghostMoves(pos, pos.next());
                            slot.getGhost().setPosition(pos.next());
                        }
                    }
                    advanceOneStep();

                    break;
                case NEW_GHOSTS_ENTERS:
                    if (board.isNeutral()) {
                        log.info("Board is neutral, skiping");
                        advanceOneStep();
                        break;
                    }

                    if (cards.hasMoreCards()) {
                        log.debug("Have more cards...");
                        GhostCard next = cards.drawNextCard();
                        eventListener.ghostEnters(next);
                        Action entryAction = next.getEntryAction();
                        applyAction(entryAction);
                        log.debug("Action applied, wait for placement to happen!");
                    } else if (reincarnationIsNotDefeated()) {
                        log.debug("No more cards to draw, reincarnation not defeated");
                        looseGame();
                    }

                    // todo
                    break;
                case PLAYER_ACTION:
                    if (board.isNeutral()) {
                        break;
                    }
                    //todo ask user ask help or exorcise;

                    if (anyExorcismAvailable(board, board.getPlayer())) {
                        // todo : ask to choose
                        if (exorcismChoiseAvailable(board, board.getPlayer())) {
                            eventListener.exorcismChoiseAvailable(board, board.getPlayer());
                        } else {
                            // auto exorcise;
                        }
                    } else {
                        // ask if player wants to aske help;
                        playerDecidesIfHeWantsToAskForHelp(board.getPlayer());
                    }
                    break;
                case PLAYER_MOVES:
                    if (board.isNeutral()) {
                        break;
                    }

                    eventListener.playerCanChooseToMove(board);
                    // todo
                    if (board.getPlayer().hasPower(Player.Power.FLY_ANYWHERE)) {
                    } else {
                        // let choose neighbour non diagonal tiles
                    }
                    break;
                default:
                    break;

            }
//                turn = turn.nextStep();
//            } while (turn.hasMoreSteps() && !lost);

        }

    }

    private void checkGameOver() {
        if (lost) {
            looseGame();
        }
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Board[] getBoards() {
        return boards;
    }

    public Random getRnd() {
        return rnd;
    }

    public TurnState getTurn() {
        return turn;
    }

    public VillageTile[] getVillageTiles() {
        return villageTiles;
    }

    public Map<Color, Player> getPlayers() {
        return players;
    }

    public PileOfCards getCards() {
        return cards;
    }

    public boolean isLost() {
        return lost;
    }

    public int getActiveBoard() {
        return activeBoard;
    }
}
