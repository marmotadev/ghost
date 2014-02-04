/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.perfectbits.ghost;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author congo
 */
public class GUIScreenWriter {
    public static final int BUFSIZE = 20;
    public static final float TXT_SCALE = 0.75f;
    private final Node guiNode;
    private final BitmapFont guiFont;
    private final BitmapText hudText;
    private String[] buffer = new String[BUFSIZE];
    Logger log = LoggerFactory.getLogger(GUIScreenWriter.class);
    private final AppSettings settings;
    
    public GUIScreenWriter(Node guiNode, BitmapFont guiFont, AppSettings settings) {
        this.guiNode = guiNode;
        this.guiFont = guiFont;
        this.settings = settings;
        
        BitmapText hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.LightGray);                             // font color
        
        
        this.hudText = hudText;
        this.hudText.scale(TXT_SCALE);
        guiNode.attachChild(hudText);
        
    }

    public void writeToBuf(String txt) {
        for (int i=BUFSIZE - 1; i >= 1; i--) {
            buffer[i] = buffer[i - 1];
        }
        buffer[0] = txt;
        
        StringBuffer sb = new StringBuffer(500);
//        sb.append(">>");
        for (int i=BUFSIZE - 1; i >= 0; i--) {
            if (buffer[i] != null)
                sb.append(buffer[i]).append("\n");
        }
//        sb.append("<<");
        
        write(sb.toString());
    }
    public void write(String txt) {
        log.debug("Writing text: \r\n " + txt);
        hudText.setText(txt);             // the text
        log.debug("Line count:" + hudText.getLineCount());
        hudText.setLocalTranslation(settings.getWidth() - hudText.getLineWidth() * TXT_SCALE, hudText.getLineHeight() * hudText.getLineCount() * TXT_SCALE, 0); // position
    }
    
}
