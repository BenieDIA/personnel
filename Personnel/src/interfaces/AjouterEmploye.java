package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class AjouterEmploye {
	
	AjouterEmploye(Ligue ligue){
		  JFrame frame = new JFrame("Ajouter un Employé");
	        frame.setSize(400, 300);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setLayout(null);

	        JLabel labelNom = new JLabel("Nom :");
	        labelNom.setBounds(20, 30, 100, 25);
	        frame.add(labelNom);

	        JTextField textNom = new JTextField();
	        textNom.setBounds(150, 30, 200, 25);
	        frame.add(textNom);

	        JLabel labelPrenom = new JLabel("Prénom :");
	        labelPrenom.setBounds(20, 70, 100, 25);
	        frame.add(labelPrenom);

	        JTextField textPrenom = new JTextField();
	        textPrenom.setBounds(150, 70, 200, 25);
	        frame.add(textPrenom);

	        JLabel labelMail = new JLabel("Email :");
	        labelMail.setBounds(20, 110, 100, 25);
	        frame.add(labelMail);

	        JTextField textMail = new JTextField();
	        textMail.setBounds(150, 110, 200, 25);
	        frame.add(textMail);

	        JLabel labelPassword = new JLabel("Mot de passe :");
	        labelPassword.setBounds(20, 150, 100, 25);
	        frame.add(labelPassword);

	        JTextField textPassword = new JTextField();
	        textPassword.setBounds(150, 150, 200, 25);
	        frame.add(textPassword);

	        JLabel labelDateArrivee = new JLabel("Date d'arrivée (aaaa-mm-jj) :");
	        labelDateArrivee.setBounds(20, 190, 120, 25);
	        frame.add(labelDateArrivee);

	        JTextField textDateArrivee = new JTextField();
	        textDateArrivee.setBounds(150, 190, 200, 25);
	        frame.add(textDateArrivee);

	        JButton btnAjouter = new JButton("Ajouter");
	        btnAjouter.setBounds(150, 230, 100, 30);
	        frame.add(btnAjouter);

	        JButton btnAnnuler = new JButton("Annuler");
	        btnAnnuler.setBounds(260, 230, 100, 30);
	        frame.add(btnAnnuler);

	        // Action bouton Ajouter
	        btnAjouter.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String nom = textNom.getText().trim();
	                String prenom = textPrenom.getText().trim();
	                String mail = textMail.getText().trim();
	                String password = textPassword.getText().trim();
	                String dateArriveeStr = textDateArrivee.getText().trim();

	                if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || password.isEmpty() || dateArriveeStr.isEmpty()) {
	                    JOptionPane.showMessageDialog(frame, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                try {
	                    LocalDate dateArrivee = LocalDate.parse(dateArriveeStr);
	                    ligue.addEmploye(nom, prenom, mail, password, dateArrivee, null);
	                    JOptionPane.showMessageDialog(frame, "Employé ajouté avec succès !");
	                    frame.dispose();
	                } catch (DateTimeParseException ex) {
	                    JOptionPane.showMessageDialog(frame, "Format de date invalide. Utilisez le format aaaa-mm-jj.", "Erreur", JOptionPane.ERROR_MESSAGE);
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