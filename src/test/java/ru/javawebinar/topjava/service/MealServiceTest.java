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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
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
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void createSameMoment() {
        service.create(getNewSameMoment(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        service.delete(MEAL_ID_2, USER_ID);
        service.get(MEAL_ID_2, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_3, USER_ID);
        assertMatch(meal, MEAL_3);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenHalfOpen2Params() {
        List<Meal> all = service.getBetweenHalfOpen(LocalDate.of(2020, 2, 22), LocalDate.of(2020, 2, 22), USER_ID);
        assertMatch(all, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenHalfOpen1Param() {
        List<Meal> all = service.getBetweenHalfOpen(LocalDate.of(2020, 2, 23), null, USER_ID);
        assertMatch(all, MEAL_6, MEAL_5, MEAL_4);
    }

    @Test
    public void getBetweenHalfOpen0Params() {
        List<Meal> all = service.getBetweenHalfOpen(null, null, USER_ID);
        assertMatch(all, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenHalfOpenEmpty() {
        List<Meal> all = service.getBetweenHalfOpen(LocalDate.of(2021, 2, 22), LocalDate.of(2020, 2, 23), USER_ID);
        assertMatch(all);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID_1, USER_ID), updated);
    }

    @Test(expected = DataAccessException.class)
    public void updateToExistingMoment() throws Exception {
        Meal updated = getUpdated();
        updated.setDateTime(LocalDateTime.of(LocalDate.of(2020, 2, 22), LocalTime.of(13, 0)));
        service.update(updated, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlien() throws Exception {
        service.delete(MEAL_ID_4, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getAlien() throws Exception {
        service.get(MEAL_ID_5, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlien() throws Exception {
        service.update(getUpdated(), ADMIN_ID);
    }
}
