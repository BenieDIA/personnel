package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import personnel.Ligue;

public class EditerLigue {

	EditerLigue(Ligue ligue){
		  JFrame frame = new JFrame("Editer " + ligue.getNom());
	        frame.setSize(400, 300);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setLayout(null);

	        JLabel label = new JLabel("Editer " + ligue.getNom());
	        label.setBounds(120, 20, 200, 25);
	        frame.add(label);

	        JButton btnAfficher = new JButton("Afficher la ligue ");
	        btnAfficher.setBounds(100, 60, 200, 30);
	        frame.add(btnAfficher);

	        

	        JButton btnGerersEmployes = new JButton("Gérer les employés ");
	        btnGerersEmployes.setBounds(100, 100, 200, 30);
	        frame.add(btnGerersEmployes);

	        JButton btnChangerAdmin = new JButton("Changer l'admin ");
	        btnChangerAdmin.setBounds(100, 140, 200, 30);
	        frame.add(btnChangerAdmin);

	        JButton btnRenommer = new JButton("Renommer ");
	        btnRenommer.setBounds(100, 180, 200, 30);
	        frame.add(btnRenommer);

	        JButton btnSupprimer = new JButton("Supprimer ");
	        btnSupprimer.setBounds(100, 220, 200, 30);
	        frame.add(btnSupprimer);

	        JButton btnBack = new JButton("Back");
	        btnBack.setBounds(10, 230, 80, 25);
	        frame.add(btnBack);

	  
	        btnBack.addActionListener(e -> frame.dispose());
	        
	        // Actions
	        btnAfficher.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // affichage des ligues Admin
	            		new AfficheLigueAdmin(ligue);
	            }
	        });
			btnGerersEmployes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// gestion des employés de la ligue
					new GererLesEmployes(ligue);
				}
			});
			btnChangerAdmin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// changer l'administrateur de la ligue
					new ChangerAdmin(ligue);
				}
			});


	        frame.setVisible(true);
	}
}
