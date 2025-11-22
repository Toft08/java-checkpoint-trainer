import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class MonthlyPeriod {
    public String calculatePeriod(String startDate, String endDate) {
        try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        int months = Math.abs(Period.between(start, end).getMonths());
        int years = Math.abs(Period.between(start, end).getYears());

        StringBuilder result = new StringBuilder();
        if (years >= 1) {
            result.append(years).append(years == 1 ? " year" : " years");
        }
        if (months >= 1) {
            if (!result.isEmpty()) {
                result.append(" and ");
            }
            result.append(months).append(months == 1 ? " month" : " months");
        }
        return result.toString();
    } catch (Exception e) {
        return "Error";
    }
    }
}