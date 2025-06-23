package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class ajouterLigues extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ajouterLigues() {
        JFrame frame = new JFrame("Ajouter une Ligue");
        frame.setSize(350, 180);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel labelNom = new JLabel("Nom :");
        labelNom.setBounds(20, 30, 80, 25);
        frame.add(labelNom);

        JTextField textNom = new JTextField();
        textNom.setBounds(100, 30, 200, 25);
        frame.add(textNom);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setBounds(100, 70, 120, 30);
        frame.add(btnAjouter);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBounds(230, 70, 80, 30);
        frame.add(btnAnnuler);

        // Action bouton Ajouter
        btnAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomLigue = textNom.getText().trim();
                if (nomLigue.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Le nom de la ligue ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    GestionPersonnel gp = GestionPersonnel.getGestionPersonnel();
                    Ligue nouvelleLigue = gp.addLigue(nomLigue);
                    gp.sauvegarder(); // pour sauvegarder via passerelle JDBC
                    JOptionPane.showMessageDialog(frame, "Ligue ajoutée : " + nouvelleLigue.getNom());
                    frame.dispose();
                } catch (SauvegardeImpossible ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action bouton Annuler
        btnAnnuler.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}
