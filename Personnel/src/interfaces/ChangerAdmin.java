package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import personnel.Employe;
import personnel.Ligue;

public class ChangerAdmin {
	 private static final long serialVersionUID = 1L;

	    public ChangerAdmin(Ligue ligue) {
	        JFrame frame = new JFrame("Changer Administrateur");
	        frame.setSize(400, 200);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setLayout(null);

	        JLabel label = new JLabel("Sélectionnez un employé pour l'affecter comme administrateur :");
	        label.setBounds(20, 20, 350, 25);
	        frame.add(label);

	        // Liste déroulante des employés
	        JComboBox<Employe> comboEmployes = new JComboBox<>();
	        for (Employe employe : ligue.getEmployes()) {
	            comboEmployes.addItem(employe);
	        }
	        comboEmployes.setBounds(20, 60, 350, 25);
	        frame.add(comboEmployes);

	        // Bouton pour affecter l'administrateur
	        JButton btnAffecter = new JButton("Affecter Admin");
	        btnAffecter.setBounds(120, 100, 150, 30);
	        frame.add(btnAffecter);

	        // Action du bouton
	        btnAffecter.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Employe employeSelectionne = (Employe) comboEmployes.getSelectedItem();
	                if (employeSelectionne != null) {
	                    try {
	                        ligue.setAdministrateur(employeSelectionne);
	                        JOptionPane.showMessageDialog(frame, employeSelectionne.getNom() + " est maintenant administrateur.");
	                        frame.dispose();
	                    } catch (Exception ex) {
	                        JOptionPane.showMessageDialog(frame, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(frame, "Aucun employé sélectionné.", "Erreur", JOptionPane.WARNING_MESSAGE);
	                }
	            }
	        });

	        frame.setVisible(true);
	    }
}