package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

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
	
	
	@Test
	public void setdDate() throws SauvegardeImpossible, dateInvalide {
	  Ligue ligue = gestionPersonnel.addLigue("Darts League");
	  Employe employe1 = ligue.addEmploye("Joe", "Bob", "jb@live.com", "azerty",
	                                      LocalDate.now(),
	                                       LocalDate.of(2026, 01, 02)); 

	  // Assert that the date retrieved by getDate matches the expected date of birth
	  assertEquals(employe1.getDate(), LocalDate.of(2026, 01, 02));
	}
}