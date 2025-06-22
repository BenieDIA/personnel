package interfaces;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import personnel.Employe;

public class afficheEmployeSelect extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public afficheEmployeSelect(Employe employe) {
        JFrame menuFrame = new JFrame("Menu - " + employe.getNom() + " " + employe.getPrenom());
        menuFrame.setSize(300, 200);
        menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuFrame.setLayout(null);

        JLabel label = new JLabel("Options pour " + employe.getNom() + " :");
        label.setBounds(20, 20, 250, 25);
        menuFrame.add(label);

        JButton btnAfficher = new JButton("l : Afficher l'employé");
        btnAfficher.setBounds(20, 50, 250, 30);
        menuFrame.add(btnAfficher);

        JButton btnModifier = new JButton("m : Modifier " + employe.getNom());
        btnModifier.setBounds(20, 90, 250, 30);
        menuFrame.add(btnModifier);

        JButton btnSupprimer = new JButton("v : Supprimer");
        btnSupprimer.setBounds(20, 130, 250, 30);
        menuFrame.add(btnSupprimer);

        JButton btnBack = new JButton("q : Back");
        btnBack.setBounds(20, 170, 250, 30);
        menuFrame.add(btnBack);

        // Actions des boutons
        btnAfficher.addActionListener(e -> JOptionPane.showMessageDialog(menuFrame, employe.toString()));
        
        btnModifier.addActionListener(e -> {
            JOptionPane.showMessageDialog(menuFrame, "Modifier " + employe.getNom());
            new ModifierEmploye(employe);
        });
        
        btnSupprimer.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(menuFrame, "Voulez-vous vraiment supprimer " + employe.getNom() + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    employe.remove();
                    JOptionPane.showMessageDialog(menuFrame, employe.getNom() + " a été supprimé.");
                    menuFrame.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(menuFrame, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnBack.addActionListener(e -> menuFrame.dispose());

        menuFrame.setVisible(true);
    }
}
