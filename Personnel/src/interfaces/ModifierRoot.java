package interfaces;

import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import personnel.Employe;
import personnel.SauvegardeImpossible;

public class ModifierRoot  extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModifierRoot(Employe root) {
        setTitle("Modifier Root");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnAfficher = new JButton("Afficher Root");
        btnAfficher.setBounds(100, 20, 200, 30);
        add(btnAfficher);

        JButton btnNom = new JButton("Changer le nom");
        btnNom.setBounds(100, 60, 200, 30);
        add(btnNom);

        JButton btnPrenom = new JButton("Changer le prénom");
        btnPrenom.setBounds(100, 100, 200, 30);
        add(btnPrenom);

        JButton btnEmail = new JButton("Changer le mail");
        btnEmail.setBounds(100, 140, 200, 30);
        add(btnEmail);

        JButton btnPassword = new JButton("Changer le mot de passe");
        btnPassword.setBounds(100, 180, 200, 30);
        add(btnPassword);

        JButton btnDate = new JButton("Changer la date d'inscription");
        btnDate.setBounds(100, 220, 200, 30);
        add(btnDate);

        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.setBounds(100, 260, 200, 30);
        add(btnQuitter);

        // Actions
        btnAfficher.addActionListener(e -> JOptionPane.showMessageDialog(this, root.toString()));

        btnNom.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(this, "Nouveau nom :");
            if (nom != null)
				root.setNom(nom);
        });

        btnPrenom.addActionListener(e -> {
            String prenom = JOptionPane.showInputDialog(this, "Nouveau prénom :");
            if (prenom != null)
				root.setPrenom(prenom);
        });

        btnEmail.addActionListener(e -> {
            String mail = JOptionPane.showInputDialog(this, "Nouveau mail :");
            if (mail != null)
				root.setMail(mail);
        });

        btnPassword.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(this, "Nouveau mot de passe :");
            if (password != null)
				root.setPassword(password);  
        });

        btnDate.addActionListener(e -> {
            String dateStr = JOptionPane.showInputDialog(this, "Nouvelle date (AAAA-MM-JJ) :");
            if (dateStr != null) {
                try {
                    LocalDate date = LocalDate.parse(dateStr);
                    root.setDateinscription(date);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Format invalide (AAAA-MM-JJ)", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnQuitter.addActionListener(e -> dispose());

        setVisible(true);
    }
}
