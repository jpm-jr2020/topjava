package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getDescription().contains(" Админа") ? 1 : 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, k -> new HashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }

        // handle case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (k, v)  -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return (userMeals != null) && (userMeals.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? null : (userMeals.get(id));
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getMealsFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate date1, LocalDate date2) {
        return getMealsFiltered(userId, meal -> DateTimeUtil.isBetweenInclusive(meal.getDate(), date1, date2));
    }

    private List<Meal> getMealsFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? Collections.emptyList() :
                userMeals.values().stream()
                .filter(filter)
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }
}

