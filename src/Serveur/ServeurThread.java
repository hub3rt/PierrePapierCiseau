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
 *
 * @author Hubert
 */
public class ServeurThread implements Runnable {

    private Socket s;
    private BufferedReader in;
    private PrintWriter out;
    private String input;
    
    public ServeurThread(Socket s) {
        
        this.s = s;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        while (true) {
            // TODO récupérer la valeur du radio button JSON ?
            input = in.readLine();
        }
        } catch (Exception e){
            System.out.println("Petit problème au lancement du Thread : "+ e.getMessage());
        }
    }
}