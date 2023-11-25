package main.utils.ui;

import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateStringConverter {
    public static String dateString(LocalDate d) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return d.format(formatter);
    }
}
