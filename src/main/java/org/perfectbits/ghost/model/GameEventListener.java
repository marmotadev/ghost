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
    
}
