/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.net.Socket;

/**
 *
 * @author Hubert
 */
public class Client {
    
    public Client (String ip, int port){
        try {
                maFrame frame = new maFrame(new Socket(ip, port));
                frame.show();
            }
        catch (Exception e) {
                System.out.println("Problème client : "+e.getMessage());}
    }
    
    public static void main(String[] args) {
        new Client("localhost", 1313);
    }
}