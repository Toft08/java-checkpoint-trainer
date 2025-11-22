import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AgeFinder {
    public int calculateAge(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate birthDate = LocalDate.parse(date, formatter);

            LocalDate today = LocalDate.now();

            if (birthDate.isAfter(today)) {
                return -1;
            }
            return Period.between(birthDate, today).getYears();
        } catch(Exception e) {
            return -1;
        }
    }
}