package interfaces;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class loginRoot extends JFrame{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    public loginRoot() {
    	
    	        // Vérifie si le root existe, sinon l'ajoute
    	  ajouterRootSiNecessaire();
    	  
	        JFrame frame = new JFrame("Connexion Root");
	    	frame.setSize(400, 200);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLayout(null);

	        // Label "Root"
	        JLabel labelRoot = new JLabel("Root");
	        labelRoot.setBounds(20, 20, 80, 25);
	        frame.add(labelRoot);


	        JTextField textRoot = new JTextField();
	        textRoot.setBounds(100, 20, 200, 25);
	        frame.add(textRoot);

	        JLabel labelPassword = new JLabel("Mot de passe: ");
	        labelPassword.setBounds(10, 60, 100, 25);
	        frame.add(labelPassword);

	        JPasswordField textPassword = new JPasswordField();
	        textPassword.setBounds(100, 60, 200, 25);
	        frame.add(textPassword);
	        
	        JButton btnConnexion = new JButton("Se connecter");
	        btnConnexion.setBounds(100, 100, 150, 30);
	        frame.add(btnConnexion);
	        btnConnexion.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
			
		     		String userName = textRoot.getText();
		     	     String password = new String(textPassword.getPassword());
	
		     	    try {
	                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaml", "root", "");
	                    PreparedStatement st = connection.prepareStatement(
	                        "SELECT nom, password FROM employe WHERE isSuperUser = TRUE AND nom = ? AND password = ?");
	                    st.setString(1, userName);
	                    st.setString(2, password);
	                    ResultSet rs = st.executeQuery();

	                    if (rs.next()) {
	                        JOptionPane.showMessageDialog(null, "Connexion réussie en tant que root !");
	                        frame.dispose();
	                        new Acceuil(); // Ouvre l'interface principale
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Accès refusé. Seul le root peut se connecter.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                    }

	                    rs.close();
	                    st.close();
	                    connection.close();

	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });
	        	frame.setVisible(true);
	    }

    private void ajouterRootSiNecessaire() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaml", "root", "")) {
            // Vérifie si le root existe déjà
            PreparedStatement checkRoot = connection.prepareStatement(
                "SELECT * FROM employe WHERE nom = 'root' AND isSuperUser = TRUE");
            ResultSet rs = checkRoot.executeQuery();

            if (!rs.next()) {
                // Ajoute le root avec des identifiants par défaut
                PreparedStatement insertRoot = connection.prepareStatement(
                    "INSERT INTO employe (nom, password, isSuperUser) VALUES ('root', 'toor', TRUE)");
                insertRoot.executeUpdate();
                insertRoot.close();
                System.out.println("Root ajouté à la base de données.");
            }

            rs.close();
            checkRoot.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du root à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new loginRoot();
    }
	       
}
