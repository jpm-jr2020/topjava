package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DatajpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, USER);
        MEAL_MATCHER.assertMatch(user.getMeals(), MEALS);
    }

    @Test
    public void getWithNoMeals() throws Exception {
        User user = service.getWithMeals(HUNGRY_ID);
        USER_MATCHER.assertMatch(user, HUNGRY);
        MEAL_MATCHER.assertMatch(user.getMeals(), Collections.EMPTY_LIST);
    }
}
