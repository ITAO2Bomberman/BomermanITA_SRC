/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ita.bombermangame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Olenberg, Heinrich, Musiolik, Szymczak
 */
public abstract class Charakter {

    protected ArrayList<Bomb> bomben = new ArrayList<>();
    protected int dx, dy, x, y, width, heigth;
    protected int bx, by;
    protected URL[] charURL;
    protected URL bombURL;
    protected URL explodeicon;
    protected boolean mov = true;
    protected int key = KeyEvent.VK_DOWN;
    protected final int[] xInner;
    protected final int[] yInner;
    protected BreakableBlock[] block;
    protected boolean bomb;
    protected boolean vis = true;
    protected int punktestand = 0;
    protected URL winnerscreen;

    public int getPunktestand() {
        return punktestand;
    }

    public void setPunktestand(int erhöhen) {
        punktestand += erhöhen;
    }

    public boolean getVis() {
        return vis;
    }

    public void setVis(boolean vis) {
        this.vis = vis;
    }

    public Charakter(int[] xInner, int[] yInner, BreakableBlock[] block) {
        this.xInner = xInner;
        this.yInner = yInner;
        this.block = block;

    }

    //Setzt eine Bombe (wird noch nicht verwendet)
    protected boolean setbomb(boolean wert) {
        bomb = wert;
        return wert;
    }

    //Abstrakte Klasse für das Laden des Explosionssprites
    protected abstract URL loadexplode();

    //Abstrakte Klasse zum Initialisieren des Charakters
    public abstract void initChar();
    //Abstrakte Klassen zur KI
//    public abstract void startKI();
//    public abstract void stop();

    //Abstrakte Klasse zum Initialisieren des Bombensprites
    protected abstract URL bombSprite();

    //Abstrakte Klasse zum Initialisieren des Charaktersprites
    protected abstract URL[] charSprites();

    //Klasse zum zeichnen der Bomben 
    public void drawBomb(Graphics g, Charakter c1, Charakter c2) {
        //ArrayList Stream zum zeichnen der Bombenelemente
        bomben.stream().filter((b) -> ((b.getVis() == true && b.getBvis() == true) || (b.getBvis() == true && b.getVis() == false) || (b.getBvis() == false && b.getVis() == true))).map((b) -> {
            if (b.getVis() == true) {

                g.drawImage(b.getBombSprite(), b.getX(), b.getY(), null);
            }
            return b;

        }).forEach((b) -> {
            //Variablen zum zeichnen der Explosion wenn die Explosion auf eine Mauer trifft soll die Explosion nicht gezeichnet werden
            boolean up = false;
            boolean down = false;
            boolean left = false;
            boolean right = false;

            punktestand = punktestand + b.explode(block, c1, c2);
            //wenn die Explosionsvariable auf true steht soll er mit der Überprüfung starten
            if (b.getBvis() == true) {
                //Schleife geht die Inneren Blöcke durch
                for (int i = 0; i < yInner.length; i++) {
                    for (int j = 0; j < xInner.length; j++) {
                        //Überprüft die einzelnen Positionen ob die Explosionen die Inneren Blöcke Berühren
                        //Wenn sie Kollidieren wird ein true zurückgegeben
                        if ((yInner[i] == b.getY() - 16 && xInner[j] == b.getX()) || b.getY() - 16 == 0) {
                            up = true;
                        }
                        if ((yInner[i] == b.getY() + 16 && xInner[j] == b.getX()) || b.getY() + 16 == 272 - 48) {
                            down = true;
                        }
                        if ((yInner[i] == b.getY() && xInner[j] == b.getX() - 16) || b.getX() - 16 == 56) {
                            left = true;
                        }
                        if ((yInner[i] == b.getY() && xInner[j]  == b.getX() + 16) || b.getX() + 16 >= 440) {
                            right = true;
                        }
                    }
                }
                g.drawImage(b.getexplodesprite(), b.getX(), b.getY(), null);
                //Wenn die Variablen ein false zurück geben wird wird die Explosion gezeichnet
                //left
                if (left == false) {
                    g.drawImage(b.getexplodesprite(), b.getX() - 16, b.getY(), null);
                }
//right
                if (right == false) {
                    g.drawImage(b.getexplodesprite(), b.getX() + 16, b.getY(), null);
                }//down
                if (up == false) {
                    g.drawImage(b.getexplodesprite(), b.getX(), b.getY() - 16, null);
                }//up
                if (down == false) {
                    g.drawImage(b.getexplodesprite(), b.getX(), b.getY() + 16, null);
                }
            }
        });
    }
//Läd die Charakter Sprites in Abhängikeit vom Tastendruck

    public Image loadCharSprite() {
        //Mit dem Switch-Case wird in Abhängigkeit des Tastendrucks der Demensprächende Sprite geladen
        switch (key) {
            case KeyEvent.VK_LEFT: {
                return new ImageIcon(charURL[1]).getImage();
            }
            case KeyEvent.VK_RIGHT: {
                return new ImageIcon(charURL[2]).getImage();
            }
            case KeyEvent.VK_UP: {
                return new ImageIcon(charURL[3]).getImage();
            }
            case KeyEvent.VK_DOWN: {
                return new ImageIcon(charURL[0]).getImage();
            }
        }
        return new ImageIcon(charURL[0]).getImage();
    }

    //Abstrakte Methode zur Bewegung
    public abstract void move(BreakableBlock[] breakable);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setMovable(Boolean mov) {
        this.mov = mov;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, heigth);
    }
// Bei Tastendruck werden die Bewegungsvariablen verändert

    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -16;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 16;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -16;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 16;
        }
        // Beim drücken der Lehrtaste wird ein neues Bombenobjekt erstellt
        if (key == KeyEvent.VK_SPACE) {

            bomben.add(new Bomb(explodeicon, bombURL, x, y));

        }
    }

    //Beim loslassen Tasten werden die bewegungsvariablen auf null gesetzt
    public void keyReleased(KeyEvent e) {
        key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    //Noch nicht benutzt
    protected abstract boolean dropbomb();

    protected Image getwinnerscreen() {
        Image i = new ImageIcon(winnerscreen).getImage();
        i.getScaledInstance(480, 272, Image.SCALE_AREA_AVERAGING);
        return i;
    }
    

}
