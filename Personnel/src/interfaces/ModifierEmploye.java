package interfaces;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import personnel.Employe;
import personnel.SauvegardeImpossible;
import personnel.dateInvalide;

public class ModifierEmploye extends JFrame {
	  private static final long serialVersionUID = 1L;

	    public ModifierEmploye(Employe employe) {
	        JFrame menuFrame = new JFrame("Modifier - " + employe.getNom() + " " + employe.getPrenom());
	        menuFrame.setSize(400, 300);
	        menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        menuFrame.setLayout(null);

	        JLabel label = new JLabel("Options pour " + employe.getNom() + " :");
	        label.setBounds(20, 20, 350, 25);
	        menuFrame.add(label);

	        JButton btnAfficher = new JButton("l : Afficher l'employé");
	        btnAfficher.setBounds(20, 50, 350, 30);
	        menuFrame.add(btnAfficher);

	        JButton btnChangerNom = new JButton("n : Changer le nom");
	        btnChangerNom.setBounds(20, 90, 350, 30);
	        menuFrame.add(btnChangerNom);

	        JButton btnChangerPrenom = new JButton("p : Changer le prénom");
	        btnChangerPrenom.setBounds(20, 130, 350, 30);
	        menuFrame.add(btnChangerPrenom);

	        JButton btnChangerMail = new JButton("e : Changer le mail");
	        btnChangerMail.setBounds(20, 170, 350, 30);
	        menuFrame.add(btnChangerMail);

	        JButton btnChangerPassword = new JButton("x : Changer le mot de passe");
	        btnChangerPassword.setBounds(20, 210, 350, 30);
	        menuFrame.add(btnChangerPassword);

	        JButton btnChangerDate = new JButton("d : Changer la date d'inscription");
	        btnChangerDate.setBounds(20, 250, 350, 30);
	        menuFrame.add(btnChangerDate);

	        JButton btnBack = new JButton("q : Back");
	        btnBack.setBounds(20, 290, 350, 30);
	        menuFrame.add(btnBack);

	        // Actions des boutons
	        btnAfficher.addActionListener(e -> JOptionPane.showMessageDialog(menuFrame, employe.toString()));

	        btnChangerNom.addActionListener(e -> {
	            String nouveauNom = JOptionPane.showInputDialog(menuFrame, "Entrez le nouveau nom :");
	            if (nouveauNom != null && !nouveauNom.trim().isEmpty()) {
	                try {
	                    employe.setNom(nouveauNom.trim());
	                    JOptionPane.showMessageDialog(menuFrame, "Nom modifié avec succès !");
	                } catch (SauvegardeImpossible ex) {
	                    JOptionPane.showMessageDialog(menuFrame, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        btnChangerPrenom.addActionListener(e -> {
	            String nouveauPrenom = JOptionPane.showInputDialog(menuFrame, "Entrez le nouveau prénom :");
	            if (nouveauPrenom != null && !nouveauPrenom.trim().isEmpty()) {
	                try {
	                    employe.setPrenom(nouveauPrenom.trim());
	                    JOptionPane.showMessageDialog(menuFrame, "Prénom modifié avec succès !");
	                } catch (SauvegardeImpossible ex) {
	                    JOptionPane.showMessageDialog(menuFrame, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        btnChangerMail.addActionListener(e -> {
	            String nouveauMail = JOptionPane.showInputDialog(menuFrame, "Entrez le nouveau mail :");
	            if (nouveauMail != null && !nouveauMail.trim().isEmpty()) {
	                try {
	                    employe.setMail(nouveauMail.trim());
	                    JOptionPane.showMessageDialog(menuFrame, "Mail modifié avec succès !");
	                } catch (SauvegardeImpossible ex) {
	                    JOptionPane.showMessageDialog(menuFrame, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        btnChangerPassword.addActionListener(e -> {
	            String nouveauPassword = JOptionPane.showInputDialog(menuFrame, "Entrez le nouveau mot de passe :");
	            if (nouveauPassword != null && !nouveauPassword.trim().isEmpty()) {
	                try {
	                    employe.setPassword(nouveauPassword.trim());
	                    JOptionPane.showMessageDialog(menuFrame, "Mot de passe modifié avec succès !");
	                } catch (SauvegardeImpossible ex) {
	                    JOptionPane.showMessageDialog(menuFrame, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        btnChangerDate.addActionListener(e -> {
	            String nouvelleDate = JOptionPane.showInputDialog(menuFrame, "Entrez la nouvelle date d'inscription (aaaa-mm-jj) :");
	            if (nouvelleDate != null && !nouvelleDate.trim().isEmpty()) {
	                try {
	                    LocalDate date = LocalDate.parse(nouvelleDate.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	                    employe.setDate(date);
	                    JOptionPane.showMessageDialog(menuFrame, "Date d'inscription modifiée avec succès !");
	                } catch (DateTimeParseException ex) {
	                    JOptionPane.showMessageDialog(menuFrame, "Format de date invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                } catch (dateInvalide | SauvegardeImpossible ex) {
	                    JOptionPane.showMessageDialog(menuFrame, "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        btnBack.addActionListener(e -> menuFrame.dispose());

	        menuFrame.setVisible(true);
	    }
}
