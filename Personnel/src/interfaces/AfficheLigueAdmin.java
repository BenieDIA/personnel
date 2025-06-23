package interfaces;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import personnel.Ligue;

public class AfficheLigueAdmin {

	public AfficheLigueAdmin(Ligue ligue) {
		JFrame frame = new JFrame("Ligue : " + ligue.getNom());
	    frame.setSize(350, 200);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setLayout(null);

	    JLabel labelNom = new JLabel("Ligue : " + ligue.getNom());
	    labelNom.setBounds(50, 30, 250, 30);
	    frame.add(labelNom);

	    JLabel labelAdmin = new JLabel("Administrée par : " + ligue.getAdministrateur());
	    labelAdmin.setBounds(50, 70, 250, 30);
	    frame.add(labelAdmin);

	    JButton btnFermer = new JButton("Fermer");
	    btnFermer.setBounds(120, 120, 100, 30);
	    frame.add(btnFermer);	

	    btnFermer.addActionListener(e -> frame.dispose());

	    frame.setVisible(true);
	}
}