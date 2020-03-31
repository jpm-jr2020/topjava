package ru.javawebinar.topjava.util.format;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class TimeFormatter implements Formatter<LocalTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
      return LocalTime.parse(text, formatter);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.toString();
    }
}
