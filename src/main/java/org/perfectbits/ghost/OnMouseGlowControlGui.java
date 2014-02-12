/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.collision.CollisionResults;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author congo
 */
class OnMouseGlowControlGui implements Control {

    Logger log = LoggerFactory.getLogger(OnMouseGlowControlGui.class);
    Spatial model;
    private final Camera cam;
    private final InputManager inputManager;
    private final AssetManager assetManager;
    private final Node rootNode;
    private Material orig;
    private final ColorRGBA color;

    public OnMouseGlowControlGui(Camera cam, InputManager inputManager, AssetManager assetManager, Node rootNode, ColorRGBA color) {
        this.cam = cam;
        this.inputManager = inputManager;
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        this.color = color;
    }

    OnMouseGlowControlGui(Camera cam, InputManager inputManager, AssetManager assetManager, Node rootNode) {
        this(cam, inputManager, assetManager, rootNode, ColorRGBA.Blue);
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSpatial(Spatial spatial) {
        model = spatial;
        log.debug("ccccc {}", spatial.getClass().getName());

        log.debug("Control spatial name {}", spatial.getName());
    }

    public void update(float tpf) {
        // Reset results list.
        CollisionResults results = new CollisionResults();
        // Convert screen click to 3d position

        Vector3f ori = new Vector3f(inputManager.getCursorPosition().x, inputManager.getCursorPosition().y, 1f);
        Vector3f dest = new Vector3f(0f, 0f, -1f);
        Ray ray = new Ray(ori, dest);

//        Vector2f click2d = inputManager.getCursorPosition();
//        Vector3f click3d = cam.getWorldCoordinates(
//                new Vector2f(click2d.getX(), click2d.getY()), 0f);
//        Vector3f dir = cam.getWorldCoordinates(
//                new Vector2f(click2d.getX(), click2d.getY()), 1f).subtractLocal(click3d);
//        // Aim the ray from the clicked spot forwards.
//        Ray ray = new Ray(click3d, dir);
        // Collect intersections between ray and all nodes in results list.
        model.collideWith(ray, results);

        // Use the results -- we rotate the selected geometry.
        if (results.size() > 0) {


            // The closest result is the target that the player picked:
            Geometry target = results.getClosestCollision().getGeometry();
            final Material m = target.getMaterial();
            if (orig == null) {
                orig = m;
            }
            // Here comes the action:
//            target.g
//            if (target.getName().equals(model.getName())) {
            if (true) {
//                for (MatParam p : m.getParams()) {
//                    try {
//                        log.debug("param: {}", p);
//                    } catch (Exception e) {
//                    }
//                }
//                Texture cube1Tex = assetManager.loadTexture(
//                "Interface/butka.jpg");
//                cube1Tex.setWrap(Texture.WrapMode.MirroredRepeat);
                Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//                m3.setTexture("ColorMap", cube1Tex);
//                  Material mat = new Material(getAssetManager(), "MatDefs/Misc/SolidColor.j3md");
//                  Material mat = new Material(getAssetManager(), "MatDefs/Misc/SolidColor.j3md");
                TextureKey tankGlow = new TextureKey(
                        "Interface/butka.jpg", false);

                mat.setTexture("GlowMap",
                        assetManager.loadTexture(tankGlow));
                mat.setColor("GlowColor", color);
                mat.setColor("Color", color);

                target.setMaterial(mat);

                Geometry mainNode = target;
                AmbientLight ambient = new AmbientLight();
                ambient.setColor(color);
                mainNode.addLight(ambient);

                log.trace("Colision with {}", target.getName());
            }
        } else {
            if (orig != null) {
                model.setMaterial(orig);
            }
        }
    }

    public void render(RenderManager rm, ViewPort vp) {
    }

    public void write(JmeExporter ex) throws IOException {
    }

    public void read(JmeImporter im) throws IOException {
    }
}
