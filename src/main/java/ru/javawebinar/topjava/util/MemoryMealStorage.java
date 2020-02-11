package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealStorage implements MealStorage {
    private static AtomicInteger counter = new AtomicInteger(0);
    private static MemoryMealStorage instance = new MemoryMealStorage();

    private List<Meal> meals = Collections.synchronizedList(new ArrayList<>());
    {
        add(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        add(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        add(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    }

    private MemoryMealStorage(){}

    public static MemoryMealStorage getInstance() {
        return instance;
    }

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        meals.add(new Meal(counter.getAndIncrement(), dateTime, description, calories));
    }

    @Override
    public void edit(int id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = get(id);
        if (meal != null) {
            int index = meals.indexOf(meal);
            meals.set(index, new Meal(id, dateTime, description, calories));
        }
    }

    @Override
    public void delete(int id) {
        meals.removeIf(m -> m.getId() == id);
    }

    @Override
    public Meal get(int id) {
        return meals.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals);
    }
}
