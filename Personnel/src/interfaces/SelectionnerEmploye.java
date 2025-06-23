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

public class SelectionnerEmploye extends JFrame {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionnerEmploye(Ligue ligue) {
	        JFrame frame = new JFrame("Sélectionner un employé");
	        frame.setSize(350, 180);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        frame.setLayout(null);

	        JLabel label = new JLabel("Choisissez un employé :");
	        label.setBounds(20, 20, 200, 25);
	        frame.add(label);

	        // ComboBox avec les employés
	        JComboBox<Employe> comboEmployes = new JComboBox<>();
	        for (Employe employe : ligue.getEmployes()) {
	            comboEmployes.addItem(employe);
	        }
	        comboEmployes.setBounds(20, 60, 300, 25);
	        frame.add(comboEmployes);

	        JButton btnOK = new JButton("OK");
	        btnOK.setBounds(60, 100, 80, 30);
	        frame.add(btnOK);

	        JButton btnAnnuler = new JButton("Annuler");
	        btnAnnuler.setBounds(180, 100, 100, 30);
	        frame.add(btnAnnuler);

	        // Action OK
	        btnOK.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Employe employeSelectionne = (Employe) comboEmployes.getSelectedItem();
	                if (employeSelectionne != null) {
	                    JOptionPane.showMessageDialog(frame, "Employé sélectionné : " + employeSelectionne.getNom() + " " + employeSelectionne.getPrenom());
	                    frame.dispose();
	                    new afficheEmployeSelect(employeSelectionne);
	                } else {
	                    JOptionPane.showMessageDialog(frame, "Aucun employé sélectionné.");
	                }
	            }
	        });

	        // Action Annuler
	        btnAnnuler.addActionListener(e -> frame.dispose());

	        frame.setVisible(true);
	    }

}