package commandLine;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import commandLineMenus.List;

import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}

	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}

	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerAdministrateur(ligue));
		menu.add(retirerAdministrateur(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "s", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	
	private Option ajouterEmploye(final Ligue ligue) throws datesInvalides
	{
		 
		
		return new Option("ajouter un employé", "a",
				() -> 
				{
					String dateStr = getString("Nouvelle date d'inscription (aaaa-mm-jj) : ");
					try {
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					    LocalDate date = LocalDate.parse(dateStr, formatter);
						try {
							ligue.addEmploye(getString("nom : "), 
								getString("prenom : "), getString("mail : "), 
								getString("password : "), 
								date, 
								null);
						} catch (SauvegardeImpossible e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}		    
					} catch (DateTimeParseException d) {
			            System.out.println("Format de date invalide: " + d.getMessage());
			        }
				}
		);
	}
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(afficherEmployes(ligue));
		try {
			menu.add(ajouterEmploye(ligue));
		} catch (datesInvalides e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menu.add(selectionEmploye(ligue));
		menu.addBack("q");
		return menu;
	}
	
	private List<Employe> selectionEmploye(final Ligue ligue)
	{
		return new List<>("selectionner employe", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.selectEmploye()
    			
				);
	}
	
	Option selectEmploye(Ligue ligue, Employe employe)
	{
			Menu menu = new Menu("Gérer le compte " + ligue.getEmployes(), "c");
			menu.add(employeConsole.selectEmploye(employe));
			menu.add(supprimerEmploye(ligue));
			menu.addBack("q");
			return menu;
	}

	
	private List<Employe> supprimerEmploye(final Ligue ligue)
	{
		return new List<>("Supprimer un employé", "s", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {try {
					try {
						element.remove();
					} catch (SauvegardeImpossible e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (datesInvalides e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
				);
	}
	
	private List<Employe> changerAdministrateur(final Ligue ligue)
	{
		return new List<Employe>("changer administrateur", "k", 
				() -> new ArrayList<>(ligue.getEmployes()),
				(index, element) -> {
					try {
						ligue.setAdministrateur(element );
					} catch (SauvegardeImpossible e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
		
	}		
	private Option retirerAdministrateur(Ligue ligue)
	{
	
		return new Option("Retirer les droits d'administrateur", "t", () -> {try {
			ligue.setAdministrateur(gestionPersonnel.getRoot());
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
		
	}		
	
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {try {
			ligue.remove();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	
}