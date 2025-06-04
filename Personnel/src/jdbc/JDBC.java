package jdbc;

import java.sql.*;
import java.time.LocalDate;
import personnel.*;

public class JDBC implements Passerelle {
    Connection connection;

    public JDBC() {
        try {
            Class.forName(Credentials.getDriverClassName());
            connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
        } catch (ClassNotFoundException e) {
            System.out.println("Pilote JDBC non installé.");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public GestionPersonnel getGestionPersonnel() {
        GestionPersonnel gestionPersonnel = new GestionPersonnel();
        try {
            // Charger les ligues
            String requeteLigues = "SELECT * FROM ligue";
            Statement instructionLigues = connection.createStatement();
            ResultSet resultLigues = instructionLigues.executeQuery(requeteLigues);
            while (resultLigues.next()) {
                int id = resultLigues.getInt("id");
                String nom = resultLigues.getString("nom");
                gestionPersonnel.addLigue(id, nom);
            }

            // Charger les employés
            String requeteEmployes = "SELECT * FROM employe";
            Statement instructionEmployes = connection.createStatement();
            ResultSet resultEmployes = instructionEmployes.executeQuery(requeteEmployes);
            while (resultEmployes.next()) {
                int id = resultEmployes.getInt("id");
                String nom = resultEmployes.getString("nom");
                String prenom = resultEmployes.getString("prenom");
                String mail = resultEmployes.getString("mail");
                String password = resultEmployes.getString("password");
                LocalDate dateArriver = resultEmployes.getDate("dateArriver").toLocalDate();
                LocalDate depart = null;
                if (resultEmployes.getDate("dateDepart") != null)
                    depart = resultEmployes.getDate("dateDepart").toLocalDate();

                int ligueId = resultEmployes.getInt("ligue_id");
                if (resultEmployes.wasNull()) {
                    // Employé sans ligue = root
                    gestionPersonnel.addRoot(id, nom, password);
                } else {
                    // Ajouter employé à la bonne ligue
                    for (Ligue ligue : gestionPersonnel.getLigues()) {
                        if (ligue.getId() == ligueId) {
                            ligue.addEmploye(nom, prenom, mail, password, dateArriver, depart);
                            break;
                        }
                    }
                }
            }
        } catch (SQLException | SauvegardeImpossible e) {
            System.out.println(e);
        }

        return gestionPersonnel;
    }


    @Override
    public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible {
        close();
    }

    public void close() throws SauvegardeImpossible {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new SauvegardeImpossible(e);
        }
    }

    @Override
    public int insert(Ligue ligue) throws SauvegardeImpossible {
        try {
            PreparedStatement instruction = connection.prepareStatement(
                "INSERT INTO ligue (nom) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            instruction.setString(1, ligue.getNom());
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
    public int insert(Employe employe) throws SauvegardeImpossible {
        try {
            PreparedStatement instruction = connection.prepareStatement(
                "INSERT INTO employe (dateArriver, nom, prenom, mail, password, ligue_id) VALUES(?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            instruction.setDate(1, java.sql.Date.valueOf(employe.getDateinscription()));
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
    public int update(Employe employe) throws SauvegardeImpossible {
        try {
            PreparedStatement instruction = connection.prepareStatement(
                "UPDATE employe SET dateArriver = ?, nom = ?, prenom = ?, mail = ?, password = ? WHERE id = ?");
            instruction.setDate(1, employe.getDateinscription() != null 
                ? java.sql.Date.valueOf(employe.getDateinscription()) 
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
    public int delete(Ligue ligue) throws SauvegardeImpossible {
        try {
            PreparedStatement instruction = connection.prepareStatement(
                "DELETE ligue, employe FROM ligue LEFT JOIN employe ON employe.ligue_id = ligue.id WHERE ligue.id = ?");
            instruction.setInt(1, ligue.getId());
            int delete = instruction.executeUpdate();
            return delete;
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new SauvegardeImpossible(exception);
        }
    }

    @Override
    public int delete(Employe employe) throws SauvegardeImpossible {
        try {
            PreparedStatement instruction = connection.prepareStatement(
                "DELETE FROM employe WHERE id = ?");
            instruction.setInt(1, employe.getId());
            int delete = instruction.executeUpdate();
            return delete;
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new SauvegardeImpossible(exception);
        }
    }
    

@Override
public int setAdmin(Employe employe) throws SauvegardeImpossible {
    try {
        PreparedStatement instruction = connection.prepareStatement(
            "UPDATE employe SET isAdmin = ? WHERE id = ?");
        instruction.setInt(1, employe.estAdmin(employe.getLigue()) ? 1 : 0); // 1 pour admin, 0 sinon
        instruction.setInt(2, employe.getId());
        instruction.executeUpdate();
        return employe.getId();
    } catch (SQLException exception) {
        exception.printStackTrace();
        throw new SauvegardeImpossible(exception);
    }
}
}
