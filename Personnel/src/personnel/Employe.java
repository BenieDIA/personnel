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
	private int id = 1;
	private String nom, prenom, password, mail;
	private LocalDate dateArriver = LocalDate.now();
	private int ligue_id;
	private LocalDate dateDepart = null;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	
	Employe(GestionPersonnel gestionPersonnel, int id,Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArriver, LocalDate dateDepart, int ligue_id)
	{
	    this.dateArriver = dateArriver;
	    this.nom = nom;
	    this.prenom = prenom;
	    this.password = password;
	    this.mail = mail;
	    this.ligue = ligue;
	    this.gestionPersonnel = gestionPersonnel;
	    this.id = id;
	    this.ligue_id = ligue.getLigueId();	
	}
	
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArriver, LocalDate dateDepart, int ligue_id) throws SauvegardeImpossible
	{
	    this(gestionPersonnel, -1, ligue, nom, prenom, mail, password, dateArriver, dateDepart, ligue_id);
	    this.id = gestionPersonnel.insert(this);
	}
	
	Employe(GestionPersonnel gestionPersonnel,int id,LocalDate dateArriver,String nom, String prenom, String mail, String password)
	{
		this.dateArriver = dateArriver;
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		
	}
	
	Employe(String nom, String password)
	{
		this.nom = nom;
		this.password = password;
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
	
	
    public int getLigueId() {
        return ligue_id;
    }

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
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
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
	
	public void setDate(LocalDate date) throws dateInvalide{
		if(date.isAfter(LocalDate.now())) throw new dateInvalide("Date dans le futur");
		this.dateArriver = date;
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
	 */
	
	public void remove() throws dateInvalide
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
		String res = nom + " " + prenom + " " + mail +" "+dateArriver+" (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
}
