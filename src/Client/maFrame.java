/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Hubert
 */
public class maFrame extends JFrame implements ActionListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
    private BufferedReader br;
    private JButton bEnvoyer, bQuitter, bCiseaux, bPierre, bPapier;
    private ImageIcon iCiseaux, iPierre, iPapier, iBandeau;
    
    public maFrame (Socket ss){
        
        this.setTitle("Pierre Papier Ciseau");
        this.setPreferredSize(new Dimension(600, 600));
        this.setLocationRelativeTo(null);
        
        // Le panel du Bandeau
        
        JPanel jBandeau = new JPanel();
        this.add(jBandeau, BorderLayout.NORTH);
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("img/bandeau.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Image dimg = img.getScaledInstance(500, 230,
                Image.SCALE_SMOOTH);
        
        iBandeau = new ImageIcon(dimg);
        JLabel lBandeau = new JLabel(iBandeau);
        jBandeau.add(lBandeau);
        
        // Le panel de Choix
        
        JPanel jChoix = new JPanel();
        this.add(jChoix, BorderLayout.CENTER);
        iPapier = new ImageIcon("img/papier.png");
        iPierre = new ImageIcon("img/pierre.png");
        iCiseaux = new ImageIcon("img/ciseaux.png");
        
        bPierre = new JButton(iPierre);
        bPierre.setActionCommand("pierre");
        jChoix.add(bPierre);
        
        bPapier = new JButton(iPapier);
        bPapier.setActionCommand("papier");
        jChoix.add(bPapier);
        
        bCiseaux = new JButton(iCiseaux);
        bCiseaux.setActionCommand("ciseaux");
        jChoix.add(bCiseaux);
        
        bPierre.addActionListener(this);
        bPapier.addActionListener(this);
        bCiseaux.addActionListener(this);
        
        // Le panel permettant de quitter
        
        /*JPanel jQuitter = new JPanel();
        this.add(jQuitter, BorderLayout.SOUTH);
        jQuitter.add(bQuitter= new JButton("Quitter"));
        jQuitter.add(bEnvoyer = new JButton("Envoyer"));*/
        /*bQuitter.addActionListener(this);
        bEnvoyer.addActionListener(this);*/

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    
    //TODO Méthode pour récupérer et envoyer la valeur du radio button choisi
    public void envoyer(JSONObject obj){
        
        try {
            //En fonction du bouton, on envoie la valeur au coeur.
            obj.accumulate("Commande", "Envoyer");
            System.out.println(obj.toString());
            //out.println(obj.toString());
        } catch (JSONException e){
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
        else if (e.getSource() == bCiseaux || e.getSource() == bPapier || e.getSource() == bPierre){
        	JButton sender = (JButton) e.getSource();
        	JSONObject obj = new JSONObject();
        	try {
				obj.accumulate("button", sender.getActionCommand());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	this.envoyer(obj);
        }
    }
}
