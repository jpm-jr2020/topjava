package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DatajpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() throws Exception {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        USER_MATCHER.assertMatch(meal.getUser(), USER);
        MEAL_MATCHER.assertMatch(meal, MEAL1);
    }
}
