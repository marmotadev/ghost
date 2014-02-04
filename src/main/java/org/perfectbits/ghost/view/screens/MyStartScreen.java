/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost.view.screens;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.perfectbits.ghost.GhostsApplication;
import org.perfectbits.ghost.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author congo
 */
public class MyStartScreen extends AbstractAppState implements ScreenController {

    Logger log = LoggerFactory.getLogger(MyStartScreen.class);
    private Nifty nifty;
    private Screen screen;
    private SimpleApplication app;
    private final Game game;

    public MyStartScreen(Game game) {
        this.game = game;
    }

    /**
     * Nifty GUI ScreenControl methods
     */
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    /**
     * jME3 AppState methods
     */
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (GhostsApplication) app;
    }

    @Override
    public void update(float tpf) {
        /**
         * jME update loop!
         */
    }

    /**
     * custom methods
     */
    public void startGame() {
        try {
            log.debug("start clicked");
            if (game.isStarted() == false) {
                game.start();
            } else {
                log.debug("Game already started!");
            }
        } catch (Exception e) {
            log.error("error on game start: ", e);
        }
    }

    public void quitGame() {
        log.debug("quit");
//        stop();
        app.stop();
    }
}
