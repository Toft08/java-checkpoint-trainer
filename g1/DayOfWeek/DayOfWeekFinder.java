import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DayOfWeekFinder {
    public String findNextDayOfWeek(String startDate, String dayOfWeek) {
        try {
            LocalDate date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyy-MM-dd"));
            DayOfWeek targetDay = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            
            LocalDate nextDate = date.with(TemporalAdjusters.next(targetDay));
            
            return nextDate.toString();
        } catch (Exception e) {
            return "Error";
        }
    }
}
// alt solution

// import java.time.DayOfWeek;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.time.format.DateTimeParseException;

// public class DayOfWeekFinder {
//     public String findNextDayOfWeek(String startDate, String dayOfWeek) {
//         try {
//             LocalDate date = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyy-MM-dd"));

//             DayOfWeek targetDay = DayOfWeek.valueOf(dayOfWeek.toUpperCase());

//             int current = date.getDayOfWeek().getValue();
//             int target = targetDay.getValue();

//             // calculate how many days to add (wraps correctly if target is earlier in the week)
//             int daysToAdd = (target - current + 7) % 7;
//             if (daysToAdd == 0) {
//                 daysToAdd = 7; 
//             }

//             return date.plusDays(daysToAdd).toString();
//         } catch (Exception e) {
//             return "Error";
//         }
//     }
// }
