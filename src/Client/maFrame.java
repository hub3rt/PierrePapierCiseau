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
    private JRadioButton rbCiseau, rbPapier, rbPierre;
    private JButton bEnvoyer, bQuitter;
    private ButtonGroup BR;
    
    public maFrame (Socket ss){
        
        this.setTitle("Pierre Papier Ciseau");
        this.setSize(450,250);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        JPanel jChoix = new JPanel();
        this.add(jChoix, BorderLayout.CENTER);
        
        jChoix.add(rbCiseau = new JRadioButton("Ciseau"));
        jChoix.add(rbPapier = new JRadioButton("Papier"));
        jChoix.add(rbPierre = new JRadioButton("Pierre"));
        BR.add(rbCiseau);
        BR.add(rbPapier);
        BR.add(rbPierre);
        rbCiseau.setEnabled(true);
        
        JPanel jButton = new JPanel();
        this.add(jButton, BorderLayout.SOUTH);
        jButton.add(bQuitter= new JButton("Quitter"));
        jButton.add(bEnvoyer = new JButton("Envoyer"));
        bQuitter.addActionListener(this);
        bEnvoyer.addActionListener(this);
        
    }

    
    //TODO Méthode pour récupérer et envoyer la valeur du radio button choisi
    public void envoyer(){
        
        try {
            JSONObject obj = new JSONObject();
            JSONObject objVal = new JSONObject();
            obj.accumulate("Commande", "Envoyer");
            
            //En fonction du bouton radio sélectionné, on envoie la valeur au coeur.
            if (rbCiseau.isEnabled())
                objVal.accumulate("Valeur", "Ciseau");
            else if (rbPapier.isEnabled())
                objVal.accumulate("Valeur", "Papier");
            else
                objVal.accumulate("Valeur", "Pierre");
            out.println(obj.toString());
            out.println(objVal.toString());
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
		}
    }
    
}
