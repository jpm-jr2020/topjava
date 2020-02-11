package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealStorage {
    void add(Meal meal);
    void edit(Meal meal);
    void delete(Integer id);
    Meal get(Integer id);
    List<Meal> getAll();
}
