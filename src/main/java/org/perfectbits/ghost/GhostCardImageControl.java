/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author congo card rotates from perspective to screen and ends at 15 deg
 * after rotating 360 * 4
 */
public class GhostCardImageControl extends AbstractControl {
    Logger log = LoggerFactory.getLogger(GhostsApplication.class);

    float animTime = 0.5f; // 1 second
    float rotateRounds = 1.05f;
    float curDeg = 0;
    float endDeg = rotateRounds * 360;
    float degPerSec = endDeg / animTime;
    
    volatile boolean active = false;
    public void start() {
        active = true;
    }

    public GhostCardImageControl() {
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (!active)
            return;
        curDeg += degPerSec * tpf;
        if (curDeg <= endDeg) 
        {
            Quaternion q = getSpatial().getLocalRotation();
            //            curDeg = 45 * FastMath.DEG_TO_RAD ;
            float d = curDeg * FastMath.DEG_TO_RAD;
            q.fromAngles(0, 0, d);
            getSpatial().setLocalRotation(q);
            
            
        }
        else
            active = false;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
