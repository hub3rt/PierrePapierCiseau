/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serveur;

import java.io.*;
import java.net.*;
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
    
    public boolean isAlive = true;
    
    public ServeurThread(Socket s) {
        
        so = s;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(so.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(so.getInputStream()));
            System.out.println("Coeur cr�� sur le port : "+so.getPort());
            while (true) {
            inObj = new JSONObject(in.readLine());
                if (inObj.get("Commande").equals("Jouer")){
                	if (inObj.get("valeur").equals("pierre")){
                        System.out.println("Le joueur a jou� pierre.");
                	} else if (inObj.get("valeur").equals("papier")){
                        System.out.println("Le joueur a jou� papier.");
                    } else if (inObj.get("valeur").equals("ciseaux")){
                        System.out.println("Le joueur a jou� ciseaux.");
                    }
                } else if (inObj.get("Commande").equals("Quitter")){
                    System.out.println("Le joueur a quitt� la partie.");
                    isAlive = false;
                    break;
                }
            }
        
        System.out.println("Coeur ferm� sur le port : " + so.getPort());
        so.close();
        
        } catch (Exception e){
            System.out.println("Petit probl�me au lancement du Thread : "+ e.getMessage());
        }
    }
}