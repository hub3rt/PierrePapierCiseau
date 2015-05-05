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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Hubert
 */
public class maFrame extends JFrame implements ActionListener {
    
    private PrintWriter out;
    private BufferedReader br;
    private JRadioButton rbCiseau, rbPapier, rbPierre;
    private JButton bEnvoyer, bQuitter;
    
    public maFrame (Socket ss){
        
        this.setTitle("Pierre Papier Ciseau");
        this.setSize(450,250);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
        
        JPanel jChoix = new JPanel();
        this.add(jChoix, BorderLayout.CENTER);
        
        jChoix.add(rbCiseau = new JRadioButton("Ciseau"));
        jChoix.add(rbPapier = new JRadioButton("Papier"));
        jChoix.add(rbPierre = new JRadioButton("Pierre"));
        
        
        JPanel jButton = new JPanel();
        this.add(jButton, BorderLayout.SOUTH);
        jButton.add(bQuitter= new JButton("Quitter"));
        jButton.add(bEnvoyer = new JButton("Envoyer"));
        bQuitter.addActionListener(this);
        bEnvoyer.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
