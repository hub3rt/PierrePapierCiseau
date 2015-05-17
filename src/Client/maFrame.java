/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Hubert
 */
public class maFrame extends JFrame implements ActionListener, MouseMotionListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
    private BufferedReader br;
    private JButton bQuitter, bCiseaux, bPierre, bPapier;
    private ImageIcon iCiseaux, iPierre, iPapier, iBandeau;
    private JLabel lresultat, lScoreClient, lScoreAdver;
    private JSONObject in;
    private int scoreClient, scoreAdver;
    private boolean abandon = false;
    
    private Color bckColor, txtColor;
    
    public maFrame (Socket ss){
        
    	scoreClient = 0;
    	scoreAdver = 0;
    	
    	bckColor = Color.DARK_GRAY;
    	
    	txtColor = Color.LIGHT_GRAY;
    	
		try {
			out = new PrintWriter(ss.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(ss.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
        this.setTitle("Pierre Papier Ciseau");
        this.setPreferredSize(new Dimension(620, 660));
        this.setMinimumSize(new Dimension(620, 660));
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        addMouseMotionListener(this);
        
        // Le panel du Bandeau
        
        JPanel jBandeau = new JPanel();
        this.add(jBandeau, BorderLayout.NORTH);
        
        jBandeau.setBackground(bckColor);
        
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
        
        // Le panel Central, contenant choix, score et affichage du resultat
        
        JPanel jCentre = new JPanel();
        this.add(jCentre, BorderLayout.CENTER);

        jCentre.setBackground(bckColor);
        
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
        
        this.desactiveBouton();
        
        // Le panel d'affichage du score, qui va contenir un panel d'affichage de texte "score:",
        //        un panel d'affichage de score, et les textes permettant d'identifier é qui sont les scores
        
        JPanel jScore = new JPanel();
        
        jScore.setBackground(bckColor);
        
        jCentre.add(jScore, BorderLayout.CENTER);
        jScore.setPreferredSize(new Dimension(600, 170));
        
        // Le panel affichant "Score :"
        JPanel jTextScore = new JPanel();
        
        jTextScore.setBackground(bckColor);
        
        jScore.add(jTextScore, BorderLayout.NORTH);
        jTextScore.setPreferredSize(new Dimension(600, 20));
        JLabel lTextScore = new JLabel();
        lTextScore.setText("Score :");
        lTextScore.setForeground(txtColor);
        jTextScore.add(lTextScore);
        
        // Le panel affichant les Scores
        JPanel jScores = new JPanel();
        
        jScores.setBackground(bckColor);
        
        jScore.add(jScores, BorderLayout.CENTER);
        jScores.setPreferredSize(new Dimension(600, 100));
        
        // Le panel affichant le score du client
        JPanel jScoreClient = new JPanel();
        jScores.add(jScoreClient, BorderLayout.WEST);
        
        lScoreClient = new JLabel();
        lScoreClient.setText("0");
        lScoreClient.setFont(new Font("Sans-Serif", Font.PLAIN, 60));
        jScoreClient.setPreferredSize(new Dimension(100, 100));
        jScoreClient.setAlignmentX(CENTER_ALIGNMENT);
        jScoreClient.setBackground(Color.white);
        jScoreClient.add(lScoreClient);
        
        // Un panel d'ecartement
        JPanel jEcartement = new JPanel();
        
        jEcartement.setBackground(bckColor);
        
        jScores.add(jEcartement, BorderLayout.CENTER);
        jEcartement.setPreferredSize(new Dimension(100, 100));
        
        // Le panel affichant le score de l'adversaire
        JPanel jScoreAdver = new JPanel();
        jScores.add(jScoreAdver, BorderLayout.EAST);
        
        lScoreAdver = new JLabel();
        lScoreAdver.setText("0");
        lScoreAdver.setFont(new Font("Sans-Serif", Font.PLAIN, 60));
        jScoreAdver.setPreferredSize(new Dimension(100, 100));
        jScoreAdver.setAlignmentX(CENTER_ALIGNMENT);
        jScoreAdver.setBackground(Color.white);
        jScoreAdver.add(lScoreAdver);
        
        // Le panel affichant les textes "vous" et "adversaire"
        JPanel jTextesIdent = new JPanel();
        
        jTextesIdent.setBackground(bckColor);
        
        jScore.add(jTextesIdent, BorderLayout.CENTER);
        jTextesIdent.setPreferredSize(new Dimension(600, 30));
        
        // Le panel affichant "vous"
        JPanel jTextClient = new JPanel();
        jTextClient.setPreferredSize(new Dimension(100, 30));
        jTextClient.setBackground(Color.green);
        jTextesIdent.add(jTextClient);
        JLabel lTextClient = new JLabel();
        lTextClient.setText("Vous");
        jTextClient.add(lTextClient);
        
        // Un panel d'ecartement
        JPanel jTextEcartement = new JPanel();
        
        jTextEcartement.setBackground(bckColor);
        
        jTextEcartement.setPreferredSize(new Dimension(100, 30));
        jTextesIdent.add(jTextEcartement);
        
        // Le panel affichant "adversaire"
        JPanel jTextAdver = new JPanel();
        jTextAdver.setPreferredSize(new Dimension(100, 30));
        jTextAdver.setBackground(Color.yellow);
        jTextesIdent.add(jTextAdver);
        JLabel lTextAdver = new JLabel();
        lTextAdver.setText("Adversaire");
        jTextAdver.add(lTextAdver);
        
        // Le panel affichant le resultat
        
        JPanel jResultat = new JPanel();

        jResultat.setBackground(bckColor);
        
        jCentre.add(jResultat, BorderLayout.SOUTH);
        lresultat = new JLabel();
        lresultat.setText("En attente de connection d'un adversaire.");
        lresultat.setForeground(txtColor);
        jResultat.add(lresultat);
        
        // Le panel permettant de quitter
        
        JPanel jQuitter = new JPanel();
        this.add(jQuitter, BorderLayout.SOUTH);
        
        jQuitter.setBackground(bckColor);
        
        jQuitter.add(bQuitter= new JButton("Quitter"));
        bQuitter.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        
		try {
                        in = new JSONObject(br.readLine());
                        lresultat.setText("Adversaire connecté, veuillez choisir un mouvement.");
                        
                        bCiseaux.setEnabled(true);
                        bPierre.setEnabled(true);
                        bPapier.setEnabled(true);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    //TODO Methode pour recuperer et envoyer la valeur du radio button choisi
    public void envoyer(JSONObject obj){
        
        try {
            //En fonction du bouton, on envoie la valeur au coeur.
            obj.accumulate("Commande", "Jouer");
            out.println(obj.toString());
            try {
				in = new JSONObject(br.readLine());
				System.out.println(in);
				
				String reponse = "";
				
				if (in.get("Resultat").equals("W")){
					scoreClient += 1;
					reponse = "Manche gagnée, ";
				} else if (in.get("Resultat").equals("L")){
					scoreAdver += 1;
					reponse = "Manche perdue, ";
				} else if (in.get("Resultat").equals("E")){
					reponse = "Egalité, ";
				} else if (in.get("Resultat").equals("A")){
					reponse = "Votre adversaire a abandonné, vous avez gagné";
                                        this.desactiveBouton();
					abandon = true;
				}
				
				if (!abandon){
					if (scoreClient == 5){
						reponse = "Bien joué, vous avez gagné!";
                                                this.desactiveBouton();
					} else if (scoreAdver == 5){
						reponse = "Vous avez perdu, abruti!";
                                                this.desactiveBouton();
					} else {
						reponse += "veuillez choisir un mouvement.";
					}
				}

		        lScoreClient.setText(Integer.toString(scoreClient));
		        lScoreAdver.setText(Integer.toString(scoreAdver));
		        lresultat.setText(reponse);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
    
    public void desactiveBouton(){
        bPierre.setEnabled(false);
        bCiseaux.setEnabled(false);
        bPapier.setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == bQuitter){
			System.out.println("Fermeture du client et extinction du coeur.");
			this.quitter();
			System.exit(0);
		}
        else if (e.getSource() == bCiseaux || e.getSource() == bPapier || e.getSource() == bPierre){
        	if (scoreClient < 5 && scoreAdver < 5){
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

    @Override
    public void mouseDragged(MouseEvent e) {
        setLocation(getLocation().x+e.getX(), getLocation().y+e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}
