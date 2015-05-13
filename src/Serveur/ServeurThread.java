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
import org.json.JSONObject;
/**
 * 
 *
 * @author Hubert
 */
public class ServeurThread implements Runnable {

    private Socket so;
    private BufferedReader in;
    private PrintWriter out;
    private JSONObject inObj;
    
    public ServeurThread(Socket s) {
        
        so = s;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(so.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(so.getInputStream()));
        while (true) {
            // TODO récupérer la valeur du radio button JSON ?
            inObj = new JSONObject(in.readLine());
            System.out.println("Coeur créé sur le port : "+so.getPort());
                if (inObj.get("Commande").equals("Pierre")){
                    System.out.println("Le joueur a joué pierre.");

                } else if (inObj.get("Commande").equals("papier")){
                    System.out.println("Le joueur a joué papier.");

                } else if (inObj.get("Commande").equals("ciseaux")){
                    System.out.println("Le joueur a joué ciseaux.");

                } else if (inObj.get("Commande").equals("Quitter")){
                    System.out.println("Le joueur a quitté la partie.");
                    break;
                } else { 
                    break;
                }
            }
        
        System.out.println("Coeur fermé sur le port : " + so.getPort());
        so.close();
        
        } catch (Exception e){
            System.out.println("Petit problème au lancement du Thread : "+ e.getMessage());
        }
    }
}