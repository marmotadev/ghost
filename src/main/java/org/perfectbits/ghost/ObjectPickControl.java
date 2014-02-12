/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
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
class ObjectPickControl implements Control {

    Logger log = LoggerFactory.getLogger(ObjectPickControl.class);
    Spatial model;
    private final Camera cam;
    private final InputManager inputManager;
    private final AssetManager assetManager;
    private final Node rootNode;
    private Material orig;
    private final ColorRGBA color;
    private static String MAPPING_PICK = "Pick";
    private boolean overObject = false;
    private String steppedOn;
    private final GhostImage gi;

    public ObjectPickControl(Camera cam, InputManager inputManager, AssetManager assetManager, Node rootNode, ColorRGBA color, final GhostImage gi) {
        this.cam = cam;
        this.inputManager = inputManager;
        this.assetManager = assetManager;
        this.rootNode = rootNode;
        this.color = color;
        this.gi = gi;

        inputManager.addMapping(MAPPING_PICK, new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping(MAPPING_PICK, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(new ActionListener() {
            public void onAction(String name, boolean isPressed, float tpf) {
                log.debug("Action happened {}", name);
                if (name.equals(MAPPING_PICK)) {
                    log.debug("Button pressed: " + isPressed + ", over " + steppedOn);
                    if (overObject) {
                        if (isPressed) {
                            log.debug("Pickup!!" + steppedOn);
                            gi.pickup();
                        }
                    } else {
                        if (isPressed) {
                            log.debug("Misfire");
                        } else {
                            log.debug("Dropped");
                            gi.drop();
                        }
                    }
                } else {
                    log.debug("Some fucking other action");
                }
            }
        }, MAPPING_PICK);

    }

//    ObjectPickControl(Camera cam, InputManager inputManager, AssetManager assetManager, Node rootNode) {
//        this(cam, inputManager, assetManager, rootNode, ColorRGBA.Blue);
//    }
    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSpatial(Spatial spatial) {
        model = spatial;
        log.debug("ccccc {}", spatial.getClass().getName());

        log.debug("Control spatial name {}", spatial.getName());
    }

    public void update(float tpf) {
        if (gi.isPickedUp()) {
            gi.moveToCursor();
        } else {
            // Reset results list.
            CollisionResults results = new CollisionResults();
            // Convert screen click to 3d position

            Vector3f ori = new Vector3f(inputManager.getCursorPosition().x, inputManager.getCursorPosition().y, 1f);
            Vector3f dest = new Vector3f(0f, 0f, -1f);
            Ray ray = new Ray(ori, dest);
            model.collideWith(ray, results);

            // Use the results -- we rotate the selected geometry.
            if (results.size() > 0) {
                final CollisionResult cc = results.getClosestCollision();
                // The closest result is the target that the player picked:
                Geometry target = cc.getGeometry();

                this.steppedOn = target.getName();
                if (true) {
                    overObject = true;
                    log.trace("Colision with {}", target.getName());
                } else {
                }
            } else {
                overObject = false;
                this.steppedOn = null;
//            if (orig != null) {
//                model.setMaterial(orig);
//            }
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
