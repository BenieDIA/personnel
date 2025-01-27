package personnel;
import java.time.LocalDate;

public class datesInvalides extends Exception {
	 private static final long serialVersionUID = 1L;
    public datesInvalides(String message) {
    	
        super(message);
    }
}