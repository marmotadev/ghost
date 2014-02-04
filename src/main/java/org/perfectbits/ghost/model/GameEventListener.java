/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost.model;

/**
 *
 * @author congo
 */
public interface GameEventListener {

    public void ghostEnters(GhostCard next);

    public void lostGame();

    public void playerStartsTurn(int i);

    public void villageBecomesHaunted(VillageTile villageTile, int facingTileNo);

    public void ghostMoves(GhostCard.GhostPosition pos, GhostCard.GhostPosition next);

    public void exorcismChoiseAvailable(Board board, Player player);

    public void playCanAskForHelp(Player player, VillageTile village);

    public void playerCanChooseToMove(Board board);

    public void playerAsksForHelpFromVillager(Player player, VillageTile village);

    public void turnGoesToNewBoard(int activeBoard);

    public void userCanPlaceBuddah(Board board, Player player);

    public void ghostsStartMoving(Board board);

    public void playerCanMove(Player player);

    public void playerExorcisesGhost(Player p, Board board, int slot);

    public void stepChanges(TurnState turn, TurnState next);
    
}
