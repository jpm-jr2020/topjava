package ru.javawebinar.topjava.util.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DateAndTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<DateAndTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(Arrays.asList(LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(DateAndTimeFormat annotation, Class<?> fieldType) {
        return getDateFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(DateAndTimeFormat annotation, Class<?> fieldType) {
        return getDateFormatter(annotation, fieldType);
    }

    private Formatter<?> getDateFormatter(DateAndTimeFormat annotation, Class<?> fieldType) {
        return fieldType.equals(LocalDate.class) ? new DateFormatter() : new TimeFormatter();
    }
}
