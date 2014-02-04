/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost;

import org.perfectbits.ghost.model.Game;
import org.perfectbits.ghost.model.GameEventListener;

/**
 *
 * @author congo
 */
public abstract class GameEventListenerImpl implements GameEventListener {
    private final Game game;
    public GameEventListenerImpl(Game g) {
        this.game = g;
    }
}
