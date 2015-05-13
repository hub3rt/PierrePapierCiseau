/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Hubert
 */
public class Client {
    
    private BufferedReader in;
    private PrintWriter out;
    
    public Client (String ip, int port){
        try {
                maFrame frame = new maFrame(new Socket(ip, port));
            }
        catch (Exception e) {
                System.out.println("Problème client : "+e.getMessage());}
    }
    
    public static void main(String[] args) {
        new Client("localhost", 1313);
    }
}