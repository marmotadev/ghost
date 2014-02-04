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

    private static final Logger log = LoggerFactory.getLogger(GhostsApplication.class);
    private Trigger trigger_rotate = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    private boolean useDebug = true;
    
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
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

    @Override
    public void simpleInitApp() {

        GameAppState state = new GameAppState();
        stateManager.attach(state);
        if (useDebug)
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

        configureSettings();
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    private void configLoggers() {
        System.setProperty("java.util.logging.config.file", "logging.properties");
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public AppSettings getSettings() {
        return settings;
    }

    private void configureSettings() {
        settings.setVSync(true);
        setDisplayStatView(false); 
        setDisplayFps(false);
    }
}
