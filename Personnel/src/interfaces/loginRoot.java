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

	public static void main(String[] args) {
	        // Crée la fenêtre
	        JFrame frame = new JFrame("Connexion Root");
	        frame.setSize(300, 150);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	        		String password = textPassword.getText();
	
	            	try {
	        			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/m2l", "root","");
	        			PreparedStatement st = connection.prepareStatement("Select nom, password from employe where nom=? and password = ?");
	        			st.setString(1, userName);
	        			st.setString(2, password);
	        			ResultSet rs = st.executeQuery();
	        			
	        			if (rs.next()) {
	                        JOptionPane.showMessageDialog(null, "Connexion réussie !");
	                        // ouvrir la fenêtre principale ou continuer ton appli
	                        frame.dispose();
	                        new Acceuil();	                        
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                    }
	        			
	        		      rs.close();
	        	            st.close();
	        	            connection.close();
	        			
	        		}catch(SQLException ex) {
	        			ex.printStackTrace();
	        		}
	            	
	                
				}
			});
	        	frame.setVisible(true);
	    }
	       
}
