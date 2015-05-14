/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serveur;

import java.io.*;
import java.net.*;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 *
 * @author Hubert
 */
public class ServeurThread implements Runnable {

    private Socket so, socketManager;
    private BufferedReader in, inManager;
    private PrintWriter out, outManager;
    private JSONObject inObj;
    
    public boolean isAlive = true;
    
    public ServeurThread(Socket s) {
        so = s;
        try {
			out = new PrintWriter(so.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(so.getInputStream()));
	        System.out.println("Coeur créé sur le port : "+so.getPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public Socket getSo() {
        return so;
    }
    
    public void createSocket(int port){
        try {
            socketManager = new Socket("localhost", port);
            inManager = new BufferedReader(new InputStreamReader(socketManager.getInputStream()));
            outManager = new PrintWriter(socketManager.getOutputStream(), true);
            System.out.println("Socket créé avec le manager");
        } catch (Exception e) {
            System.out.println("Problème lors de la création du socket manager : " + e.getMessage());
        }
    }
    
    @Override
    public void run() {
    	try {
    		while (true) {
				inObj = new JSONObject(in.readLine());
                if (inObj.get("Commande").equals("Jouer")){
                	if (inObj.get("valeur").equals("pierre")){
                        System.out.println("Le joueur a joué pierre.");
                	} else if (inObj.get("valeur").equals("papier")){
                        System.out.println("Le joueur a joué papier.");
                    } else if (inObj.get("valeur").equals("ciseaux")){
                        System.out.println("Le joueur a joué ciseaux.");
                    }
                } else if (inObj.get("Commande").equals("Quitter")){
                    System.out.println("Le joueur a quitté la partie.");
                    isAlive = false;
                    break;
                }
            }
	        System.out.println("Coeur fermé sur le port : " + so.getPort());
	        so.close();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
