package ru.javawebinar.topjava.util.format;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
      return LocalDate.parse(text, formatter);
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.toString();
    }
}
