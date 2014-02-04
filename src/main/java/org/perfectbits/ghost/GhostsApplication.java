package org.perfectbits.ghost;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.MatParam;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.CartoonEdgeFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * test
 *
 * @author normenhansen
 */
public class GhostsApplication extends SimpleApplication {

    Logger log = LoggerFactory.getLogger(GhostsApplication.class);
    
    
    
    
    public BitmapFont getGuiFont() {
        return guiFont;
    }
    
    public AppSettings getSettings() {
        return settings;
    }

    private void checkForCollision(Float intensity) {
        // Reset results list.
        CollisionResults results = new CollisionResults();
        // Convert screen click to 3d position
        Vector2f click2d = inputManager.getCursorPosition();
        Vector3f click3d = cam.getWorldCoordinates(
                new Vector2f(click2d.getX(), click2d.getY()), 0f);
        Vector3f dir = cam.getWorldCoordinates(
                new Vector2f(click2d.getX(), click2d.getY()), 1f).subtractLocal(click3d);
        // Aim the ray from the clicked spot forwards.
        Ray ray = new Ray(click3d, dir);
        // Collect intersections between ray and all nodes in results list.
        rootNode.collideWith(ray, results);
        // (Print the results so we see what is going on:)
        for (int i = 0; i < results.size(); i++) {
            // (For each “hit”, we know distance, impact point, geometry.)
            float dist = results.getCollision(i).getDistance();
            Vector3f pt = results.getCollision(i).getContactPoint();
            String target = results.getCollision(i).getGeometry().getName();
//                log.debug("Selection #" + i + ": " + target + " at " + pt + ", " + dist + " WU away.");
        }
        // Use the results -- we rotate the selected geometry.
        if (results.size() > 0) {
            // The closest result is the target that the player picked:
            Geometry target = results.getClosestCollision().getGeometry();
            final Material m = target.getMaterial();
            // Here comes the action:
            if (target.getName().equals("stranger1")) {
                for (MatParam p : m.getParams()) {
                    try {
                        log.debug("param: {}", p);
                    } catch (Exception e) {
                    }
                }
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
                mat.setColor("GlowColor", ColorRGBA.Green);
                mat.setColor("Color", ColorRGBA.Green);

                target.setMaterial(mat);


                log.debug("Colision with {}", target.getName());
            }
        }
    }
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
            if (name.equals("Rotate")) {
                checkForCollision(intensity);
            }

        }
    };

    public GhostsApplication() {
        showSettings = false;
        configLoggers();
    }

    public static void main(String[] args) {
        GhostsApplication app = new GhostsApplication();
        app.start();
    }

    
    private Trigger trigger_rotate = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);

    @Override
    public void simpleInitApp() {

        GameAppState  state = new GameAppState();
        stateManager.attach(state);
        stateManager.attach(new DebugGuiAppState());

        inputManager.deleteMapping(INPUT_MAPPING_MEMORY);
        inputManager.addMapping("Rotate", trigger_rotate);
        inputManager.addListener(analogListener, new String[]{"Rotate"});

        //glow
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
//        bloom.setDownSamplingFactor(200.0f); 
        fpp.addFilter(bloom);

        //toon
        CartoonEdgeFilter toon = new CartoonEdgeFilter();
        toon.setDepthThreshold(speed);
        fpp.addFilter(toon);
        viewPort.addProcessor(fpp);

        //sun
        DirectionalLight sunLight = new DirectionalLight();
        sunLight.setDirection(new Vector3f(0.3f, -0.5f, -0.5f));
        rootNode.addLight(sunLight);

        
        

        


        
        
        settings.setVSync(
                true);

        

        


        

        
    }
    

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    
    

    private void configLoggers() {
        System.setProperty("java.util.logging.config.file", "logging.properties");
    }

   

    
    public Node getRootNode() {
        return rootNode;
    }
}
