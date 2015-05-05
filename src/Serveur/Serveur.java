/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serveur;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hubert
 */
public class Serveur {


    public Serveur(int portNumber) {

        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Coucou, ici le serveur.");
            while (true){
                Socket s = ss.accept();
                new Thread(new ServeurThread(s)).start();
                System.out.println("Un client est connecté sur le socket "+s);
            }
        } catch (IOException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Serveur s = new Serveur(1313);
    }
}
