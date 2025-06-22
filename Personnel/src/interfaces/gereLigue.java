package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class gereLigue {

	public gereLigue() {
		 JFrame frame = new JFrame("Gérer les Ligues");
	        frame.setSize(400, 300);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setLayout(null);

	        JLabel labelTitre = new JLabel("Gérer les Ligues");
	        labelTitre.setBounds(130, 20, 200, 30);
	        frame.add(labelTitre);

	        // Bouton Afficher les Ligues 
	        JButton btnAfficherLigues = new JButton("Afficher les ligues");
	        btnAfficherLigues.setBounds(100, 60, 200, 30);
	        frame.add(btnAfficherLigues);

	        // Bouton Ajouter une Ligue 
	        JButton btnAjouterLigue = new JButton("Ajouter une ligue");
	        btnAjouterLigue.setBounds(100, 100, 200, 30);
	        frame.add(btnAjouterLigue);

	        // Bouton Sélectionner une Ligue 
	        JButton btnSelectionnerLigue = new JButton("Sélectionner une ligue");
	        btnSelectionnerLigue.setBounds(100, 140, 200, 30);
	        frame.add(btnSelectionnerLigue);

	        // Bouton Back (q)
	        JButton btnBack = new JButton("Retour");
	        btnBack.setBounds(100, 180, 200, 30);
	        frame.add(btnBack);

	        // Actions
	        btnAfficherLigues.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // affichage des ligues
	            		new afficherLigues();
	            }
	        });

	        btnAjouterLigue.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // ajout d'une ligue
	                new ajouterLigues();
	            }
	        });

	        btnSelectionnerLigue.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                //sélection d'une ligue
	            	new SelectionnerLigues();
	            }
	        });
	        btnBack.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                frame.dispose(); // ferme cette fenêtre
	            }
	        });

	        frame.setVisible(true);
	    }
	
	}

