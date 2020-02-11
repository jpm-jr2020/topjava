package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealStorage {
    void add(LocalDateTime dateTime, String description, int calories);
    void edit(int id, LocalDateTime dateTime, String description, int calories);
    void delete(int id);
    Meal get(int id);
    List<Meal> getAll();
}
