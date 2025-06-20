package personnel;

import java.io.Serializable;
import java.time.LocalDate;

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
	private int id = -1;
	private String nom, prenom, password, mail;
	private LocalDate dateArriver = LocalDate.now();
	private LocalDate dateDepart = null;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	
	
	  Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArriver, LocalDate dateDepart) throws SauvegardeImpossible
	    {
	        this(gestionPersonnel,ligue, -1, nom, prenom, mail, password, dateArriver, dateDepart);
	        this.id = gestionPersonnel.insert(this);
	        
	    }

	    Employe(GestionPersonnel gestionPersonnel,Ligue ligue,int id,String nom, String prenom, String mail, String password, LocalDate dateArriver, LocalDate dateDepart)
	    {
	    	
	    
	    	this.ligue = ligue;
	        this.id = id;
	        this.gestionPersonnel = gestionPersonnel;
	        this.nom = nom;
	        this.prenom = prenom;
	        this.password = password;
	        this.mail = mail;
	        this.dateArriver = dateArriver;
	        this.dateDepart = dateDepart;


	    }

	  
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	    public boolean estAdmin(Ligue ligue) {
	        if (ligue == null) {
	            return false; // Retourne false si la ligue est null
	        }
	        return this.equals(ligue.getAdministrateur());
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
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 * @throws SauvegardeImpossible 
	 */
	
	public void setNom(String nom) throws SauvegardeImpossible
	{
		this.nom = nom;

			this.id = gestionPersonnel.update(this);

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
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 * @throws SauvegardeImpossible 
	 */

	public void setPrenom(String prenom) throws SauvegardeImpossible
	{
		this.prenom = prenom;
			this.id = gestionPersonnel.update(this);
	
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
	 * @throws SauvegardeImpossible 
	 */

	public void setMail(String mail) throws SauvegardeImpossible
	{
		this.mail = mail;
			this.id = gestionPersonnel.update(this);
	
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
	 * @throws SauvegardeImpossible 
	 */
	
	public void setPassword(String password) throws SauvegardeImpossible
	{
		this.password= password;

			this.id = gestionPersonnel.update(this);
	
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}
	
	
	public LocalDate getDate() {
		return dateArriver;
	}
	
	public void setDate(LocalDate dateArriver) throws dateInvalide, SauvegardeImpossible{
		if(dateArriver.isAfter(LocalDate.now())) throw new dateInvalide("Date dans le futur");
		this.dateArriver = dateArriver;
		this.id = gestionPersonnel.update(this);

	}
	
	public LocalDate getDateDepart() {
		return dateDepart;
	}
	
	public void setDateDepart(LocalDate dateDepart) throws dateInvalide {
		if(dateDepart.isAfter(LocalDate.now())) throw new dateInvalide("Date dans le futur");
		if(dateDepart.isBefore(getDate())) throw new dateInvalide("Date depart ne peut pas etre avant la date d'inscription");
	
			this.dateDepart = dateDepart;
	}
	

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 * @throws SauvegardeImpossible 
	 * @throws dateInvalide 
	 */
	


	public void remove() throws dateInvalide, SauvegardeImpossible {
	    Employe root = gestionPersonnel.getRoot();
	    if (this != root) {
	        if (getLigue() != null) {
	            getLigue().remove(this); // Supprime l'employé de la ligue
	        }
	    } else {	
	        throw new ImpossibleDeSupprimerRoot();
	    }
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
		String res = nom + " " + prenom + " " + mail +" "+dateArriver+" (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
}
