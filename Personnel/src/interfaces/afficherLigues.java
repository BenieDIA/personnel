package interfaces;

import java.awt.BorderLayout;
import java.util.SortedSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import personnel.GestionPersonnel;
import personnel.Ligue;

public class afficherLigues extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public afficherLigues() {
	       JFrame frame = new JFrame("Liste des Ligues");
	        frame.setSize(400, 300);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setLayout(new BorderLayout());

	        JTextArea textAreaLigues = new JTextArea();
	        textAreaLigues.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(textAreaLigues);
	        frame.add(scrollPane, BorderLayout.CENTER);

	        JButton btnFermer = new JButton("Fermer");
	        frame.add(btnFermer, BorderLayout.SOUTH);

	        // Action pour fermer la fenêtre
	        btnFermer.addActionListener(e -> frame.dispose());

	        // Récupérer les ligues et les afficher
	        GestionPersonnel gp = GestionPersonnel.getGestionPersonnel();
	        SortedSet<Ligue> ligues = gp.getLigues();

	        if (ligues.isEmpty()) {
	            textAreaLigues.setText("Aucune ligue enregistrée.");
	        } else {
	            StringBuilder message = new StringBuilder();
	            for (Ligue ligue : ligues) {
	                message.append("- ").append(ligue.getNom()).append("\n");
	            }
	            textAreaLigues.setText(message.toString());
	        }

	        frame.setVisible(true);
	    }
	
	}
