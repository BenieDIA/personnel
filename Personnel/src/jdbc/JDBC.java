package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() {
	    GestionPersonnel gestionPersonnel = new GestionPersonnel();
	    try {
	        String requeteLigues = "SELECT * FROM ligue";;
	        Statement instructionLigues = connection.createStatement();
	        ResultSet ligues = instructionLigues.executeQuery(requeteLigues);
	        
	        while (ligues.next()) {
	            gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
	                        
            }
	        String requeteRoot = "SELECT * FROM employe WHERE nom = 'root'";
	        Statement instructionRoot = connection.createStatement();
	        ResultSet rootResultat = instructionRoot.executeQuery(requeteRoot);

	        if (!rootResultat.next()) {  
	        	int id = rootResultat.getInt("id");
	        	String nom = rootResultat.getString("nom");
	        	String password = rootResultat.getString("password");
	            gestionPersonnel.addRoot(id,nom, password);
	        }

	    } catch (SQLException | SauvegardeImpossible e) {
	        System.out.println(e);
	    } 
	    return gestionPersonnel;
	}


	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	@Override 
	public int insert(Employe employe) throws SauvegardeImpossible
	{
		try {
			
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into employe (dateArriver, nom, prenom, mail, password, ligue_id) values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setDate(1, java.sql.Date.valueOf(employe.getDateinscription()));
			instruction.setString(2, employe.getNom());
			instruction.setString(3, employe.getPrenom());
			instruction.setString(4, employe.getMail());
			instruction.setString(5, employe.getPassword());
			 if (employe.estRoot()) {
		            instruction.setNull(6, java.sql.Types.INTEGER);
		        } else {
		            instruction.setInt(6, employe.getLigue().getLigueId());
		        }
			System.out.println("Ajout de l'employé : " + employe.getNom() + " | Ligue ID : " + (employe.getLigue() != null ? employe.getLigue().getLigueId() : "NULL"));
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
			
			
		}
		catch(SQLException exception){
			
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
			
		}
		
	}

	public int update(Employe employe) throws SauvegardeImpossible {
	    try {
	        PreparedStatement instruction;
	        instruction = connection.prepareStatement(
	            "UPDATE employe SET dateArriver = ?, nom = ?, prenom = ?, mail = ?, password = ? WHERE id = ?"
	        );
	        instruction.setDate(1, java.sql.Date.valueOf(employe.getDateinscription()));
	        instruction.setString(2, employe.getNom());
	        instruction.setString(3, employe.getPrenom());
	        instruction.setString(4, employe.getMail());
	        instruction.setString(5, employe.getPassword());
	        instruction.setInt(6, employe.getId());
	        instruction.executeUpdate();
	        return employe.getId();
	    } catch (SQLException exception) {
	        exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
	    }
	}
	public int update(Ligue ligue) throws SauvegardeImpossible {
	    try {
	        PreparedStatement instruction;
	            instruction = connection.prepareStatement(
	                "UPDATE ligue SET nom = ? WHERE id = ?;"
	            );
	         
	            instruction.setString(1, ligue.getNom());
	            instruction.setInt(2, ligue.getLigueId());
	            instruction.executeUpdate();
	            return ligue.getLigueId();
	        } catch (SQLException exception) {
	            exception.printStackTrace();
	            throw new SauvegardeImpossible(exception);
	        }
	
	        }
	
	

}
