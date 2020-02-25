package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private final int userId = SecurityUtil.authUserId();

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, userId);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, userId), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void createSameMoment() {
        service.create(getNewSameMoment(), userId);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(MEAL_ID_2, userId);
        service.get(MEAL_ID_2, userId);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, userId);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_ID_3, userId);
        assertMatch(meal, MEAL_3);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, userId);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(userId);
        assertMatch(all, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenHalfOpen2Params() {
        List<Meal> all = service.getBetweenHalfOpen(DateTimeUtil.parseLocalDate("2020-02-22"), DateTimeUtil.parseLocalDate("2020-02-22"), userId);
        assertMatch(all, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenHalfOpen1Param() {
        List<Meal> all = service.getBetweenHalfOpen(DateTimeUtil.parseLocalDate("2020-02-23"), null, userId);
        assertMatch(all, MEAL_6, MEAL_5, MEAL_4);
    }

    @Test
    public void getBetweenHalfOpen0Params() {
        List<Meal> all = service.getBetweenHalfOpen(null, null, userId);
        assertMatch(all, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenHalfOpenEmpty() {
        List<Meal> all = service.getBetweenHalfOpen(DateTimeUtil.parseLocalDate("2021-02-22"), DateTimeUtil.parseLocalDate("2020-02-23"), userId);
        assertMatch(all);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, userId);
        assertMatch(service.get(MEAL_ID_1, userId), updated);
    }

    @Test(expected = DataAccessException.class)
    public void updateToExistingMoment() throws Exception {
        Meal updated = getUpdated();
        updated.setDateTime(LocalDateTime.of(DateTimeUtil.parseLocalDate("2020-02-22"), DateTimeUtil.parseLocalTime("13:00:00")));
        service.update(updated, userId);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlien() throws Exception {
        service.delete(MEAL_ID_4, 1);
    }

    @Test(expected = NotFoundException.class)
    public void getAlien() throws Exception {
        service.get(MEAL_ID_5, 1);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlien() throws Exception {
        service.update(getUpdated(), 1);
    }
}
