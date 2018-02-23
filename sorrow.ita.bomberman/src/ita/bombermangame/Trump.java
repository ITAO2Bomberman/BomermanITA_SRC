/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ita.bombermangame;

import java.net.URL;

/**
 *
 * @author Olenberg, Heinrich, Musiolik, Szymczak
 */
public class Trump extends Charakter {

    public Trump(int[] xInner, int[] yInner, BreakableBlock[] block) {
        super(xInner, yInner, block);
        initChar();
        super.charURL = charSprites();
    }
    //Überschriebene Methode zum Initialisieren des Charakters
    @Override
    public void initChar() {
        charURL = charSprites();
        super.x = 72;
        super.y = 16;
        super.heigth = 16;
        super.width = 16;
        super.bombURL=bombSprite();
        super.explodeicon=loadexplode();
        super.winnerscreen=loadwinnerscreen();
    }
    //Laden des Bombensprites von URL
    @Override
    protected URL bombSprite() {
        return Trump.class.getResource("sprites/bombs/bombAmi.png");
    }
    //Läde Explosionssprite von URL
    @Override
    protected URL loadexplode(){
        return Trump.class.getResource("sprites/bomb/explosion.png");
    }
//Macht garnicht
    
    //Läd die einzelnen Charaktersprites von den URL´s
    @Override
    protected URL[] charSprites() {
        URL[] re = new URL[4];
        for (int i = 0; i < 4; i++) {
            re[i] = Trump.class.getResource("sprites/trump/" + (i + 1) + ".png");
        }
        return re;
    }
// Überschriebene Methode zur Kollisionsberechnung 
    @Override
    public void move(BreakableBlock[] breakable) {
       
        if (!mov) {
        } else {
            //Wenn die mov true ist wird die Kollisionsberechnung gestartet
            boolean wall = false;
            // Zwei Zählschleifen werden zur Überprüfung ob es zu einer Kollision kommt 
            for (int xw : super.xInner) {
                for (int yw : super.yInner) {
                    // Kollisionsberechnung ob eine Wand mit dem Charakter kollidiert
                    if (xw == super.x + super.dx && yw == super.y + super.dy) {
                        wall = true;
                    }
                }
            }
            // Zählschleife zur Überprüfung 
            for (int i = 0; i < breakable.length; i++) {
                if (breakable[i].getX() == super.x + (super.dx) && breakable[i].getY()+super.dy == super.y + (super.dy)*2 && breakable[i].getVisibility() == true) {
                 wall = true;   
//                    System.out.println("Wall");
                }
            }
            //Wenn die Kollision nicht erfolgt soll geschaut werden ob der Charakter an einer Wand daranstößt
            if (wall == false) {
                super.y += super.dy;
                super.x += super.dx;
            }
            if (super.x < 72) {
                super.x = 72;
            }
            if (super.y < 16) {
                super.y = 16;
            }
            if (super.x > 424) {
                super.x = 424;
            }
            if (super.y > 208) {
                super.y = 208;
            }
          //  System.out.println("x " + super.x + " y " + super.y);
        }

    }

    @Override
    protected boolean dropbomb() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private URL loadwinnerscreen() {
       return Trump.class.getResource("sprites/trump/gewinntrump.png");
    }

  

}
