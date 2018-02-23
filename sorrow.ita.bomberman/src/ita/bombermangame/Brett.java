/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ita.bombermangame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.player.Player;

/**
 *
 * @author Olenberg, Heinrich, Musiolik, Szymczak
 */
public class Brett extends JPanel implements ActionListener {

    private final int B_WIDTH = 480;
    private final int B_HEIGHT = 272;
    private Timer timer;

    private final int[] xInner = {88, 120, 152, 184, 216, 248, 280, 312, 344, 376, 408};
    private final int[] yInner = {32, 64, 96, 128, 160, 192};
    private BreakableBlock[] breakableBlock;
    private Charakter c1;
    private Charakter c2;
    private Image outerWall;
    private Image innerWall;
    private int time = 1;
    private int punkteDrump = 0;
    private int punkteKim = 0;
    private boolean wind = true;
    private boolean wink = true;
    private int countdown = 120;
    private int timer1;
    private Player p1;
    private String f;
    private boolean drawwindrump = false;
    private boolean drawwinkim = false;
    Sound s = new Sound("Musik.wav");
    
    Sound s2 = new Sound("USA Hymne.wav");
    Sound s3 = new Sound("Nordkorea Hymne.wav");
    private Clip clip = s2.playmusik();
    private Clip clip1 = s3.playmusik();
    
    private Clip musik = s.playmusik();
    private int soundtime = 0;
    
    
    private boolean gameover = false;

    public Brett() {
        initBrett();
    }

    private void initBrett() {
        addKeyListener(new TAdapter());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);
        //Setz die Zerstörbaren Blöcke
        breakableBlock = getInnerLayout();
        //Initialisiert die Charaktere
        c1 = new Trump(xInner, yInner, breakableBlock);
        c2 = new Kim(xInner, yInner, breakableBlock);
        //Initialisiert die Sprites für die Inneren und die Äußerren Blöcke
        outerWall = loadImage(ita.bombermangame.Brett.class.getResource("sprites/mauer/mauergeil.png"));
        innerWall = loadImage(ita.bombermangame.Brett.class.getResource("sprites/mauer/pixelmauermitte.png"));
        //Methode für Zufälligen Felder
        randomBlocks();
        //Initialisiert den Timer
        timer = new Timer(100, this);
        timer.start();
        timer1 = 0;
        
        musik.start();
        
    }

    //läd Bild
    private Image loadImage(URL path) {
        ImageIcon ii = new ImageIcon(path);
        return ii.getImage();
    }

    //Zeichnet Bild
    private void drawImage(Graphics g, int x, int y, Image i) {
        g.drawImage(i, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    // Erzeugt ein Randomfeld
    private void randomBlocks() {
        Random r1 = new Random();
        //Durchläuft eine Zählschleife wo 299 mal eine Pseudozufallszahl ->(r) erstellt wird und das Feld an der Position 
        //r wird zerstört sodass ein Feld erstllt wird das Zufällig erscheint
        for (int i = 0; i < 299; i++) {
            int r = r1.nextInt(299);
            
            //if(i==r){
            breakableBlock[r].zerbrechen();
            //Unbedingt löschen bevor wir abgeben
            //Aber für Testzwecke drinlassen
           
            // } 

        }
    }

    private BreakableBlock[] getInnerLayout() {
        //int[] x = {72, 88, 104, 120, 136, 152, 168, 184, 200, 216, 232, 248, 264, 280, 296, 312, 328, 344, 360, 376, 392, 408, 424};
        //int[] y = {16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 192, 208};
        //Erstellt ein Feld wo alle Felder im Spielfeld ausgefüllt sind (wird in randomBlocks dezimiert)
        //ArrayList wird erstellt da es so leichter ist die Blöcke einzufügen 
        ArrayList<BreakableBlock> blist = new ArrayList<>();
        BreakableBlock[] b = new BreakableBlock[300];
        //Doppelte Zählschleife zum Ausfüllen der Gesammten Spielfläche 
        for (int i = 0; i < 23; i++) {
            for (int j = 0; j < 13; j++) {
                BreakableBlock block = new BreakableBlock(72 + 16 * i, 16 + j * 16);
                //Hiermit wird der Block dem Array hinzugefügt 
                blist.add(block);
            }
        }
        blist.add(new BreakableBlock(72 + 16 * 23, 16 + 13 * 16));
        //ArrayList blist wird in eine Array umgewandelt das mit BreakableBlock Objekten gefüllt ist
        b = blist.toArray(b);
        return b;
    }

    //Zeichnet alle Componenten des Spiels
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintBBlocks(g);
        paintWalls(g);
        paintChar(g);
        paintpunkte(g);
        if (timer1 == 10) {
            countdown--;
            timer1 = 0;
        } else {
            timer1++;
        }
        if(drawwindrump == true){
           // while (true) {   
                timer.stop();
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(c1.getwinnerscreen(), 0, 0,null);
                musik.stop();
                
                
                clip.start();
            //}
            
        }
        else{
            if (drawwinkim == true) {
                timer.stop();
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawImage(c2.getwinnerscreen(), 0, 0, null);
                    musik.stop();
                    
                clip1 = s3.playmusik();
                clip1.start();
                
            }
        }
        Gameover(g);
       if(drawwindrump == true || drawwinkim == true){
        Toolkit.getDefaultToolkit().sync();
    }
}

    private void paintpunkte(Graphics g) {

        punkteDrump = c1.getPunktestand();
        punkteKim = c2.getPunktestand();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        //Font erzeugen
        Font myFont = new Font("Arial", Font.BOLD, 14);

        String s = "" + punkteDrump;

        g2d.setFont(myFont); //Schriftart setzen
        g2d.drawString(s, 14, 14); //String rendern

        s = "" + punkteKim;
        g2d.drawString(s, 14, 28);
        s = "" + countdown;
        g2d.drawString(s, 14, 42);
        if (countdown == 0) {
            gameover = true;
        }

    }

//Zeichnet die Zerstörbaren Blöcke
    private void paintBBlocks(Graphics g) {
        //Überprüft im gesamten Array ob die Blöcke sichtbar sind wenn nicht werden sie nicht gezeichnet
        for (int i = 0; i < breakableBlock.length; i++) {
            if (breakableBlock[i].getVisibility() == true) {
                g.drawImage(breakableBlock[i].getSprite(), breakableBlock[i].getX() + 2, breakableBlock[i].getY(), this);
            }
            //Startpositionen der Spieler
            breakableBlock[0].zerbrechen();
            breakableBlock[1].zerbrechen();
            breakableBlock[13].zerbrechen();
            breakableBlock[298].zerbrechen();
            breakableBlock[297].zerbrechen();
            breakableBlock[298 - 13].zerbrechen();

        }
    }
// Zeichnet die Äußeren und inneren Felder der Map

    private void paintWalls(Graphics g) {
        //Zeichnet die Äußeren Mauern
        for (int i = 60; i < B_WIDTH - 16; i += 16) {
            for (int j = 0; j < B_HEIGHT - 32; j += 16) {
                if (i == 60 || j == 0 || j >= B_HEIGHT - 48 || i >= B_WIDTH - 48) {
                    drawImage(g, i, j, outerWall);
                }
            }
        }
        //Zeichnet die Innerern Mauern des Spiels
        for (int k = 0; k < 11; k++) {
            for (int l = 0; l <= 5; l++) {
                drawImage(g, 90 + 32 * k, 32 + 32 * l, innerWall);
            }
        }
    }
//Zeichnet die Charaktere und die Bomben

    private void paintChar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        System.out.println(c1.getVis());
        if (c1.getVis()) {
            c1.drawBomb(g, c1, c2);
            
            g2d.drawImage(c1.loadCharSprite(), c1.getX(), c1.getY(), this);
            if (gameover == true) {
                Gameover(g);
            }
        } else {
            if (gameover == true || c1.getVis() == false || c2.getVis() == false) {
                Gameover(g);
            }
//Gewinner Display einbinden
            while (wink) {
                c2.setPunktestand((10 * 10) * 10);
                System.out.println("Kim gewinnt");
                wink = false;
                gameover = true;
            }
        }

        if (c2.getVis()) {
            c2.drawBomb(g, c1, c2);
            g2d.drawImage(c2.loadCharSprite(), c2.getX(), c2.getY(), this);
        } else {
            //Gewinner Display einbinden
            while (wind) {

                c1.setPunktestand((10 * 10) * 10
                );
                System.out.println("Trump gewinnt");
                wind = false;
            }

        }

    }

    //Ist für den timer wichtig und zeichnet den Frame neu
    @Override
    public void actionPerformed(ActionEvent e) {

        updateChar();
        repaint();
    }

    //Bewegt die Charaktere 
    private void updateChar() {
        c1.move(breakableBlock);

        c2.move(breakableBlock);

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            c1.keyReleased(e);
            c2.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            c1.keyPressed(e);
            c2.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_M) {
                clip.stop();
            }
            if(e.getKeyCode() == KeyEvent.VK_R){
                clip.start();
            }
//            checkCollision(e);
        }
    }

    private void Gameover(Graphics g) {
        if (countdown <= 0 && c1.getPunktestand() > c2.getPunktestand()) {
           
                drawwindrump = true;
                System.out.println("Drump gewinnt");
           
        } else {
            if (countdown <= 0 && c2.getPunktestand() > c1.getPunktestand()) {
                    drawwinkim = true;
                    System.out.println("Kim gewinnt");
           
            } else {
                if (c1.getVis() == false) {
                        drawwinkim = true;
                        System.out.println("Kim gewinnt");
           
                } else {

                    if (c2.getVis() == false) {
                          drawwindrump = true;
                          System.out.println("Drump gewinnt");
           
                    }
                }
            }
        }

    }

    
}
