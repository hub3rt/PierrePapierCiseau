/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Hubert
 */
public class ServerManager {
    
    private ServeurThread st1, st2;
    private PrintWriter out1, out2 ;
    private BufferedReader in1, in2 ;
    private Socket so1, so2;
    
    public ServerManager (ServeurThread st1, ServeurThread st2, int port1, int port2){
        
        this.st1 = st1;
        this.st2 = st2;
        try {
            
            st1.createSocket(port1);
            st2.createSocket(port2);
            
            so1 = new Socket("localhost", port1);
            so2 = new Socket("localhost", port2);
            
            in1 = new BufferedReader(new InputStreamReader(so1.getInputStream()));
            out1 = new PrintWriter(st1.getSo().getOutputStream(), true);
            
            in2 = new BufferedReader(new InputStreamReader(so2.getInputStream()));
            out2 = new PrintWriter(st2.getSo().getOutputStream(), true);
            
            while (true){
                
                JSONObject inObj1 = new JSONObject(in1.readLine());
                JSONObject inObj2 = new JSONObject(in2.readLine());

                if (inObj1.get("État").equals("non joué") == false && inObj2.get("État").equals("non joué") == false){
                   
                } else if (inObj1.get("État").equals("non joué")){
                    
                } else if (inObj2.get("État").equals("non joué")){
                    
                } else {
                    
                }
            }
            
        } catch (Exception e) {
            System.out.println("Problème au lancement du Manager : " + e.getMessage());
        }
    }
    
    
    
}
