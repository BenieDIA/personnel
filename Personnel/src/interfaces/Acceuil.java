package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import personnel.Employe;
import personnel.GestionPersonnel;

public class Acceuil extends JFrame {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Acceuil() {
	        JFrame frame = new JFrame("Accueil");
	        frame.setSize(400, 250);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(null);

	        JLabel labelBienvenue = new JLabel("Bienvenue sur l'application !");
	        labelBienvenue.setBounds(100, 20, 250, 30);
	        frame.add(labelBienvenue);

	        // Bouton Modifier Root 
	        JButton btnModifierRoot = new JButton(" Modifier Root");
	        btnModifierRoot.setBounds(100, 60, 200, 30);
	        frame.add(btnModifierRoot);

	        // Bouton Gérer les Ligues 
	        JButton btnGererlLigues = new JButton("Gérer les Ligues");
	        btnGererlLigues.setBounds(100, 100, 200, 30);
	        frame.add(btnGererlLigues);

	        // Bouton Quitter 
	        JButton btnQuitter = new JButton(" Quitter");
	        btnQuitter.setBounds(100, 140, 200, 30);
	        frame.add(btnQuitter);

	        // Action pour Modifier Root
	        btnModifierRoot.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	 // Récupérer le root
	                Employe root = GestionPersonnel.getGestionPersonnel().getRoot();
	                if (root != null) {
	                    new ModifierRoot(root);
	                } else {
	                    JOptionPane.showMessageDialog(frame, "Root non trouvé dans la base.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	            }
	        );

	        // Action pour Gérer les Ligues
	        btnGererlLigues.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	new gereLigue();
	            }
	        });

	        // Action pour Quitter
	        btnQuitter.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                int confirm = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter ?", "Confirmation", JOptionPane.YES_NO_OPTION);
	                if (confirm == JOptionPane.YES_OPTION) {
	                    frame.dispose(); // ferme la fenêtre
	                }
	            }
	        });

	        frame.setVisible(true);
	        }
	   
}
