package jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.protocol.x.StatementExecuteOkMessageListener;

import personnel.Employe;
import personnel.GestionPersonnel;
import personnel.Ligue;
import personnel.Passerelle;
import personnel.SauvegardeImpossible;


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
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
			
		
			// lecture Root
			  String requeteRoot = "SELECT * FROM employe WHERE nom = 'root'";
              Statement instructionRoot = connection.createStatement();
              ResultSet rootResultat = instructionRoot.executeQuery(requeteRoot);
              if (rootResultat.next()) {
            	  int id = rootResultat.getInt("id");
            	  String nom = rootResultat.getString("nom");
            	  String password = rootResultat.getString("password");
                  gestionPersonnel.addRoot(id,nom,password);
              }
              
              
            
		}
		
		catch (SQLException e)
		{
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
	
	//insertion ligue
	
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

	
	
	// insertion Employe
	@Override 
    public int insert(Employe employe) throws SauvegardeImpossible
    {
        try {

        	PreparedStatement instruction = connection.prepareStatement("insert into employe (dateArriver, nom, prenom, mail, password, ligue_id )  values(?,?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
        	if (employe.getDate() != null) {
        	    instruction.setDate(1, java.sql.Date.valueOf(employe.getDate()));
        	} else {
        	    instruction.setNull(1, java.sql.Types.DATE);
        	}      
        	
        	instruction.setString(2, employe.getNom());
        	instruction.setString(3, employe.getPrenom());
        	instruction.setString(4, employe.getMail());
        	instruction.setString(5, employe.getPassword());
        	if (employe.getLigue()==null) {
                instruction.setNull(6, java.sql.Types.INTEGER);
            } else {
                instruction.setInt(6, employe.getLigue().getId());
            }
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

	@Override
	public int update(Ligue ligue) throws SauvegardeImpossible {
		
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update ligue set nom = ? where id = ?" , Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			int update = instruction.executeUpdate(); // Exécute la mise à jour et retourne le nombre de lignes affectées
			return update;
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}

	@Override
	public int update(Employe employe) throws SauvegardeImpossible {
		try {
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update employe set nom = ? , prenom = ?, mail = ? , password = ?, dateArriver = ? where id = ? ", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			instruction.setDate(5, java.sql.Date.valueOf(employe.getDate()));
			instruction.setInt(6, employe.getId());
			
			int update = instruction.executeUpdate(); 
			return update;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}

	}

	@Override
	public int delete(Ligue ligue) throws SauvegardeImpossible {

		try {
			PreparedStatement instruction;
			
			instruction = connection.prepareStatement("delete ligue, employe from ligue LEFT JOIN employe ON employe.ligue_id = ligue.id where ligue.id = ? ", Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1, ligue.getId());


			int delete = instruction.executeUpdate();
			return delete;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	

	}

	@Override
	public int delete(Employe employe) throws SauvegardeImpossible {
		
		try {
			PreparedStatement instruction;
			
			instruction = connection.prepareStatement("DELETE from employe where id = ?", Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1, employe.getId());
			int delete = instruction.executeUpdate();
			return delete;
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
		
	}
		



	


}
