package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.Ligue;
import personnel.dateInvalide;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}
	
	

	
	ListOption<Employe> selectEmploye()
	{
		return (employe) -> selectEmploye(employe);		
	}

	Option selectEmploye(Employe employe)
	{
			Menu menu = new Menu("Selectionner " + employe.getNom(), "w");
			menu.add(afficher(employe));
			menu.add(Modifier(employe));
			menu.add(supprimer(employe));
			menu.addBack("q");
			return menu;
			
			
	}
	
	Option Modifier(Employe employe)
	{
			Menu menu = new Menu("Modifier " + employe.getNom(), "m");
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(changerDate(employe));
			menu.addBack("q");
			return menu;


	}
	
	private Option supprimer(Employe employe)
	{
		return new Option("Supprimer", "v", () -> {try {
			employe.remove();
		} catch (dateInvalide e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	


	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setNom(getString("Nouveau nom : "));}
			);
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setPrenom(getString("Nouveau prénom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	
 Option changerDate(final Employe employe) throws DateTimeParseException{
        return new Option("Changer la date d'inscription", "d", () -> {
            String dateStr = getString("Nouvelle date d'inscription (aaaa-mm-jj) : ");
            
            try {
            	
            	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            	    LocalDate date = LocalDate.parse(dateStr, formatter);
                 employe.setDate(date);
                 
            } catch (dateInvalide e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (DateTimeParseException d) {
                System.out.println("Format de date invalide: " + d.getMessage());
            }
           
        });
    }

}


