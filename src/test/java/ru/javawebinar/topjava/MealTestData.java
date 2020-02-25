package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;

    public static final int MEAL_ID_1 = START_SEQ + 2;
    public static final int MEAL_ID_2 = START_SEQ + 3;
    public static final int MEAL_ID_3 = START_SEQ + 4;
    public static final int MEAL_ID_4 = START_SEQ + 5;
    public static final int MEAL_ID_5 = START_SEQ + 6;
    public static final int MEAL_ID_6 = START_SEQ + 7;

    public static final Meal MEAL_1 = new Meal(MEAL_ID_1,
            LocalDateTime.of(DateTimeUtil.parseLocalDate("2020-02-22"), DateTimeUtil.parseLocalTime("10:00:00")),
            "Пользователь завтрак 1", 500);
    public static final Meal MEAL_2 = new Meal(MEAL_ID_2,
            LocalDateTime.of(DateTimeUtil.parseLocalDate("2020-02-22"), DateTimeUtil.parseLocalTime("13:00:00")),
            "Пользователь обед 1", 1000);
    public static final Meal MEAL_3 = new Meal(MEAL_ID_3,
            LocalDateTime.of(DateTimeUtil.parseLocalDate("2020-02-22"), DateTimeUtil.parseLocalTime("20:00:00")),
            "Пользователь ужин 1", 500);
    public static final Meal MEAL_4 = new Meal(MEAL_ID_4,
            LocalDateTime.of(DateTimeUtil.parseLocalDate("2020-02-23"), DateTimeUtil.parseLocalTime("10:00:00")),
            "Пользователь завтрак 2", 510);
    public static final Meal MEAL_5 = new Meal(MEAL_ID_5,
            LocalDateTime.of(DateTimeUtil.parseLocalDate("2020-02-23"), DateTimeUtil.parseLocalTime("12:00:00")),
            "Пользователь обед 2", 1010);
    public static final Meal MEAL_6 = new Meal(MEAL_ID_6,
            LocalDateTime.of(DateTimeUtil.parseLocalDate("2020-02-23"), DateTimeUtil.parseLocalTime("20:00:00")),
            "Пользователь ужин 2", 510);

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "Новая еда", 1234);
    }

    public static Meal getNewSameMoment() {
        return new Meal(LocalDateTime.of(DateTimeUtil.parseLocalDate("2020-02-22"), DateTimeUtil.parseLocalTime("10:00:00")), "Новая еда", 1234);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("Измененная еда");
        updated.setCalories(1567);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
