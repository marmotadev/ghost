package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private static final Logger log = Logger.getLogger(Main.class.getName());
    public static Vector3f startPos = new Vector3f(2, 2, -20);
    public static float VILLAGE_CARD_SIZE = 1.5f;
    public static final String MAPPING_BOARD_1 = "Board 1";
    public static final String MAPPING_BOARD_2 = "Board 2";
    public static final String MAPPING_BOARD_3 = "Board 3";
    public static final String MAPPING_BOARD_4 = "Board 4";

    private CameraMover cameraMover;

    public Main() {
        showSettings = false;
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    public Vector3f getGameboardCenter() {
        return startPos.add(VILLAGE_CARD_SIZE * 3f / 2f, -VILLAGE_CARD_SIZE * 3f / 2f, 0);
    }

    @Override
    public void simpleInitApp() {

        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

//        rootNode.attachChild(geom);
        Geometry cardGeom = buildCard();

        attachCoordinateAxes(Vector3f.ZERO);
//        rootNode.attachChild(cardGeom);
        flyCam.setEnabled(false);
        
        
        placeGameArtifacts(rootNode);
        this.cameraMover = new CameraMover(cam, getGameboardCenter());
        cam.setLocation(cam.getLocation().add(-2, -7, -28));
        cam.lookAt(getGameboardCenter(), Vector3f.UNIT_XYZ);
        inputManager.addMapping(MAPPING_BOARD_1, TRIGGER_BOARD1);
        inputManager.addMapping(MAPPING_BOARD_2, TRIGGER_BOARD2);
        inputManager.addMapping(MAPPING_BOARD_3, TRIGGER_BOARD3);
        inputManager.addMapping(MAPPING_BOARD_4, TRIGGER_BOARD4);
        inputManager.addListener(actionListener, new String[]{
            MAPPING_BOARD_1, MAPPING_BOARD_2,
            MAPPING_BOARD_3, MAPPING_BOARD_4});
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean isPressed, float tpf) {
            log.log(Level.WARNING, "Action happened " + name);
            int plNo = 0;
            if (name.equals(MAPPING_BOARD_1)) {
                plNo = 1;
            } else if (name.equals(MAPPING_BOARD_2)) {
                plNo = 2;
            } else if (name.equals(MAPPING_BOARD_3)) {
                plNo = 3;
            } else if (name.equals(MAPPING_BOARD_4)) {
                plNo = 4;
            }

            lookToBoard(plNo);
            cam.lookAt(getGameboardCenter(), Vector3f.UNIT_XYZ);
        }

        private void lookToBoard(int plNo) {
            // cameras distance from center
            float dist = 10f;
            cam.setLocation(startPos);
            cam.setRotation(Quaternion.ZERO);
            Vector3f l = cam.getLocation();
            if (plNo == 1) {
                l = l.add(0, dist, 0);
            }
            if (plNo == 2) {
                l = l.add(dist, 0, 0);
            }
            if (plNo == 3) {
                l = l.add(0, -dist, 0);
            }
            if (plNo == 4) {
                l = l.add(-dist, 0, 0);
            }
            cam.setLocation(l);
        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        moveCameraAroundBoard(tpf);
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
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

        log.log(Level.WARNING, "Village at [{0}] [{1}] [{2}]", new Object[]{x, y, z});

        cardGeom.setLocalTranslation(lt.add(new Vector3f(x, y, z)));

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
            log.log(Level.WARNING, "Rotation PlayerCard [{0}] [{1}]", new Object[]{i, rotationDeg});

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
            rootNode.attachChild(cardGeom);
        }
    }

    private void placeGhost(Node rootNode) {

        Spatial model = assetManager.loadModel("Models/Blender/Ghost/stranger4.j3o");
        model.scale(0.25f);
        model.rotate(45 * FastMath.DEG_TO_RAD, 0, 0);
        model.setLocalTranslation(startPos);
        rootNode.attachChild(model);
    }
//    private Vector3f getPositionForTile(int row, int col) {
//        startPos.add(speed, speed, speed)
//    }

    private void attachCoordinateAxes(Vector3f pos) {
        Arrow arrow = new Arrow(Vector3f.UNIT_X);
        arrow.setLineWidth(4); // make arrow thicker
        putShape(arrow, ColorRGBA.Red).setLocalTranslation(pos);

        arrow = new Arrow(Vector3f.UNIT_Y);
        arrow.setLineWidth(4); // make arrow thicker
        putShape(arrow, ColorRGBA.Green).setLocalTranslation(pos);

        arrow = new Arrow(Vector3f.UNIT_Z);
        arrow.setLineWidth(4); // make arrow thicker
        putShape(arrow, ColorRGBA.Blue).setLocalTranslation(pos);
    }

    private Geometry putShape(Mesh shape, ColorRGBA color) {
        Geometry g = new Geometry("coordinate axis", shape);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", color);
        g.setMaterial(mat);
        rootNode.attachChild(g);
        return g;
    }
    private final static Trigger TRIGGER_BOARD1 = new KeyTrigger(KeyInput.KEY_NUMPAD1);
    private final static Trigger TRIGGER_BOARD2 = new KeyTrigger(KeyInput.KEY_NUMPAD2);
    private final static Trigger TRIGGER_BOARD3 = new KeyTrigger(KeyInput.KEY_NUMPAD3);
    private final static Trigger TRIGGER_BOARD4 = new KeyTrigger(KeyInput.KEY_NUMPAD4);

    private void placePlayer(Node rootNode) {

//                        Spatial model = assetManager.loadModel("Models/Blender/bunny/LuckyBunny.j3o" );
        Spatial model = assetManager.loadModel("Models/Blender/duck/rubberduck.j3o");
        model.scale(0.25f);
        model.rotate(45 * FastMath.DEG_TO_RAD, 0, 0);
        model.setLocalTranslation(getGameboardCenter());
        rootNode.attachChild(model);
    }

    private void moveCameraAroundBoard(float tpf) {
        cameraMover.move(tpf);
    }
}
