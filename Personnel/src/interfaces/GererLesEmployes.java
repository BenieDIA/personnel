package interfaces;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import personnel.Ligue;

public class GererLesEmployes {

	public GererLesEmployes(Ligue ligue) {
		  JFrame frame = new JFrame("Gérer les employés de " + ligue.getNom());
		    frame.setSize(400, 250);
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    frame.setLayout(null);

		    JLabel label = new JLabel("Gestion des employés");
		    label.setBounds(120, 20, 200, 25);
		    frame.add(label);

		    JButton btnLister = new JButton("l : Afficher les employés");
		    btnLister.setBounds(80, 60, 220, 30);
		    frame.add(btnLister);

		    JButton btnAjouter = new JButton("a : Ajouter un employé");
		    btnAjouter.setBounds(80, 100, 220, 30);
		    frame.add(btnAjouter);

		    JButton btnSelectionner = new JButton("m : Sélectionner un employé");
		    btnSelectionner.setBounds(80, 140, 220, 30);
		    frame.add(btnSelectionner);

		    JButton btnFermer = new JButton("Fermer");
		    btnFermer.setBounds(140, 180, 100, 30);
		    frame.add(btnFermer);

		    // Action pour fermer
		    btnFermer.addActionListener(e -> frame.dispose());

		    // Action pour lister les employés
			btnLister.addActionListener(e -> {
				// Logique pour afficher les employés de la ligue
				new AfficherEmployes(ligue);
			});
			            // Action pour ajouter un employé
			btnAjouter.addActionListener(e -> {
				//  ajouter un employé à la ligue
                new AjouterEmploye(ligue);
			});
			
			// Action pour sélectionner un employé
			btnSelectionner.addActionListener(e -> {
				// Logique pour sélectionner un employé de la ligue
                new SelectionnerEmploye(ligue);
            });

            frame.setLocationRelativeTo(null); // Centrer la fenêtre
            frame.setResizable(false); // Empêcher le redimensionnement de la fenêtre	    
            frame.setVisible(true);
			}
	
	}

