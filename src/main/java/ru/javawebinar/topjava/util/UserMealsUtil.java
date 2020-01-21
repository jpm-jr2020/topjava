package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("Filter by cycles, O(N), 2 passes");
        mealsTo.forEach(System.out::println);
        System.out.println();

        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println("Filter by streams, O(N), 2 passes");
        mealsTo.forEach(System.out::println);
        System.out.println();
    }

    // Filter by cycles, O(N), 2 passes
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if (meals == null) return null;

        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        meals.forEach(userMeal -> caloriesPerDayMap.merge(userMeal.getDate(), userMeal.getCalories(),
                Integer::sum));

        List<UserMealWithExcess> mealWithExcessList = new ArrayList<>();
        meals.forEach(userMeal -> {
            if (TimeUtil.isBetweenInclusive(userMeal.getTime(), startTime, endTime)) {
                mealWithExcessList.add(toUserMealWithExcess(userMeal, caloriesPerDay < caloriesPerDayMap.get(userMeal.getDate())));
            }
        });
        return mealWithExcessList;
    }

    // Filter by streams, O(N), 2 passes
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if (meals == null) return null;

        Map<LocalDate, Integer> caloriesPerDayMap = meals.stream()
                .collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum));

        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenInclusive(userMeal.getTime(), startTime, endTime))
                .map(userMeal -> toUserMealWithExcess(userMeal, caloriesPerDay < caloriesPerDayMap.get(userMeal.getDate())))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess toUserMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }
}
