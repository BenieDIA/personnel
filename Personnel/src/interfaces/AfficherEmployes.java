package interfaces;

import java.awt.BorderLayout;
import java.util.SortedSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import commandLineMenus.List;
import personnel.Employe;
import personnel.Ligue;

public class AfficherEmployes extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AfficherEmployes(Ligue ligue) {
        setTitle("Liste des Employés - " + ligue.getNom());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Liste des employés
        DefaultListModel<String> employeListModel = new DefaultListModel<>();
        SortedSet<Employe> employes = ligue.getEmployes();
        for (Employe employe : employes) {
            employeListModel.addElement(employe.getNom() + " " + employe.getPrenom() + " - " + employe.getMail());
        }
        JList<String> employeList = new JList<>(employeListModel);
        JScrollPane scrollPane = new JScrollPane(employeList);
        add(scrollPane, BorderLayout.CENTER);

        // Bouton Fermer
        JButton btnFermer = new JButton("Fermer");
        btnFermer.addActionListener(e -> dispose());
        add(btnFermer, BorderLayout.SOUTH);

        setVisible(true);
    }
}
