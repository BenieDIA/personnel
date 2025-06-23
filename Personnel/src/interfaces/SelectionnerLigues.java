package interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import personnel.GestionPersonnel;
import personnel.Ligue;

public class SelectionnerLigues extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionnerLigues() {
        JFrame frame = new JFrame("Sélectionner une ligue");
        frame.setSize(350, 180);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel label = new JLabel("Choisissez une ligue :");
        label.setBounds(20, 20, 200, 25);
        frame.add(label);

        // ComboBox avec les ligues
        GestionPersonnel gp = GestionPersonnel.getGestionPersonnel();
        JComboBox<Ligue> comboLigues = new JComboBox<>();
        for (Ligue ligue : gp.getLigues()) {
            comboLigues.addItem(ligue);
        }
        comboLigues.setBounds(20, 60, 300, 25);
        frame.add(comboLigues);

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
                Ligue ligueSelectionnee = (Ligue) comboLigues.getSelectedItem();
                if (ligueSelectionnee != null) {
                    new EditerLigue(ligueSelectionnee);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Aucune ligue sélectionnée.");
                }
            }
        });
        // Action Annuler
        btnAnnuler.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
	}
}