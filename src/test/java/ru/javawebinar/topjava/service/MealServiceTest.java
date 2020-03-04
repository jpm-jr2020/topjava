package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.*;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.util.StopWatch;
import org.junit.rules.Stopwatch;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    @Autowired
    private MealService service;
    @Autowired
    private MealRepository repository;

    private static final String ESCAPE_BLACK = "\u001B[0m";
    private static final String ESCAPE_RED = "\u001B[31m";
    private static final String ESCAPE_BLUE = "\u001B[34m";
    private static final String ESCAPE_GREEN = "\u001B[32m";

    private static final Logger log = getLogger(MealServiceTest.class);
    private static final Logger statLog = getLogger("statistics");
    private static Map<String, Long> timers = new HashMap<>();
    private static Long totalTime = new Long(0);

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @AfterClass
    public static void afterClass() {
        StringBuilder stats = new StringBuilder();
        String format = "%-30s %10s %n";
        stats.append(ESCAPE_BLUE + "Tests duration:\n");
        stats.append(String.format(format, "Test name", "Duration"));
        stats.append(String.format(format, "-------------------------", "----------"));
        timers.forEach((k, v) -> stats.append(String.format(format, k, v + " us")));
        stats.append(String.format(format, "-------------------------", "----------"));
        stats.append(String.format(format, "Total", totalTime + " us" + ESCAPE_BLACK));
        statLog.info(stats.toString());
    }

    @Rule
    public final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            timers.put(description.getMethodName(), nanos / 1000);
            totalTime += nanos / 1000;
            statLog.info(ESCAPE_GREEN + "Test {} finished, duration {} us" + ESCAPE_BLACK, description.getMethodName(), nanos / 1000);
        }
    };

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        Assert.assertNull(repository.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with");
        service.delete(1, USER_ID);
    }

    @Test
    public void deleteNotOwn() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with");
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with");
        service.get(1, USER_ID);
    }

    @Test
    public void getNotOwn() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with");
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with");
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        MEAL_MATCHER.assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetweenInclusive() throws Exception {
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID),
                MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenWithNullDates() throws Exception {
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), MEALS);
    }
}