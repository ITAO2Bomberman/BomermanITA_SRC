/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ita.bombermangame;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Olenberg, Heinrich, Musiolik, Szymczak
 */
public class Kim extends Charakter {

    public Kim(int[] xInner, int[] yInner, BreakableBlock[] block) {
        super(xInner, yInner, block);
        initChar();
        super.charURL = charSprites();
    }

    @Override
    public void initChar() {
        charURL = charSprites();
        super.x = 424;
        super.y = 208;
        super.heigth = 16;
        super.width = 16;
        super.bombURL=bombSprite();
        super.explodeicon=loadexplode();
        super.bomb = setbomb(false);
        super.winnerscreen = loadwinnerscreen();
    }

    @Override
    protected URL bombSprite() {
        return Kim.class.getResource("sprites/bombs/bombKora.png");
    }

    @Override
    protected URL[] charSprites() {
        URL[] re = new URL[4];
        for (int i = 0; i < 4; i++) {
            re[i] = Kim.class.getResource("sprites/fatKorean/" + (i+1) + ".png");
        }
        return re;
    }

    @Override
    public void move(BreakableBlock[] breakable) {
        if (!mov) {
            
        } else {
            boolean wall = false;
            for (int xw : super.xInner) {
                for (int yw : super.yInner) {
                    if (xw == super.x + super.dx && yw == super.y + super.dy) {
                        wall = true;
                    }
                }
            }
            for (int i = 0; i < breakable.length; i++) {
                if (breakable[i].getX() == super.x + (super.dx) && breakable[i].getY()+super.dy == super.y + (super.dy)*2 && breakable[i].getVisibility() == true) {
                 wall = true;   
                    System.out.println("Atom");
                   // dropbomb();
                }
            }
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
    
   
    
//    public void startKI(){
//        dx = -16;
//        
//    } 
//    public void stop(){
//        dx = 0;
//        dy = 0;
//        
//    }
    
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = -16;
        }

        if (key == KeyEvent.VK_D) {
            dx = 16;
        }

        if (key == KeyEvent.VK_W) {
            dy = -16;
        }

        if (key == KeyEvent.VK_S) {
            dy = 16;
        }

        if (key == KeyEvent.VK_Q) {
            bomben.add(new Bomb(explodeicon,bombURL, x, y));
        }
    }
    
    public void keyReleased(KeyEvent e) {
     key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            dx = 0;
        }

        if (key == KeyEvent.VK_D) {
            dx = 0;
        }

        if (key == KeyEvent.VK_W) {
            dy = 0;
        }

        if (key == KeyEvent.VK_S) {
            dy = 0;
        }

}
      public Image loadCharSprite() {
        switch (key) {
            case KeyEvent.VK_A: {
                return new ImageIcon(charURL[1]).getImage();
            }
            case KeyEvent.VK_D: {
                return new ImageIcon(charURL[2]).getImage();
            }
            case KeyEvent.VK_W: {
                return new ImageIcon(charURL[3]).getImage();
            }
            case KeyEvent.VK_S: {
                return new ImageIcon(charURL[0]).getImage();
            }
        }
        return new ImageIcon(charURL[0]).getImage();
    }

    @Override
    protected URL loadexplode(){
        return Trump.class.getResource("sprites/bomb/explosion.png");
    }

    @Override
    protected boolean dropbomb() {
        bomben.add(new Bomb(explodeicon, bombURL, x, y));
        bomb = setbomb(true);
        return true;
    }

    private URL loadwinnerscreen() {
        return Kim.class.getResource("sprites/fatKorean/gewinnkim.png");
    }
}
