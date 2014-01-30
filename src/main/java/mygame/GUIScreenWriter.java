/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/**
 *
 * @author congo
 */
public class GUIScreenWriter {
    private final Node guiNode;
    private final BitmapFont guiFont;
    private final BitmapText hudText;

    GUIScreenWriter(Node guiNode, BitmapFont guiFont) {
        this.guiNode = guiNode;
        this.guiFont = guiFont;
        BitmapText hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.White);                             // font color
        
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0); // position
        this.hudText = hudText;
        guiNode.attachChild(hudText);
    }
    public void write(String txt) {
        hudText.setText(txt);             // the text
    }
    
}
