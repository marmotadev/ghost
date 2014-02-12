/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author congo
 */
class GhostImage {

    Logger log = LoggerFactory.getLogger(GhostImage.class);
    private final AssetManager assetManager;
    private final Node guiNode;
    private final AppSettings settings;
    private final String imagePath;
    private final GhostCardImageControl control;
    private Picture pic;
    private Node n;
    private final Vector3f startpos;
    private final Camera cam;
    private Geometry cardGeom;
    private final Node rootNode;
    private Geometry pickedUpCard;
    private final InputManager inputManager;
    private Node n1;

    GhostImage(AssetManager assetManager, AppSettings settings, Node guiNode, Node rootNode, String imagePath, Vector3f startPos, Camera cam, InputManager inputManager) {

        this.assetManager = assetManager;
        this.settings = settings;
        this.guiNode = guiNode;
        this.rootNode = rootNode;
        this.imagePath = imagePath;
        this.control = new GhostCardImageControl();
        this.startpos = startPos;
        this.cam = cam;
        this.inputManager = inputManager;
        init();
    }

    void display() {
        guiNode.attachChild(n);

        control.start();
    }

    public GhostCardImageControl getControl() {
        return control;
    }

    private void init() {
        Box card = new Box(1, 1.5f, 0.02f);
        cardGeom = new Geometry("NewGhostCard", card);
        Material cardMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cardMat.setColor("Color", ColorRGBA.White);
        Texture cube1Tex = assetManager.loadTexture(
                "Interface/butka.jpg");
        cube1Tex.setWrap(Texture.WrapMode.MirroredRepeat);
        cardMat.setTexture("ColorMap", cube1Tex);
        cardGeom.setMaterial(cardMat);

        final float scale = 2f;
        pic = new Picture("HUD Picture");
        pic.setImage(assetManager, imagePath, true);
        final int w = (int) (settings.getWidth() / scale);
        final int h = (int) (settings.getHeight() / scale);
        log.debug(" w:h=" + w + ":" + h);
        pic.setWidth(w);
        pic.setHeight(h);

        n = new Node();
//        n.attachChild(randomBox());
        n.attachChild(cardGeom);
//         pic.move(-w/2f,-h/2f,0);
        final Vector3f lt = new Vector3f(settings.getWidth() / 2 - w / 2, settings.getHeight() / 2 - h / 2, 0);
//        log.debug("CCCCCCC: " + lt);
        n.setLocalTranslation(lt);
//        n.setLocalTranslation(cam.getLocation());
        n.addControl(control);

//        n.move(0, 5, 0);
//        n.lookAt(cam.getDirection(), cam.getUp());
//        log.debug("Cam loc:" + cam.getLocation());
//        log.debug("Node loc:" + n.getLocalTranslation());
        n.scale(100f);
//        log.debug("ghost card loc:" + n.getLocatio);

    }

    void addControl(Control control) {
        n.addControl(control);
    }

    private Geometry randomBox() {
        Box b = new Box(1, 1, 1); // create cube shape
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.White);   // set color of material to blue
        geom.setMaterial(mat);
        geom.scale(0.5f);
        geom.setLocalTranslation(startpos);
        return geom;
    }

    public void pickup() {
//        n.detachChild(cardGeom);
//        cardGeom.scale(0.01f);
//        cardGeom.clone();
//        this.pickedUpCard = cardGeom.clone();
//        pickedUpCard.setLocalTranslation(startpos);
//        rootNode.attachChild(pickedUpCard);
        pickedUpCard = randomBox();
//        Vector3f dir = cam.getDirection().normalize();
        pickedUpCard.setLocalTranslation(startpos);
        n1 = new Node();
        rootNode.attachChild(n1);
        float z = moveToCursor();
        addGridOnProjectedPlane(z);

//        rootNode.attachChild(pickedUpCard);
    }

    public void drop() {
//        rootNode.detachChild(n1);
//        if (pickedUpCard != null) {
//            cardGeom.scale(100f);
//            n.attachChild(cardGeom);
//            rootNode.detachChild(pickedUpCard);
//            pickedUpCard = null;
//        }
        
    }

    public boolean isPickedUp() {
        return pickedUpCard != null;
    }

//    void moveToCursor() {
//        Vector2f click2d = inputManager.getCursorPosition();
//        Vector3f boardNormal = Vector3f.UNIT_Z;
//        
//        
//        /**
//         * this how much plane needs to be rotated *
//         */
//
////        log.debug("Angle: " + angl *  * FastMath.RAD_TO_DEG);
//
//        float angl = boardNormal.angleBetween(cam.getDirection().negate());
////        sq.fromAngleAxis(angl, startpos);
//        final float zProj = cam.getViewToProjectionZ(10);
//        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.getX(), click2d.getY()), zProj);
//        
//        Vector3f screenWorldPos = cam.getWorldCoordinates(new Vector2f(0, 0), zProj);
//        // it gets real-world vector for offset, but we need local offset relative to screenWorldPos.
//        Vector3f offset = click3d.subtract(screenWorldPos);
////        log.debug("offset from rw coord: " + offset);
//        pickedUpCard.setLocalTranslation(offset);
//        log.debug("click3d: " + click3d);
////        pickedUpCard.setLocalTranslation(click3d);
//        
//        n1.setLocalTranslation(screenWorldPos);
//        n1.setLocalRotation(cam.getRotation());
//        n1.attachChild(pickedUpCard);
//
//        Vector3f camXFar = cam.getWorldCoordinates(new Vector2f(200,0), zProj).normalize();
//        Vector3f camAxisNormalized = camXFar.subtract(screenWorldPos).normalize();
//        
//        Quaternion qrot = Quaternion.IDENTITY.fromAngleNormalAxis(angl, camAxisNormalized);
//        qrot = qrot.mult(n1.getLocalRotation());
//        n1.setLocalRotation(qrot);
//        rootNode.attachChild(n1);
//        
//    }
    float moveToCursor() {
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f boardNormal = Vector3f.UNIT_Z;
        
        
        /**
         * this how much plane needs to be rotated *
         */

        

        float angl = boardNormal.angleBetween(cam.getDirection().negate());
        log.debug("Angle: " + angl * FastMath.RAD_TO_DEG);
        final float zProj = cam.getViewToProjectionZ(10);
        Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.getX(), click2d.getY()), zProj);
        
        
        Vector3f screenWorldPos = cam.getWorldCoordinates(new Vector2f(0, 0), zProj);
        log.debug("Screen start " + screenWorldPos);
        log.debug("Click " + click3d);
//        // it gets real-world vector for offset, but we need local offset relative to screenWorldPos.
        Vector3f offset = click3d.subtract(screenWorldPos);
        log.debug("shortened click "  + offset);
//
//        pickedUpCard.setLocalTranslation(offset);
//        log.debug("click3d: " + click3d);
////        pickedUpCard.setLocalTranslation(click3d);
//        
//        n1.setLocalTranslation(screenWorldPos);
//        n1.setLocalRotation(cam.getRotation());
//        n1.attachChild(x);

        Vector3f camXFar = cam.getWorldCoordinates(new Vector2f(200,0), zProj).normalize();
        Vector3f camAxisNormalized = camXFar.subtract(screenWorldPos).normalize();
        
        Quaternion qrot = Quaternion.IDENTITY.fromAngleNormalAxis(angl, camAxisNormalized);
        Vector3f rotatedProjected = qrot.mult(offset).add(screenWorldPos);
        log.debug("Rotated projected " + rotatedProjected);
        //        offset.mult(qrot);
        //        qrot = qrot.mult(n1.getLocalRotation());
//                n1.setLocalRotation(qrot);
                pickedUpCard.setLocalTranslation(rotatedProjected);
        rootNode.attachChild(pickedUpCard);
        return zProj;
    }

    private void addGridOnProjectedPlane(float zProj) {
        Vector3f x00 = cam.getWorldCoordinates(Vector2f.ZERO, zProj);
        Vector3f x01 = cam.getWorldCoordinates(new Vector2f(0, settings.getHeight()), zProj);
        Vector3f x10 = cam.getWorldCoordinates(new Vector2f(settings.getWidth(), 0), zProj);
        Vector3f x11 = cam.getWorldCoordinates(new Vector2f(settings.getWidth(), settings.getHeight()), zProj);
        Vector3f centerPoint = cam.getWorldCoordinates(new Vector2f(settings.getWidth()/2, settings.getHeight()/2), zProj);
        int size = 10;
        Geometry grid;
        Vector3f lenv = x10.subtract(x00);
        float dist= lenv.length() / size;;
        grid = DebugGuiAppState.buildGrid(assetManager, size, centerPoint, dist);
        grid.setLocalRotation(cam.getRotation());
        rootNode.attachChild(grid);
        
    }
}
