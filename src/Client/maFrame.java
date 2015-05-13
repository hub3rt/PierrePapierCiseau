/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.xpath.internal.operations.Lte;

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
    private JButton bQuitter, bCiseaux, bPierre, bPapier;
    private ImageIcon iCiseaux, iPierre, iPapier, iBandeau;
    private JLabel lresultat;
    
    public maFrame (Socket ss){
        
		try {
			out = new PrintWriter(ss.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(ss.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
        this.setTitle("Pierre Papier Ciseau");
        this.setPreferredSize(new Dimension(620, 700));
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
        
        // Le panel Central, contenant choix, score et affichage du rÈsultat
        
        JPanel jCentre = new JPanel();
        this.add(jCentre, BorderLayout.CENTER);
        
        // Le panel de Choix
        
        JPanel jChoix = new JPanel();
        jCentre.add(jChoix, BorderLayout.NORTH);
        iPapier = new ImageIcon("img/papier.png");
        iPierre = new ImageIcon("img/pierre.png");
        iCiseaux = new ImageIcon("img/ciseaux.png");
        
        GridLayout layoutChoix = new GridLayout(1, 3);
        jChoix.setPreferredSize(new Dimension(550, 130));
        jChoix.setLayout(layoutChoix);
        
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
        
        // Le panel d'affichage du score, qui va contenir un panel d'affichage de texte "score:",
        //        un panel d'affichage de score, et les textes permettant d'identifier ‡ qui sont les scores
        
        JPanel jScore = new JPanel();
        jCentre.add(jScore, BorderLayout.CENTER);
        jScore.setPreferredSize(new Dimension(600, 220));
        
        // Le panel affichant "Score :"
        JPanel jTextScore = new JPanel();
        jScore.add(jTextScore, BorderLayout.NORTH);
        jTextScore.setPreferredSize(new Dimension(600, 20));
        JLabel lTextScore = new JLabel();
        lTextScore.setText("Score :");
        jTextScore.add(lTextScore);
        
        // Le panel affichant les Scores
        JPanel jScores = new JPanel();
        jScore.add(jScores, BorderLayout.CENTER);
        jScores.setPreferredSize(new Dimension(600, 100));
        
        // Le panel affichant le score du client
        JPanel jScoreClient = new JPanel();
        jScores.add(jScoreClient, BorderLayout.WEST);
        
        JLabel lTextScore2 = new JLabel();
        lTextScore2.setText("Score :");
        jScoreClient.setPreferredSize(new Dimension(100, 100));
        jScoreClient.setAlignmentX(CENTER_ALIGNMENT);
        jScoreClient.setBackground(Color.white);
        jScoreClient.add(lTextScore2);
        
        // Un panel d'Ècartement
        JPanel jEcartement = new JPanel();
        jScores.add(jEcartement, BorderLayout.CENTER);
        jEcartement.setPreferredSize(new Dimension(100, 100));
        
        // Le panel affichant le score de l'adversaire
        JPanel jScoreAdver = new JPanel();
        jScores.add(jScoreAdver, BorderLayout.EAST);
        
        JLabel lTextScore3 = new JLabel();
        lTextScore3.setText("Score :");
        jScoreAdver.setPreferredSize(new Dimension(100, 100));
        jScoreAdver.setAlignmentX(CENTER_ALIGNMENT);
        jScoreAdver.setBackground(Color.white);
        jScoreAdver.add(lTextScore3);
        
        // Le panel affichant les textes "vous" et "adversaire"
        
        // Le panel affichant le rÈsultat
        
        JPanel jResultat = new JPanel();
        jCentre.add(jResultat, BorderLayout.SOUTH);
        lresultat = new JLabel();
        lresultat.setText("En attente de connection d'un adversaire.");
        jResultat.add(lresultat);
        
        // Le panel permettant de quitter
        
        JPanel jQuitter = new JPanel();
        this.add(jQuitter, BorderLayout.SOUTH);
        jQuitter.add(bQuitter= new JButton("Quitter"));
        bQuitter.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    
    //TODO M√©thode pour r√©cup√©rer et envoyer la valeur du radio button choisi
    public void envoyer(JSONObject obj){
        
        try {
            //En fonction du bouton, on envoie la valeur au coeur.
            obj.accumulate("Commande", "Jouer");
            System.out.println(obj.toString());
            out.println(obj.toString());
        } catch (JSONException e){
        	System.out.println("Probl√®me lors de l'envoi : " + e.getMessage());
            
        }
    }
    
    public void quitter(){
        
        try {
            JSONObject obj = new JSONObject();
            obj.accumulate("Commande", "Quitter");
            out.println(obj.toString());
        } catch (JSONException e) {
            System.out.println("Probl√®me lors de la fermeture : " + e.getMessage());
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
				obj.accumulate("valeur", sender.getActionCommand());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	this.envoyer(obj);
        }
    }
}
