package org.perfectbits.ghost;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.font.BitmapFont;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import de.lessvoid.nifty.Nifty;
import java.util.ArrayList;
import java.util.List;
import org.perfectbits.ghost.model.Board;
import org.perfectbits.ghost.model.Game;
import org.perfectbits.ghost.model.GhostCard;
import org.perfectbits.ghost.model.Player;
import org.perfectbits.ghost.model.TurnState;
import org.perfectbits.ghost.model.VillageTile;
import org.perfectbits.ghost.view.screens.MyStartScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author congo
 */
public class GameAppState extends AbstractAppState {
    
    private static final Logger log = LoggerFactory.getLogger(GameAppState.class);
    
    public static Vector3f startPos = new Vector3f(2, 2, -20);
    public static float VILLAGE_CARD_SIZE = 1.5f;
    public static final String MAPPING_BOARD_1 = "Board 1";
    public static final String MAPPING_BOARD_2 = "Board 2";
    public static final String MAPPING_BOARD_3 = "Board 3";
    public static final String MAPPING_BOARD_4 = "Board 4";
    public static final String MAPPING_MAIN_MENU = "Main menu";
    public static final String MAPPING_START_GAME = "Start game";
    public static final String MAPPING_QUIT_GAME = "Quit game";
    
    private final static Trigger TRIGGER_BOARD1 = new KeyTrigger(KeyInput.KEY_NUMPAD1);
    private final static Trigger TRIGGER_BOARD2 = new KeyTrigger(KeyInput.KEY_NUMPAD2);
    private final static Trigger TRIGGER_BOARD3 = new KeyTrigger(KeyInput.KEY_NUMPAD3);
    private final static Trigger TRIGGER_BOARD4 = new KeyTrigger(KeyInput.KEY_NUMPAD4);
    private final static Trigger TRIGGER_MAIN_MENU = new KeyTrigger(KeyInput.KEY_M);
    private final static Trigger TRIGGER_START_GAME = new KeyTrigger(KeyInput.KEY_S);
    private final static Trigger TRIGGER_QUIT_GAME = new KeyTrigger(KeyInput.KEY_Q);

    private boolean moveCameraAround = false;
    private GhostsApplication app;
    private Node rootNode;
    private CameraMover cameraMover;
    private GUIScreenWriter textWriter;
    private Game game;
    GhostImage ghostImage;
    private Camera cam;
    private Node guiNode;
    private BitmapFont guiFont;
    private AssetManager assetManager;
    private InputManager inputManager;
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private AppSettings settings;
    private AppStateManager stateManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); //To change body of generated methods, choose Tools | Templates.
        this.app = (GhostsApplication) app;
        this.rootNode = this.app.getRootNode();
        this.cam = this.app.getCamera();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        this.audioRenderer = this.app.getAudioRenderer();
        this.guiViewPort = this.app.getGuiViewPort();
        this.settings = this.app.getSettings();
        this.guiNode = this.app.getGuiNode();
        this.guiFont = this.app.getGuiFont();
        this.textWriter = new GUIScreenWriter(guiNode, guiFont, settings);
        this.stateManager = this.app.getStateManager();

        this.cameraMover = new CameraMover(cam, getGameboardCenter(), startPos);
        this.ghostImage = new GhostImage(assetManager, settings, rootNode, "Interface/butka.jpg", startPos);

        cam.setLocation(cam.getLocation().add(-3, -7, -23));
        cam.lookAt(getGameboardCenter(), Vector3f.UNIT_XYZ);
        placeGameArtifacts(rootNode);

        registerKeyboardMappings();
        
        //game
        this.game = new Game();
        this.game.setEventListener(new GameEventListenerImpl(game) {
            public void ghostEnters(GhostCard next) {
                ghostImage.display();
                textWriter.writeToBuf("Be afraid! New ghost arrives: " + next.getName());
            }

            public void lostGame() {
                textWriter.writeToBuf("Game Over");
            }

            public void playerStartsTurn(int i) {
                textWriter.writeToBuf("Player " + i + " starts turn" );
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void villageBecomesHaunted(VillageTile villageTile, int facingTileNo) {
                textWriter.writeToBuf("Village " + villageTile + " becomes haunted" );
            }

            public void ghostMoves(GhostCard.GhostPosition pos, GhostCard.GhostPosition next) {
                textWriter.writeToBuf("Ghost moves from " + pos + " to " + next);
            }

            public void exorcismChoiseAvailable(Board board, Player player) {
                textWriter.writeToBuf("Choose which ghost to exorcise");
            }

            public void playCanAskForHelp(Player player, VillageTile village) {
                textWriter.writeToBuf("Player can ask for help");
            }

            public void playerCanChooseToMove(Board board) {
                playerCanMove(board.getPlayer());
                   
            }

            public void playerAsksForHelpFromVillager(Player player, VillageTile village) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void turnGoesToNewBoard(int activeBoard) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void userCanPlaceBuddah(Board board, Player player) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void ghostsStartMoving(Board board) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                textWriter.writeToBuf("Ghosts start moving");
            }

            public void playerCanMove(Player player) {
                textWriter.writeToBuf("You can move now. Hurry or hide!");
            }

            public void playerExorcisesGhost(Player p, Board board, int slot) {
                textWriter.writeToBuf("That was nasty religious kill! " + " killed a " + board.getSlots()[slot]);
            }

            public void stepChanges(TurnState turn, TurnState next) {
                log.debug("turn " + turn + " -> " + next);
            }
        });

        hud();
    }

    public Vector3f getGameboardCenter() {
        return startPos.add(VILLAGE_CARD_SIZE * 3f / 2f, -VILLAGE_CARD_SIZE * 3f / 2f, 0);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf); //To change body of generated methods, choose Tools | Templates.
        if (moveCameraAround)
            moveCameraAroundBoard(tpf);
    }

    @Override
    public void cleanup() {
        super.cleanup(); //To change body of generated methods, choose Tools | Templates.
    }

    private Geometry buildCard() {
        Box card = new Box(1, 1.5f, 0.02f);
        Geometry cardGeom = new Geometry("Card", card);
        Material cardMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cardMat.setColor("Color", ColorRGBA.White);
        Texture cube1Tex = assetManager.loadTexture(
                "Interface/butka.jpg");
        cube1Tex.setWrap(Texture.WrapMode.MirroredRepeat);
        cardMat.setTexture("ColorMap", cube1Tex);
        cardGeom.setMaterial(cardMat);
        Vector3f lt = cardGeom.getLocalTranslation();
        cardGeom.setLocalTranslation(new Vector3f(2, 2, 2));
        return cardGeom;
    }

    private Geometry buildVillageCard(int row, int col) {
        float width, height, depth;
        width = 1.5f;
        height = 1.5f;
        depth = 0.02f;
        Box card = new Box(width * 0.5f, height * 0.5f, depth * 0.5f);
        Geometry cardGeom = new Geometry("Card", card);
        Material cardMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cardMat.setColor("Color", ColorRGBA.White);
        Texture cube1Tex = assetManager.loadTexture(
                "Interface/grauz.jpg");
        cube1Tex.setWrap(Texture.WrapMode.EdgeClamp);
        cardMat.setTexture("ColorMap", cube1Tex);
        cardGeom.setMaterial(cardMat);
        cardGeom.setLocalTranslation(startPos);
        Vector3f lt = cardGeom.getLocalTranslation();
        float spacing = 0;
        float x = col * width + spacing;
        float y = -(row * height + spacing);
        float z = 0;

        log.trace("Village at [{}] [{}] [{}]", new Object[]{x, y, z});

        cardGeom.setLocalTranslation(lt.add(new Vector3f(x, y, z)));
        cardGeom.addControl(new OnMouseGlowControl(cam, inputManager, assetManager, rootNode, ColorRGBA.Red));
        return cardGeom;
    }

    private void placeGameArtifacts(Node rootNode) {
        List<Geometry> villageCards = new ArrayList<Geometry>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rootNode.attachChild(buildVillageCard(i, j));
            }
        }
        placePlayerCards(rootNode);
        placeGhost(rootNode);
        placePlayer(rootNode);

    }

    private void placePlayerCards(Node rootNode) {
        for (int i = 1; i <= 4; i++) {
            float width, height, depth;
            height = VILLAGE_CARD_SIZE;
            width = VILLAGE_CARD_SIZE * 3;

            depth = 0.02f;
            Box card = new Box(width * 0.5f, height * 0.5f, depth * 0.5f);
            Geometry cardGeom = new Geometry("PlayerCard", card);
            Material cardMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//            cardMat.setColor("Color", ColorRGBA.Red);
            Texture cube1Tex = assetManager.loadTexture(
                    "Textures/pl" + i + ".jpg");
            cube1Tex.setWrap(Texture.WrapMode.EdgeClamp);
            cardMat.setTexture("ColorMap", cube1Tex);
            cardGeom.setMaterial(cardMat);
            cardGeom.setLocalTranslation(startPos);
            Vector3f lt = cardGeom.getLocalTranslation();
            float spacing = 0;
            // CCW
            int rotationDeg = 180 + (360 - 90 * (i - 1));
            log.debug("Rotation PlayerCard [{}] [{}]", new Object[]{i, rotationDeg});

            if (i == 1) {
                cardGeom.move(VILLAGE_CARD_SIZE, height, 0);
            }
            if (i == 2) {
                cardGeom.move(3 * VILLAGE_CARD_SIZE, -height, 0);

            }
            if (i == 3) {
                cardGeom.move(VILLAGE_CARD_SIZE, - 3 * VILLAGE_CARD_SIZE, 0);
            }
            if (i == 4) {
                cardGeom.move(-VILLAGE_CARD_SIZE, -height, 0);
            }

            cardGeom.rotate(0, 0, rotationDeg * FastMath.DEG_TO_RAD);
            cardGeom.addControl(new OnMouseGlowControl(cam, inputManager, assetManager, rootNode));
            rootNode.attachChild(cardGeom);
        }
    }

    private void placeGhost(Node rootNode) {

        Spatial model = assetManager.loadModel("Models/Blender/Ghost/stranger4.j3o");
        model.scale(0.25f);
        model.rotate(45 * FastMath.DEG_TO_RAD, 0, 0);
        model.setLocalTranslation(startPos);
        model.setName("Supaghost");
        log.debug("load mod {}", ((com.jme3.scene.Node) model).getChild(0).getClass().getName());
        model.addControl(new OnMouseGlowControl(cam, inputManager, assetManager, rootNode, ColorRGBA.Green));
        rootNode.attachChild(model);
        model.addControl(new AnimControl());
        AnimControl control = model.getControl(AnimControl.class);
        AnimChannel chan = control.createChannel();

//        log.debug(
//                "!!!!!!!!!!!!!!!!! {}", chan.getAnimationName());
//        chan.
//        chan.setAnim("Video::stranger_d1_png");
        for (String anim
                : control.getAnimationNames()) {
            log.debug("ANIME: {}", anim);
        }
    }

    private void hud() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        /**
         * Create a new NiftyGUI object
         */
        Nifty nifty = niftyDisplay.getNifty();
        MyStartScreen startControl = new MyStartScreen(game);
        /**
         * Read your XML and initialize your custom ScreenController
         */
        nifty.fromXml("Interface/ghoststart.xml", "start", startControl);
//        nifty.gotoScreen("startScreen");
        //StartScreenState startControl = (StartScreenState) nifty.getScreen(“startScreen”).getScreenController();
        //OptionsScreenState optionsControl = (OptionsScreenState) nifty.getScreen(“optionsScreen”).getScreenController();
        stateManager.attach(startControl);

        guiViewPort.addProcessor(niftyDisplay);
    }

    private void registerKeyboardMappings() {
        inputManager.addMapping(MAPPING_BOARD_1, TRIGGER_BOARD1);

        inputManager.addMapping(MAPPING_BOARD_2, TRIGGER_BOARD2);

        inputManager.addMapping(MAPPING_BOARD_3, TRIGGER_BOARD3);

        inputManager.addMapping(MAPPING_BOARD_4, TRIGGER_BOARD4);

        inputManager.addMapping(MAPPING_MAIN_MENU, TRIGGER_MAIN_MENU);
        inputManager.addMapping(MAPPING_START_GAME, TRIGGER_START_GAME);
        inputManager.addMapping(MAPPING_QUIT_GAME, TRIGGER_QUIT_GAME);

        inputManager.addListener( new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            log.debug("Action happened {}", name);
            int plNo = 0;
            if (name.equals(MAPPING_BOARD_1)) {
                plNo = 1;
            } else if (name.equals(MAPPING_BOARD_2)) {
                plNo = 2;
            } else if (name.equals(MAPPING_BOARD_3)) {
                plNo = 3;
            } else if (name.equals(MAPPING_BOARD_4)) {
                plNo = 4;
            } else if (name.equals(MAPPING_MAIN_MENU)) {
                displayMainMenu();
            } else if (name.equals(MAPPING_START_GAME)) {
                game.start();
            } else if (name.equals(MAPPING_QUIT_GAME)) {
                app.stop();;
            }


//            lookToBoard(plNo);
            cam.lookAt(getGameboardCenter(), Vector3f.UNIT_XYZ);
        }

        private void displayMainMenu() {

            textWriter.writeToBuf("M pressed");
        }
    },
                new String[]{
            MAPPING_BOARD_1, MAPPING_BOARD_2,
            MAPPING_BOARD_3, MAPPING_BOARD_4, MAPPING_MAIN_MENU, MAPPING_START_GAME, MAPPING_QUIT_GAME
        });
    }

    private void moveCameraAroundBoard(float tpf) {
        cameraMover.move(tpf);
    }

    private void placePlayer(Node rootNode) {

//                        Spatial model = assetManager.loadModel("Models/Blender/bunny/LuckyBunny.j3o" );
        Spatial model = assetManager.loadModel("Models/Blender/duck/rubberduck.j3o");
        model.scale(0.25f);
        model.rotate(45 * FastMath.DEG_TO_RAD, 0, 0);
        model.setLocalTranslation(getGameboardCenter());
        rootNode.attachChild(model);
    }
    private ActionListener actionListener; 
}
