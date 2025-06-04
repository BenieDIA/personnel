package personnel;

import java.io.Serializable;
import java.sql.ResultSet;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private int id = 1;
	private String nom, prenom, password, mail;
	private LocalDate Dateinscription = LocalDate.now();
	private LocalDate Depart = null;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;



  Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate Dateinscription, LocalDate Depart) throws SauvegardeImpossible
    {
        this(gestionPersonnel,ligue, -1, nom, prenom, mail, password, Dateinscription, Depart);
        this.id = gestionPersonnel.insert(this);
        
    }

    Employe(GestionPersonnel gestionPersonnel,Ligue ligue,int id,String nom, String prenom, String mail, String password, LocalDate Dateinscription, LocalDate Depart)
    {
    	
    
    	this.ligue = ligue;
        this.id = id;
        this.gestionPersonnel = gestionPersonnel;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.mail = mail;
        this.Dateinscription = Dateinscription;
        this.Depart = Depart;


    }

  

	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	
  
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
		try {
			this.id = gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	public String getPassword() {
		return password;
	}
	public int getId() {
		return id;
	}
	public void setId() {
		this.id = id;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
		try {
			this.id = gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
		try {
			this.id = gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
		try {
			this.id = gestionPersonnel.update(this);
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}
	
	
	public LocalDate getDepart() {
		return Depart;
	}
	
	public void setDateinscription(LocalDate Dateinscription) throws datesInvalides{
		if(Dateinscription.isAfter(LocalDate.now())) throw new datesInvalides("Date dans le futur");
		this.Dateinscription = Dateinscription;
	}
	
	
	public void setDepart(LocalDate Depart) throws datesInvalides {
		if(Depart.isAfter(LocalDate.now())) throw new datesInvalides("Date dans le futur");
		if(Depart.isBefore(getDateinscription())) throw new datesInvalides("Date depart ne peut pas etre avant la date d'inscription");
	
			this.Depart = Depart;
			try {
				this.id = gestionPersonnel.update(this);
			} catch (SauvegardeImpossible e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	public LocalDate getDateinscription() {
	
		return Dateinscription;
	}


	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 * @throws SauvegardeImpossible 
	 */
	
	public void remove() throws datesInvalides, SauvegardeImpossible
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " " + Dateinscription + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += "ligue : " + (ligue != null ? ligue.getNom() : "aucune");
		return res + ")";
	}
	


}