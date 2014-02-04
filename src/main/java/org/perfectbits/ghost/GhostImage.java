/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;

/**
 *
 * @author congo
 */
class GhostImage {

    private final AssetManager assetManager;
    private final Node guiNode;
    private final AppSettings settings;
    private final String imagePath;
    private final GhostCardImageControl control;
    private Picture pic;
    private Node n;
    private final Vector3f startpos;

    GhostImage(AssetManager assetManager, AppSettings settings, Node guiNode, String imagePath, Vector3f startPos) {

        this.assetManager = assetManager;
        this.settings = settings;
        this.guiNode = guiNode;
        this.imagePath = imagePath;
        this.control = new GhostCardImageControl();
        this.startpos = startPos;
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
        Geometry cardGeom = new Geometry("Card", card);
        Material cardMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        cardMat.setColor("Color", ColorRGBA.White);
        Texture cube1Tex = assetManager.loadTexture(
                "Interface/butka.jpg");
        cube1Tex.setWrap(Texture.WrapMode.MirroredRepeat);
        cardMat.setTexture("ColorMap", cube1Tex);
        cardGeom.setMaterial(cardMat);
        Vector3f lt = cardGeom.getLocalTranslation();
//        cardGeom.setLocalTranslation(new Vector3f(2, 2, 2));
//        new Box(1, 1, 0.1f);
        final int padding = 12;
        final int scale = 2;
        pic = new Picture("HUD Picture");
        pic.setImage(assetManager, imagePath, true);
        final int w = settings.getWidth() / scale;
        final int h = settings.getHeight() / scale;
        pic.setWidth(w);
        pic.setHeight(h);
        
//        pic.setWidth(w);
//        pic.setHeight(h);
//        pic.setPosition(settings.getWidth() - w - padding, settings.getHeight() - h - padding);
//        pic.center();
        n = new Node();
//        n.attachChild(pic);
                n.attachChild(cardGeom);
          pic.move(-w/2f,-h/2f,0);
          
//        n.setLocalTranslation(new Vector3f(settings.getWidth() / 2 - w/2, settings.getHeight() / 2 - h/2, 0));
          n.setLocalTranslation(startpos);
//        pic.center();
        n.addControl(control);
        
    }
    
}
