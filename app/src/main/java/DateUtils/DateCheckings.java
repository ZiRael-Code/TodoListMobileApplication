package DateUtils;

import java.util.Calendar;
import java.util.TimeZone;

public class DateCheckings {


        private static TimeZone getDefaultTimeZone() {
            return TimeZone.getDefault();
        }

        public static boolean isApiDateToday(String apiDateString) {
            TimeZone userTimeZone = getDefaultTimeZone();

            Calendar userCalendar = Calendar.getInstance(userTimeZone);
            Calendar apiCalendar = Calendar.getInstance(userTimeZone);

            String[] parts = apiDateString.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int day = Integer.parseInt(parts[2]);
            apiCalendar.set(year, month, day);

            return userCalendar.get(Calendar.YEAR) == apiCalendar.get(Calendar.YEAR) &&
                    userCalendar.get(Calendar.MONTH) == apiCalendar.get(Calendar.MONTH) &&
                    userCalendar.get(Calendar.DAY_OF_MONTH) == apiCalendar.get(Calendar.DAY_OF_MONTH);
        }


    }
