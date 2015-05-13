/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serveur;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hubert
 */
public class Serveur {
	ArrayList<ServeurThread> threadsList;
	PrintWriter out;

    public Serveur(int portNumber) {
    	
    	threadsList = new ArrayList<ServeurThread>();

        try {
        	
//        	Socket sok = new Socket("localhost", 1234);
//        	out = new PrintWriter(sok.getOutputStream(), true);
        	
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Coucou, ici le serveur.");
            while (true){
                    Socket s = ss.accept();
                    ServeurThread coeur = new ServeurThread(s);
                    threadsList.add(coeur);
                    new Thread(coeur).start();
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
