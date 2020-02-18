package ru.javawebinar.topjava.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class MealForTask5 extends Meal {

    public MealForTask5(LocalDateTime dateTime, String description, int calories) {
        super(dateTime, description, calories);
    }
}
