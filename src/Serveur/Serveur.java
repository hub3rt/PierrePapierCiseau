/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serveur;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hubert
 */
public class Serveur {
        

    public Serveur(int portNumber) {
        
        int compteur = 10000 ;
        ArrayList<ServeurThread> lesJoueurs = new ArrayList<ServeurThread>();

        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Coucou, ici le serveur.");
            while (true){
                
                Socket s = ss.accept();
                ServeurThread st = new ServeurThread(s);
                new Thread(st).start();
                System.out.println("Un client est connecté sur le socket "+s);
                lesJoueurs.add(st);
                if (lesJoueurs.size()==2){
                   
                   ServerManager SM = new ServerManager(lesJoueurs.get(0), lesJoueurs.get(1), compteur, compteur +1);
                   lesJoueurs.clear();
                   compteur += 2;
                   if (compteur == 10100){
                       break;
                   }
                }
            } 
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Serveur s = new Serveur(1313);
    }
}
