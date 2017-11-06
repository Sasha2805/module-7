package date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseDate {
    public static Date parseDateFromString(String stringDate) {  // Парсим String в Date
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
