import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectTime {
    private String startTime;
    private String endTime;
    private float hoursLogged;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ProjectTime(String start, String end) {
        this.startTime = start;
        this.endTime = end;
        calculateHours();
    }

    public void setStartTime(String start) {
        this.startTime = start;
        calculateHours();
    }

    public void setEndTime(String end) {
        this.endTime = end;
        calculateHours();
    }

    public String getStartTime() { return startTime; }
    public String getEndTime()   { return endTime; }

    public String getHoursLogged() {
        if (hoursLogged == -1) return "-1"; // error case

        if (hoursLogged < 2) { // minutes
            return (long)(hoursLogged * 60) + " m";
        } else if (hoursLogged < 120) { // hours
            return (long)hoursLogged + " h";
        }

        float days = hoursLogged / 24f;
        if (days < 120) { // days
            return (long)days + " d";
        }

        return (long)(days / 30f) + " mo"; // months
    }

    private void calculateHours() {
        try {
            Date start = formatter.parse(startTime);
            Date end   = formatter.parse(endTime);

            if (end.before(start)) {
                hoursLogged = -1;
                return;
            }

            long diffMillis = end.getTime() - start.getTime();
            hoursLogged = diffMillis / (1000f * 60 * 60); // hours
        } catch (ParseException | NullPointerException e) {
            hoursLogged = -1;
        }
    }
}


// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Date;

// public class ProjectTime {
//     private String startTime;
//     private String endTime;
//     private float hoursLogged;

//     private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

//     public ProjectTime(String start, String end) {
//         this.startTime = start;
//         this.endTime = end;
//         calculateHours();
//     }

//     public void setStartTime(String start) {
//         this.startTime = start;
//         calculateHours();
//     }

//     public void setEndTime(String end) {
//         this.endTime = end;
//         calculateHours();
//     }

//     public String getStartTime() {
//         return startTime;
//     }

//     public String getEndTime() {
//         return endTime;
//     }

//     public String getHoursLogged() {
//         if (hoursLogged == -1) {
//             return "-1";
//         }
//         float days = hoursLogged / 24f;
//             if (hoursLogged < 2) {
//             long minutes = (long) Math.floor(hoursLogged * 60);
//                 return minutes + " m";
//             } else if (hoursLogged >= 2 && hoursLogged <120) {
//                 long hours = (long) Math.floor(hoursLogged);
//                 return hours + " h";
//             } else if (hoursLogged >= 120 && days <120) {
//                 long d = (long) Math.floor(days);
//                 return d + " d";
//             } else {
//                 long months = (long) Math.floor(days / 30f);
//                 return months + " mo";
//             }
//     }

//     private void calculateHours() {
//         try {
//             Date start = formatter.parse(startTime);
//             Date end = formatter.parse(endTime);

//             if (end.before(start)) {
//                 hoursLogged = -1;
//                 return;
//             }

//             long diffMillis = end.getTime() - start.getTime();
//             float diffHours = (float) diffMillis / (1000 * 60 * 60);
//             hoursLogged = diffHours;
//         } catch (ParseException | NullPointerException e) {
//             hoursLogged = -1;
//         }
//     }
// }
