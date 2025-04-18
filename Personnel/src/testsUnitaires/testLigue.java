package testsUnitaires;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ItemEvent;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	Employe employe;
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible, dateInvalide
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.of(2025, 12, 01) , LocalDate.of(2025, 01, 01)); 
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	
	
	
	// test des Methodes Setters DATE INSCRIPTION ET DEPART
	
	@Test
	public void setdDate() throws SauvegardeImpossible, dateInvalide {
	  Ligue ligue = gestionPersonnel.addLigue("Darts League");
	  Employe employe1 = ligue.addEmploye("Joe", "Bob", "jb@live.com", "azerty",
	                                      LocalDate.now(),
	                                       LocalDate.of(2026, 01, 02)); 
	  assertEquals(employe1.getDate(), LocalDate.of(2026, 01, 02));
	}
	
	@Test
	public void setdDateDepart() throws SauvegardeImpossible, dateInvalide {
	  Ligue ligue = gestionPersonnel.addLigue("Darts League");
	  Employe employe2 = ligue.addEmploye("Joe", "Bob", "jb@live.com", "azerty",
	                                      LocalDate.now(),
	                                       LocalDate.of(2026, 01, 02)); 
	  assertEquals(employe2.getDate(), LocalDate.of(2026, 01, 02));
	}
	
	
	// test des Methode Remove

	@Test
	void remove(Employe employe) throws dateInvalide, SauvegardeImpossible
	{
		  Ligue ligue = gestionPersonnel.addLigue("Darts League");
		  Employe employe3 = ligue.addEmploye("Joe", "Bob", "jb@live.com", "azerty", LocalDate.now(),
                LocalDate.of(2026, 01, 02));
		  	
	}
	
	
	
	
}