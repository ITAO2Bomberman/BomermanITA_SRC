/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ita.bombermangame;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author Olenberg, Heinrich, Musiolik, Szymczak 
 */
public class App extends JFrame {
// Initialiesiert den JFramw

    public App() {
        initApp();
    }
//Fügt neues Spielfeld hinzu

    private void initApp() {
        add(new Brett());
//Setzt Größe fest
        setSize(480, 272);
        

        setTitle("President War");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Größe nicht Variable
        setResizable(false);
    }

    public static void main(String[] args) {
// Startet die Anwendung
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                App app = new App();
                app.setVisible(true);
            }
        });
    }
}
