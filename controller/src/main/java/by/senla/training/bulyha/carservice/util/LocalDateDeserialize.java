package by.senla.training.bulyha.carservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateDeserialize {

    private static final Logger log = LoggerFactory.getLogger(LocalDateDeserialize.class);

    public static LocalDate parsLocalDate(String date) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate datetime = null;
        try {
            datetime = LocalDate.parse(date, pattern);
        } catch (DateTimeParseException e) {
            log.error("Error! LocalDate can not parse!");
        }
        return datetime;
    }

    public static String parsFromLocalDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
