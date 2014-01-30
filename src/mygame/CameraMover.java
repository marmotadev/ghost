/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import static mygame.Main.MAPPING_BOARD_1;
import static mygame.Main.startPos;

/**
 *
 * @author congo
 */
public class CameraMover {

    final float speed = 0.1f;
    final float full = 360;
    final float radius = 15;
    private float currentCamDeg = 0;
    private final Camera camera;
    private final Vector3f focusPoint;
    public CameraMover(Camera cam, Vector3f focalPoint) {
        this.camera = cam;
        this.focusPoint = focalPoint;
        coordLog();
    }
    private Vector3f getNewCameraPos(float tpf) {

        currentCamDeg += full * tpf * speed;
        normalizeDegreeses();
        //4s - pilnas ratas.
        Vector3f newpos = new Vector3f(
                startPos.x + FastMath.cos(FastMath.DEG_TO_RAD * currentCamDeg) * radius,
                startPos.y + FastMath.sin(FastMath.DEG_TO_RAD * currentCamDeg) * radius,
                startPos.z + 5);
        return newpos;
    }
    public void move(float tpf) {
        final Vector3f v = getNewCameraPos(tpf);
        camera.setLocation(v);
        camera.lookAt(focusPoint, Vector3f.UNIT_XYZ);
    }

    private void normalizeDegreeses() {
        if (currentCamDeg > 360) {
            currentCamDeg %= 360;
        }
    }

    private void coordLog() {
        new Timer(MAPPING_BOARD_1, true).scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                System.out.println("current deg:" + currentCamDeg);
            }
        }, 0l, 500);
    }
}
