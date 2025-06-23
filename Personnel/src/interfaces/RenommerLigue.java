package interfaces;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import personnel.Ligue;

public class RenommerLigue extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RenommerLigue(Ligue ligue) {
		JFrame frame = new JFrame("Renommer la ligue " + ligue.getNom());
		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);

		JLabel label = new JLabel("Renommer la ligue " + ligue.getNom());
		label.setBounds(120, 20, 200, 25);
		frame.add(label);

		JButton btnRenommer = new JButton("Renommer");
		btnRenommer.setBounds(140, 60, 120, 30);
		frame.add(btnRenommer);

		JButton btnFermer = new JButton("Fermer");
		btnFermer.setBounds(140, 100, 120, 30);
		frame.add(btnFermer);

		btnFermer.addActionListener(e -> frame.dispose());

		btnRenommer.addActionListener(e -> {
			String nouveauNom = JOptionPane.showInputDialog(frame, "Entrez le nouveau nom de la ligue :");
			if (nouveauNom != null && !nouveauNom.trim().isEmpty()) {
				try {
					ligue.setNom(nouveauNom);
					JOptionPane.showMessageDialog(frame, "Ligue renommée en " + nouveauNom);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage(), "Erreur",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Le nom ne peut pas être vide.", "Erreur",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		frame.setLocationRelativeTo(null); // Centrer la fenêtre
		frame.setResizable(false); // Empêcher le redimensionnement de la fenêtre
		frame.setVisible(true);
	}
}
