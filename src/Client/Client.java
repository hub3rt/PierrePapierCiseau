/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONObject;

/**
 *
 * @author Hubert
 */
public class Client {
    private BufferedReader br;
    public Client (String ip, int port){
        try {
//        		Socket sok = new Socket(ip, 1234);
//    			br = new BufferedReader(new InputStreamReader(sok.getInputStream()));
    			
//    			JSONObject inObj = new JSONObject(br.readLine());
//    			
//    			if (inObj.get("isFree").equals("ok")){
            		Socket ss = new Socket(ip, port);
                    maFrame frame = new maFrame(ss);
                    frame.setVisible(true);
//    			}
//    			sok.close();
            }
        catch (Exception e) {
                System.out.println("Problème client : "+e.getMessage());}
    }
    
    public static void main(String[] args) {
        new Client("localhost", 1313);
    }
}