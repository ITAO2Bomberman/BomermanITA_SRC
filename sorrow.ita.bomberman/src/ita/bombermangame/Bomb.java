/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ita.bombermangame;

import java.awt.Image;
import java.net.URL;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
/**
 *
 * @author Olenberg, Heinrich, Musiolik, Szymczak
 */
public class Bomb {
    private Image bombSprite;
    private final int x,y;
    private int time=0;
    private boolean vis=true;
    private boolean bvis=false;
    private Image exSprite;
    // Setzt die Kooardinaten der Bombe, den Sprite der Bombe und den der Explosion
    public Bomb(URL explodeicon, URL bombURL,int x, int y){
        this.x=x;
        this.y=y;
        bombSprite=new ImageIcon(bombURL).getImage();
        exSprite = new ImageIcon(explodeicon).getImage();
        //Skaliert den Explosionssprite
        exSprite=exSprite.getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING);
    }
    
    public Image getBombSprite(){
        return bombSprite;
    }
    
    public Image getexplodesprite(){
        return exSprite;
    }
    // Leitet Explosion ein
    public int explode(BreakableBlock[] b, Charakter c1, Charakter c2){
        int q = 0;
        // Wenn die Timer Int Variable bis 10 oder darüber hinaus gezählt hat
        if(time >= 10){
            // Sichtbarkeiet der Bombe wird ausgeblendet
            vis=false;
            // for_Schleife durchläuft die zerstörbaren Blöcke und überprüft ob es zu einer Kollison zwischen dem Explosionsradius und den Zerstörbaren blöcken kommt
            for (BreakableBlock b1 : b) {
                if (x+16 == b1.getX() && y == b1.getY() || y+16 == b1.getY() && x == b1.getX() || x-16 == b1.getX() && y == b1.getY() || y-16 == b1.getY() && x == b1.getX()) {
                    if (b1.getVisibility() == true) {
                        q++;
                    }
                    // Zerstörungsmethode wird ausgeführt
                    b1.zerbrechen();
                }
                if(x+16 == c1.getX() && y == c1.getY() || x-16 == c1.getX() && y == c1.getY() || x == c1.getX() && y+16 == c1.getY() || x == c1.getX() && y-16 == c1.getY()|| x == c1.getX() && y == c1.getY()){
                    
                    c1.setVis(false);
                   
                }
                if(x+16 == c2.getX() && y == c2.getY() || x-16 == c2.getX() && y == c2.getY() || x == c2.getX() && y+16 == c2.getY() || x == c2.getX() && y-16 == c2.getY()|| x == c2.getX() && y == c2.getY()){
                   
                    c2.setVis(false);
                   
                }
            }
             
           
            
        }else{
            //Wenn der Timer noch nicht abgelaufen ist wird die Timer Variable hochgezählt
            time++;
        }
        //Dannach wird überprüft ob die Bombe noch sichtbar ist wenn nicht wird die Explosionsanzeigemethode ausgeführt
        if(vis==false){
        seeexplodeSprite();
        }
        
        return (q*q)*10;
        }
    

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public boolean getVis(){
        return vis;
    }

    public boolean getBvis() {
        return bvis;
    }
//Explosionsanzeigemethode
    public void seeexplodeSprite(){
        //Boolische Variable zur einzeige der explosionen
        bvis = true;
        Sound s = new Sound("Explosion.wav");
        Clip clip = s.playmusik();
        clip.start();
//Wenn die Timervariable den Wert 13 erreicht oder Überschritten hat soll die Explosion nicht angezeigt werden
        if (time >= 15) {
            bvis = false;
        }
        else{
        System.out.println(time);
        //Wenn das nicht der Fall ist wird die Timervariable hochgezählt
        time++;
        }
        
        
    } 
}
