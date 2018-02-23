/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ita.bombermangame;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Olenberg, Heinrich, Musiolik, Szymczak
 */
public class BreakableBlock {
    private final int x,y;
    private boolean vis = true;
    private final Image bSprite;
    
    /**
     *
     * @param x Position auf der X-Achse in der Map
     * @param y Position auf der Y-Achse in der Map
     */
    public BreakableBlock(int x,int y){
        //Sprite für die Zerstörbaren Blöcke wird geladen
        this.bSprite = new ImageIcon(App.class.getResource("sprites/mauer/breakable.png")).getImage();
        this.x=x;
        this.y=y;
    }
    
    public boolean getVisibility(){
        return vis;
    }
    
    public void zerbrechen(){
        vis=false;
    }
    
    public Image getSprite(){
        return bSprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
