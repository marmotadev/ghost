/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost.sandbox;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;

public class SandboxApplication extends SimpleApplication {

    public static void main(String[] args) {
        SandboxApplication app = new SandboxApplication();
        app.start();
    }
    private Geometry blue;
    private Geometry red;
    private Geometry white;
    Vector3f whitePos;
            

    @Override
    public void simpleInitApp() {
        // create a blue box at coordinates (1,-1,1)
        Box box1 = new Box(Vector3f.ZERO, 1f, 2f, .5f);
        blue = new Geometry("Box", box1);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blue.setMaterial(mat1);
        blue.move(-5, 0, -3);

        // create a red box straight above the blue one at (1,3,1)
        Box box2 = new Box(Vector3f.ZERO, 1f, 2f, .5f);
        red = new Geometry("Box", box2);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        red.setMaterial(mat2);
        red.move(5, 0, -3);
        
        Box box3 = new Box(Vector3f.ZERO, 1f, 1f, 1f);
        white = new Geometry("Box3", box1);
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.White);
        white.setMaterial(mat3);
//        blue.move(-5, 0, -3);

//        rootNode.attachChild(blue);
//        rootNode.attachChild(red);
        Node rotNode = new Node();
        rotNode.setLocalTranslation(2, 2, 2);
        rotNode.attachChild(white);
        rootNode.attachChild(rotNode);

        blue.lookAt(red.getWorldTranslation(), new Vector3f(0, 1, 0));
        red.lookAt(blue.getWorldTranslation(), new Vector3f(0, 1, 0));
        
        
        attachCoordinateAxes(Vector3f.ZERO);
        flyCam.setMoveSpeed(80f);
        whitePos = new Vector3f(3f, 2f, 1.1f);
        white.move(whitePos);
    }

//    @Override
//    public void simpleUpdate(float tpf) {
//        red.rotate(0, 0.001f, 0);
//
//        // For the red (moves in a circle)
//        Quaternion rotation = red.getLocalRotation();
//        Vector3f front = new Vector3f(0, 0, 0.01f);
//        Vector3f heading = rotation.mult(front);
//        red.move(heading);
//
//        /// For the blue (follows the red)
//        blue.lookAt(red.getWorldTranslation(), Vector3f.UNIT_Y);
//        float velocity = 0.01f;
//        Vector3f trajectory = red.getWorldTranslation().subtract(blue.getWorldTranslation());
//        trajectory = trajectory.normalize();
//        Vector3f offset = trajectory.mult(velocity);
//        blue.move(offset);
//        System.out.print(offset);
//
//    }
    @Override
    public void simpleUpdate(float tpf) {
        Quaternion localQuat = white.getLocalRotation();
        
        float angle = 360f/(1/tpf) * FastMath.DEG_TO_RAD / 2;
        final Vector3f axis = Vector3f.UNIT_Y.add(0.1f, 0.1f, 0.1f).multLocal(10f);
        
        
        Arrow arrow = new Arrow(axis);
        arrow.setLineWidth(4); // make arrow thicker
        putShape(arrow, ColorRGBA.Orange).setLocalTranslation(whitePos.subtract(axis.normalize().mult(5f)));
        
        
        Quaternion rotQuat = Quaternion.IDENTITY.fromAngleAxis(angle, axis);
        localQuat = rotQuat.mult(localQuat);
        white.setLocalRotation(localQuat);

    }
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
}
