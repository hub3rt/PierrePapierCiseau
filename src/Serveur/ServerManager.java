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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Hubert
 */
public class ServerManager implements Runnable {
    
    private PrintWriter out1, out2 ;
    private BufferedReader in1, in2 ;
    private Socket so1, so2;
    private ServerSocket ss;
    
    public ServerManager (ServeurThread st1, ServeurThread st2, int port1, int port2){
        
        try {
            ss = new ServerSocket(port1);
            st1.createSocket(port1);
            so1 = ss.accept();
            ss = new ServerSocket(port2);
            st2.createSocket(port2);
            so2 = ss.accept();
            
            in1 = new BufferedReader(new InputStreamReader(so1.getInputStream()));
            out1 = new PrintWriter(so1.getOutputStream(), true);
            
            in2 = new BufferedReader(new InputStreamReader(so2.getInputStream()));
            out2 = new PrintWriter(so2.getOutputStream(), true);
            
            System.out.println("Connexions établies");
            
        } catch (Exception e) {
            System.out.println("Problème au lancement du Manager : " + e.getMessage());
        }
    }
    
    @Override
    public void run(){
    	System.out.println("socket1 : " + so1.getPort() + ", localPort : " + so1.getLocalPort());
    	System.out.println("socket2 : " + so2.getPort() + ", localPort : " + so2.getLocalPort());
        
		try {
		    while (true){
		       	JSONObject inObj1 = new JSONObject(in1.readLine());
	            JSONObject inObj2 = new JSONObject(in2.readLine());

	            if (inObj1.get("Etat").equals("NJ") == false && inObj2.get("Etat").equals("NJ") == false){
	            	if (inObj1.get("Etat").equals("Quitter") && inObj2.get("Etat").equals("Quitter")){
	            		break;
	            	}
	                if (inObj1.get("Etat").equals("Pierre")){
	                	System.out.println("1 - Pierre");
	                	
	                	if (inObj2.get("Etat").equals("Quitter")){
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "A");
		                	out1.println(outObj1.toString());
		                } else if (inObj2.get("Etat").equals("Pierre")){
		                	System.out.println("2 - Pierre");

		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "E");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "E");
		                	out2.println(outObj2.toString());
		                	
		                } else if (inObj2.get("Etat").equals("Papier")){
		                	System.out.println("2 - Papier");
		                	
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "L");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "W");
		                	out2.println(outObj2.toString());
		                	
		                } else if (inObj2.get("Etat").equals("Ciseaux")){
		                	System.out.println("2 - Ciseaux");
		                	
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "W");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "L");
		                	out2.println(outObj2.toString());
		                	
		                }
	                	
	                } else if (inObj1.get("Etat").equals("Papier")) {
	                	System.out.println("1 - Papier");
	                	
	                	if (inObj2.get("Etat").equals("Quitter")){
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "A");
		                	out1.println(outObj1.toString());
		                } else if (inObj2.get("Etat").equals("Pierre")){
		                	System.out.println("2 - Pierre");
		                	
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "W");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "L");
		                	out2.println(outObj2.toString());
		                	
		                } else if (inObj2.get("Etat").equals("Papier")){
		                	System.out.println("2 - Papier");
		                	
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "E");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "E");
		                	out2.println(outObj2.toString());
		                	
		                } else if (inObj2.get("Etat").equals("Ciseaux")){
		                	System.out.println("2 - Ciseaux");
		                	
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "L");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "W");
		                	out2.println(outObj2.toString());
		                	
		                }
	                	
	                } else if (inObj1.get("Etat").equals("Ciseaux")){
	                	System.out.println("1 - Ciseaux");
	                	
	                	if (inObj2.get("Etat").equals("Quitter")){
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "A");
		                	out1.println(outObj1.toString());
		                } else if (inObj2.get("Etat").equals("Pierre")){
		                	System.out.println("2 - Pierre");
		                	
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "L");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "W");
		                	out2.println(outObj2.toString());
		                	
		                } else if (inObj2.get("Etat").equals("Papier")){
		                	System.out.println("2 - Papier");
		                	
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "W");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "L");
		                	out2.println(outObj2.toString());
		                	
		                } else if (inObj2.get("Etat").equals("Ciseaux")){
		                	System.out.println("2 - Ciseaux");
		                	
		                	JSONObject outObj1 = new JSONObject();
		                	outObj1.accumulate("Resultat", "E");
		                	out1.println(outObj1.toString());
		                	
		                	JSONObject outObj2 = new JSONObject();
		                	outObj2.accumulate("Resultat", "E");
		                	out2.println(outObj2.toString());
		                	
		                }
	                	
	                } else if (inObj1.get("Etat").equals("Quitter")){
	                	JSONObject outObj2 = new JSONObject();
	                	outObj2.accumulate("Resultat", "A");
	                	out2.println(outObj2.toString());
	                }
	            }else {
	                System.out.println("en attente des mouvements");
	            }
	        }
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
