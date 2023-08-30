//https://mkyong.com/java/how-to-calculate-date-time-difference-in-java/:

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DateDifference {

    public static String timeDifference(String dateStop) 
    {
        String returnString = "";
        
        // current time:
        SimpleDateFormat easternTime = new SimpleDateFormat("MM/dd/yyyy H:m:s");
        Date currentDate = new Date();
        Calendar currentTime = Calendar.getInstance();
        String dateStart = easternTime.format(currentDate.getTime());
        //String dateStart = "01/14/2012 09:29:58";
        //String dateStop = "01/15/2012 10:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            returnString = diffDays + "d " + diffHours + "h " + diffMinutes + "m " + diffSeconds + "s"; 

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnString;
    }

}