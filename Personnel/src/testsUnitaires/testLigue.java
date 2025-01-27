package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible, datesInvalides
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.of(2026,12,01), LocalDate.of(2023,02,04)); /*bnie*/
		assertEquals(employe, ligue.getEmployes().first());
	}
	// test des Methodes Setters DATE INSCRIPTION ET DEPART
	
	@Test
	public void setdDateinscription() throws SauvegardeImpossible, datesInvalides {
	  Ligue ligue = gestionPersonnel.addLigue("Darts League");
	  Employe employe1 = ligue.addEmploye("Joe", "Bob", "jb@live.com", "azerty",
	                                      LocalDate.now(),
	                                       LocalDate.of(2026, 01, 02)); 

	  // Assert that the date retrieved by getDate matches the expected date of birth
	  assertEquals(employe1.getDateinscription(), LocalDate.of(2026, 01, 02));
	}
	
	@Test
	public void setdDateDepart() throws SauvegardeImpossible, datesInvalides {
	  Ligue ligue = gestionPersonnel.addLigue("Darts League");
	  Employe employe2 = ligue.addEmploye("Joe", "Bob", "jb@live.com", "azerty",
	                                      LocalDate.now(),
	                                       LocalDate.of(2026, 01, 02)); 

	  // Assert that the date retrieved by getDate matches the expected date of birth
	  assertEquals(employe2.getDepart(), LocalDate.of(2026, 01, 02));
	}
	
	
	// test des Methodes Setters 
	
    
    
}