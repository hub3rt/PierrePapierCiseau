/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Hubert
 */
public class maFrame extends JFrame implements ActionListener {
    
    private PrintWriter out;
    private BufferedReader br;
    private JButton bEnvoyer, bQuitter, bCiseaux, bPierre, bPapier;
    private ImageIcon iCiseaux, iPierre, iPapier;
    
    public maFrame (Socket ss){
        
        this.setTitle("Pierre Papier Ciseaux");
        this.setSize(550,350);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        JPanel jChoix = new JPanel();
        this.add(jChoix, BorderLayout.CENTER);
        
        iPierre = new ImageIcon(this.getClass().getResource("img/pierre.png" ));
        bPierre = new JButton("pierre", iPierre); 
        jChoix.add(bPierre);
        
        iPapier = new ImageIcon(this.getClass().getResource("img/papier.png" ));
        bPapier = new JButton("papier", iPapier); 
        jChoix.add(bPapier);
        
        iCiseaux = new ImageIcon(this.getClass().getResource("img/ciseaux.png" ));
        bCiseaux = new JButton("ciseaux", iCiseaux); 
        jChoix.add(bCiseaux);
        
        JPanel jPanelButton = new JPanel();
        this.add(jPanelButton, BorderLayout.SOUTH);
        jPanelButton.add(bQuitter= new JButton("Quitter"));
        jPanelButton.add(bEnvoyer = new JButton("Envoyer"));
        bQuitter.addActionListener(this);
        bEnvoyer.addActionListener(this);
        
    }

    
    //TODO Méthode pour récupérer et envoyer la valeur du radio button choisi
    public void pierre(){
        
        try {
            JSONObject obj = new JSONObject();
            obj.accumulate("Commande", "pierre");
            out.println(obj.toString());
            System.out.println("Le joueur joue pierre.");
            
        } catch (JSONException e) {
            System.out.println("Problème lors de l'envoi : " + e.getMessage());
        }
    }
    
    public void papier(){
        
        try {
            JSONObject obj = new JSONObject();
            obj.accumulate("Commande", "papier");
            out.println(obj.toString());
            System.out.println("Le joueur joue papier.");
            
        } catch (JSONException e) {
            System.out.println("Problème lors de l'envoi : " + e.getMessage());
        }
    }
    
    public void ciseaux(){
        
        try {
            JSONObject obj = new JSONObject();
            obj.accumulate("Commande", "ciseaux");
            out.println(obj.toString());
            System.out.println("Le joueur joue ciseaux.");
            
        } catch (JSONException e) {
            System.out.println("Problème lors de l'envoi : " + e.getMessage());
        }
    }
    
    public void quitter(){
        
        try {
            JSONObject obj = new JSONObject();
            obj.accumulate("Commande", "Quitter");
            out.println(obj.toString());
        } catch (JSONException e) {
            System.out.println("Problème lors de la fermeture : " + e.getMessage());
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == bQuitter){
            System.out.println("Fermeture du client et extinction du coeur.");
            this.quitter();
            System.exit(0);
	} else if (e.getSource() == bPierre){
            this.pierre();
        } else if (e.getSource() == bPapier){
            this.papier();
        } else if (e.getSource() == bCiseaux){
            this.ciseaux();
        }
    }
}
