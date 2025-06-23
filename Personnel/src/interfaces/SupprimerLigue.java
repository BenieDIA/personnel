package interfaces;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import personnel.GestionPersonnel;
import personnel.Ligue;

public class SupprimerLigue extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SupprimerLigue(Ligue ligue) {
		JFrame frame = new JFrame("Supprimer " + ligue.getNom());
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);

		JLabel label = new JLabel("Supprimer " + ligue.getNom() + " ?");
		label.setBounds(120, 20, 200, 25);
		frame.add(label);

		JButton btnConfirmer = new JButton("Confirmer la suppression");
		btnConfirmer.setBounds(100, 60, 200, 30);
		frame.add(btnConfirmer);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(100, 100, 200, 30);
		frame.add(btnAnnuler);

		btnConfirmer.addActionListener(e -> {
			try {
				ligue.remove();
                JOptionPane.showMessageDialog(frame, "Ligue supprimée avec succès.");
                frame.dispose();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		btnAnnuler.addActionListener(e -> frame.dispose());

		frame.setVisible(true);
	}
}