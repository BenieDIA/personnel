package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
	public GestionPersonnel getGestionPersonnel() {
	    GestionPersonnel gestionPersonnel = new GestionPersonnel();
	    try {
	    	String requete = "SELECT l.id AS ligue_id, l.nom AS ligue_nom, e.id AS employe_admin_id " +
	                 "FROM Ligue l " +
	                 "LEFT JOIN Employe e ON e.ligue_id = l.id AND e.isAdmin = true";

	    			Statement instruction = connection.createStatement();
	    			ResultSet resultats = instruction.executeQuery(requete);

	    			while (resultats.next()) {
	    				int ligueId = resultats.getInt("ligue_id");
	    				String ligueNom = resultats.getString("ligue_nom");
	    
				    // attention : employe_admin_id peut être null si pas d'admin
				    int employeAdminId = resultats.getInt("employe_admin_id");
				    if (resultats.wasNull()) {
				        employeAdminId = -1;  // ou 0, ou laisser null selon ta logique
	    }

	    Ligue ligue = gestionPersonnel.addLigue(ligueId, ligueNom, employeAdminId);
	            
	            

	            // Utilisation d'un Set pour éviter les doublons
	            Set<Integer> employeIds = new HashSet<>();

	            String requestEmployes = "SELECT * from employe where ligue_id = ?;";
	            PreparedStatement instructionEmployes = connection.prepareStatement(requestEmployes);
	            instructionEmployes.setInt(1, ligueId);
	            ResultSet employes = instructionEmployes.executeQuery();
	            while (employes.next()) {
	                int employeId = employes.getInt("id");
	                
	                

	                // Vérification si l'employé existe déjà
	                if (!employeIds.contains(employeId)) {
	                    String employeNom = employes.getString("nom");
	                    String employePrenom = employes.getString("prenom");
	                    String employeMail = employes.getString("mail");
	                    String employePassword = employes.getString("password");
	                    LocalDate employeDateArrivee = employes.getObject("dateArriver", LocalDate.class);
	                    LocalDate employeDateDepart = employes.getObject("dateDepart", LocalDate.class);
	                    
	                    
	                    // Ajout de l'employé à la ligue
	                    Employe employe = ligue.addEmploye(employeId, employeNom, employePrenom, employeMail, employePassword, employeDateArrivee, employeDateDepart);
	                    
	                    // Vérification si l'employé est l'administrateur de la ligue
	                    if (employeId == employeAdminId) {
	                        ligue.setAdministrateur(employe);
	                    }
	                    
	                    // Ajout de l'ID de l'employé au Set
	                    
	                    employeIds.add(employeId);
	                }else {
	                    System.out.println("Employé avec l'ID " + employeId + " existe déjà.");
	                }
	            }
	        }

	     // Récupérer les informations de root
	        String requeteRoot = "SELECT * FROM employe WHERE ligue_id IS NULL";
	        Statement instructionRoot = connection.createStatement();
	        ResultSet rootResult = instructionRoot.executeQuery(requeteRoot);

	        if (rootResult.next()) {
	        	System.out.println("J'ai trouvé le root");
	            int id = rootResult.getInt("id");
	            String nom = rootResult.getString("nom");
	            String password = rootResult.getString("password");

	            // Vérifier si root existe déjà    
	                gestionPersonnel.addRoot(id, nom, password);
	        }
	        else {
	        	System.out.println("J'ai pas trouvé le root");
	        }
	    } catch (SQLException e) {
	        System.out.println(e);
	    } catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    public int insert(Employe employe) throws SauvegardeImpossible {
        try {
            PreparedStatement instruction = connection.prepareStatement(
                "INSERT INTO employe (dateArriver, nom, prenom, mail, password, ligue_id) VALUES(?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            instruction.setDate(1, java.sql.Date.valueOf(employe.getDate()));
            instruction.setString(2, employe.getNom());
            instruction.setString(3, employe.getPrenom());
            instruction.setString(4, employe.getMail());
            instruction.setString(5, employe.getPassword());

            if (employe.getLigue() == null) {
                instruction.setNull(6, java.sql.Types.INTEGER);
            } else {
                instruction.setInt(6, employe.getLigue().getId());
            }

            instruction.executeUpdate();
            ResultSet id = instruction.getGeneratedKeys();
            id.next();
            return id.getInt(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new SauvegardeImpossible(exception);
        }
    }
    

    @Override
    public int update(Ligue ligue) throws SauvegardeImpossible {
        try {
            PreparedStatement instruction = connection.prepareStatement(
                "UPDATE ligue SET nom = ? WHERE id = ?");
            instruction.setString(1, ligue.getNom());
            instruction.setInt(2, ligue.getId());
            instruction.executeUpdate();
            return ligue.getId();
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new SauvegardeImpossible(exception);
        }
    }

    @Override
    public int update(Employe employe) throws SauvegardeImpossible {
        try {
            PreparedStatement instruction = connection.prepareStatement(
                "UPDATE employe SET dateArriver = ?, nom = ?, prenom = ?, mail = ?, password = ? WHERE id = ?");
            instruction.setDate(1, employe.getDate() != null 
                ? java.sql.Date.valueOf(employe.getDate()) 
                : java.sql.Date.valueOf(LocalDate.now())); // Utiliser une date par défaut
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

@Override
public int delete(Ligue ligue) throws SauvegardeImpossible {
    try {
        // Supprimer les employés associés à la ligue
        PreparedStatement deleteEmployes = connection.prepareStatement(
            "DELETE FROM employe WHERE ligue_id = ?");
        deleteEmployes.setInt(1, ligue.getId());
        deleteEmployes.executeUpdate();

        // Supprimer la ligue
        PreparedStatement deleteLigue = connection.prepareStatement(
            "DELETE FROM ligue WHERE id = ?");
        deleteLigue.setInt(1, ligue.getId());
        int delete = deleteLigue.executeUpdate();

        return delete;
    } catch (SQLException exception) {
        exception.printStackTrace();
        throw new SauvegardeImpossible(exception);
    }
}


@Override
public int delete(Employe employe) throws SauvegardeImpossible {
    try {
        if (employe.getLigue() == null) {
            throw new SauvegardeImpossible(null);
        }

        PreparedStatement instruction = connection.prepareStatement(
            "DELETE FROM employe WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
        instruction.setInt(1, employe.getId());
        int delete = instruction.executeUpdate();
        return delete;

    } catch (SQLException e) {
        e.printStackTrace();
        throw new SauvegardeImpossible(e);
    }
}
	@Override
	public int setAdmin(Employe employe) throws SauvegardeImpossible {
	    try {
	        // 1. Retirer le rôle admin à tous les employés de la ligue
	        PreparedStatement removeAdmins = connection.prepareStatement(
	            "UPDATE employe SET isAdmin = 0 WHERE ligue_id = ?");
	        removeAdmins.setInt(1, employe.getLigue().getId());
	        removeAdmins.executeUpdate();

	        // 2. Nommer ce nouvel admin
	        PreparedStatement setAdmin = connection.prepareStatement(
	            "UPDATE employe SET isAdmin = 1 WHERE id = ?");
	        setAdmin.setInt(1, employe.getId());
	        setAdmin.executeUpdate();

	        return employe.getId();

	    } catch (SQLException exception) {
	        exception.printStackTrace();
	        throw new SauvegardeImpossible(exception);
	    }
	}
	



	
}
